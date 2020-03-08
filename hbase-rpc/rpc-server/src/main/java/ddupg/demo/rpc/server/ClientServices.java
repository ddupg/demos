package ddupg.demo.rpc.server;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import ddupg.demo.hbase.rpc.ClientProtos;

public class ClientServices implements ClientProtos.ClientService.BlockingInterface {

  @Override
  public ClientProtos.GetResponse get(RpcController controller, ClientProtos.GetRequest request)
      throws ServiceException {
      ClientProtos.GetResponse response = ClientProtos.GetResponse.newBuilder()
          .setRow(request.getRow())
          .setValue("value" + System.currentTimeMillis())
          .build();
      return response;
  }
}
