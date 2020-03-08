package ddupg.demo.rpc.client;

import com.google.common.collect.Maps;
import com.google.protobuf.Message;
import ddupg.demo.hbase.rpc.RPCProtos;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class NettyRpcDuplexHandler extends ChannelDuplexHandler {

  private static final Logger LOG = LoggerFactory.getLogger(NettyRpcDuplexHandler.class);

  private RpcConnection conn;

  private Map<Integer, Call> calls;

  public NettyRpcDuplexHandler(RpcConnection conn) {
    this.conn = conn;
    calls = Maps.newHashMap();
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    if (msg instanceof Call) {
      writeRequest(ctx, (Call) msg, promise);
    } else {
      super.write(ctx, msg, promise);
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof ByteBuf) {
      readResponse(ctx, (ByteBuf) msg);
    }
    super.channelRead(ctx, msg);
  }

  private void writeRequest(ChannelHandlerContext ctx, Call call, ChannelPromise promise) throws Exception {
    calls.put(call.getId(), call);
    byte[] request = call.getRequest().toByteArray();
    ByteBuf buffer = ctx.alloc().buffer(4 + request.length);
    buffer.writeInt(request.length);
    buffer.writeBytes(request);
    ctx.write(buffer, promise);
  }

  private void readResponse(ChannelHandlerContext ctx, ByteBuf buf) throws IOException {
    int totalSize = buf.readInt();
    ByteBufInputStream in = new ByteBufInputStream(buf);
    RPCProtos.ResponseHeader responseHeader = RPCProtos.ResponseHeader.parseDelimitedFrom(in);
    int callId = responseHeader.getCallId();
    Call call = calls.remove(callId);
    if (call == null) {
      return;
    }
    if (!responseHeader.getSuccess()) {
      call.onError(responseHeader.getErrMsg());
      return;
    }
    Message.Builder builder = call.getResponsePrototype().newBuilderForType();
    builder.mergeDelimitedFrom(in);
    call.onComplete(builder.build());
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      switch (((IdleStateEvent) evt).state()) {
        case WRITER_IDLE:
          conn.shutdown();
          break;
        default:
          LOG.warn("Unexpected value: " + evt);
      }
    }
  }
}
