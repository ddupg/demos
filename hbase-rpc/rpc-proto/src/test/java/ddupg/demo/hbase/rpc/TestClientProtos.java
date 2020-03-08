package ddupg.demo.hbase.rpc;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestClientProtos {

  @Test
  public void testProto() throws InvalidProtocolBufferException {
    ClientProtos.GetRequest req1 = ClientProtos.GetRequest.newBuilder().setRow("row").build();
    byte[] bytes = req1.toByteArray();
    ClientProtos.GetRequest req2 = ClientProtos.GetRequest.parseFrom(bytes);
    assertEquals(req2.getRow(), "row");

    ClientProtos.GetResponse res1 = ClientProtos.GetResponse.newBuilder().setRow("row").setValue("value").build();
    bytes = res1.toByteArray();
    ClientProtos.GetResponse res2 = ClientProtos.GetResponse.parseFrom(bytes);
    assertEquals(res2.getRow(), "row");
    assertEquals(res2.getValue(), "value");
  }
}
