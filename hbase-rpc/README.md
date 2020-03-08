
# HBase RPC

## Netty

channel 连上之后，先write了个header

### handler

BufferCallBeforeInitHandler

NettyRpcDuplexHandler

LengthFieldBasedFrameDecoder  (inbound)

IdleStateHandler 心跳
