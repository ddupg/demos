package ddupg.demo.rpc.client;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;

import java.net.InetSocketAddress;

public class RpcChannelImpl implements RpcChannel {

  private RpcClient rpcClient;

  private InetSocketAddress address;

  public RpcChannelImpl(RpcClient rpcClient, InetSocketAddress address) {
    this.rpcClient = rpcClient;
    this.address = address;
  }

  @Override
  public void callMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request,
                         Message responsePrototype, RpcCallback<Message> done) {
    Call call = new Call(method, controller, request, responsePrototype);
    RpcConnection connection = rpcClient.getConnection(address);
    connection.call(call);
  }
}
