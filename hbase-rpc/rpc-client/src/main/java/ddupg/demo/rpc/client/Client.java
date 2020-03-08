package ddupg.demo.rpc.client;

import ddupg.demo.hbase.rpc.ClientProtos;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public class Client {

  public static void main(String[] args) {
    InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 8888);

    RpcClient rpcClient = new RpcClient();

    ClientProtos.ClientService.Stub stub = ClientProtos.ClientService.newStub(rpcClient.createRpcChannel(serverAddress, 1000));
    ClientProtos.GetRequest request = ClientProtos.GetRequest.newBuilder().setRow("row").build();
    CompletableFuture<ClientProtos.GetResponse> future = new CompletableFuture<>();
    stub.get(new RpcControllerImpl(), request, res -> future.complete(res));
  }
}
