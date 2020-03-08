package ddupg.demo.rpc.server;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import ddupg.demo.hbase.rpc.ClientProtos;

public class ServerRpcServices implements ClientProtos.ClientService.BlockingInterface {

  private Server server;

  public ServerRpcServices(Server server) {
    this.server = server;
  }

  @Override
  public ClientProtos.GetResponse get(RpcController controller, ClientProtos.GetRequest request) throws ServiceException {
    return null;
  }
}
