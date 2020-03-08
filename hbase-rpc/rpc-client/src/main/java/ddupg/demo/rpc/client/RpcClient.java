package ddupg.demo.rpc.client;

import com.google.common.collect.Maps;
import com.google.protobuf.RpcChannel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentMap;

public class RpcClient {

  private ConcurrentMap<InetSocketAddress, RpcConnection> connections;

  private EventLoopGroup group;

  public RpcClient() {
    connections = Maps.newConcurrentMap();
    group = new NioEventLoopGroup();
  }

  public RpcChannel createRpcChannel(InetSocketAddress address, long timeout) {
    return new RpcChannelImpl(this, address);
  }

  public RpcConnection getConnection(InetSocketAddress address) {
    return connections.computeIfAbsent(address, a -> new RpcConnection(this, address));
  }

  public EventLoopGroup getGroup() {
    return group;
  }
}
