package learn.netty.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface OutHandle {

    void handle(FullHttpRequest fullHttpRequest, FullHttpResponse response, ChannelHandlerContext ctx);

}
