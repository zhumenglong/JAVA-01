package learn.netty.outbound;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.springframework.stereotype.Component;

import static com.google.common.net.HttpHeaders.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;

@Component
public class OutHandleImpl implements OutHandle{
    @Override
    public void handle(FullHttpRequest fullHttpRequest, FullHttpResponse response, ChannelHandlerContext ctx) {
        try {
            response.headers().set("Content-Type", "application/json");
            response.headers().set("Content-Length", response.content().readableBytes());
            response.headers().set("requestFilter", fullHttpRequest.headers().get("requestFilter"));
        } finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    if (response != null) {
                        response.headers().set(CONNECTION, KEEP_ALIVE);
                    }
                    ctx.write(response);
                }
            }
        }
    }
}
