package ddupg.demo.rpc.server;

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
}
