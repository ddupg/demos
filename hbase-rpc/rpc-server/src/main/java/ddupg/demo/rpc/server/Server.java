package ddupg.demo.rpc.server;

import ddupg.demo.hbase.rpc.ClientProtos;

public class Server {

  private ServerRpcServices rpcServices;

  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

  public Server() {
    new ServerRpcServices(this);
  }

  private void run() {

  }

  public ClientProtos.GetResponse get(ClientProtos.GetRequest get) {
    return ClientProtos.GetResponse.newBuilder().setRow(get.getRow()).setValue("value").build();
  }
}
