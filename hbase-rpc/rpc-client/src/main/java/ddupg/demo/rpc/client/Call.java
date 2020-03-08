package ddupg.demo.rpc.client;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.RpcController;

public class Call {

  private int id;

  private Descriptors.MethodDescriptor method;

  private RpcController controller;

  private Message request;

  private Message responsePrototype;

  public Call(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype) {
    this.method = method;
    this.controller = controller;
    this.request = request;
    this.responsePrototype = responsePrototype;
  }

  public void onComplete(Message response) {

  }

  public void onError(String msg) {

  }

  public int getId() {
    return id;
  }

  public Descriptors.MethodDescriptor getMethod() {
    return method;
  }

  public RpcController getController() {
    return controller;
  }

  public Message getRequest() {
    return request;
  }

  public Message getResponsePrototype() {
    return responsePrototype;
  }
}
