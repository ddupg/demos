package ddupg.demo.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

public class RpcConnection {

  private RpcClient rpcClient;

  private Channel channel;

  private InetSocketAddress address;

  public RpcConnection(RpcClient rpcClient, InetSocketAddress address) {
    this.rpcClient = rpcClient;
    this.address = address;
  }

  public void call(Call call) {

  }

  private void connect() {
    this.channel = new Bootstrap().group(rpcClient.getGroup()).channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
        .handler(new BufferCallBeforeInitHandler())
        .remoteAddress(address)
        .connect()
        .addListener(new ChannelFutureListener() {

          @Override
          public void operationComplete(ChannelFuture future) throws Exception {
            Channel ch = future.channel();
            if (!future.isSuccess()) {
              // TODO: 2020/2/29 failed
              return;
            }
            ch.pipeline().addFirst(new NettyRpcDuplexHandler(RpcConnection.this));
            ch.pipeline().addFirst(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
          }
        }).channel();
  }

  public void shutdown() {

  }
}
