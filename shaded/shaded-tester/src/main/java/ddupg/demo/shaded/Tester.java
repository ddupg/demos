package ddupg.demo.shaded;

import ddupg.demo.shaded.protobuf.generated.ProtocolProtos.Protocol;

import org.apache.hbase.thirdparty.com.google.protobuf.InvalidProtocolBufferException;

public class Tester {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        Protocol protocol = Protocol.newBuilder().setType(Protocol.Type.Add).setKey("key").setValue("value").build();
        byte[] bytes = protocol.toByteArray();
        Protocol newProtocol = Protocol.parseFrom(bytes);

        System.out.println(protocol.equals(newProtocol));
        System.out.println(protocol.getType().equals(newProtocol.getType()));
        System.out.println(protocol.getKey().equals(newProtocol.getKey()));
        System.out.println(protocol.getValue().equals(newProtocol.getValue()));
    }
}
