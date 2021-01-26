package gateway.filter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.internal.StringUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class IpHttpRequestFilter implements HttpRequestFilter {

    List<String> blackIps = Arrays.asList("196.128.0.1");

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        String clientIP = fullRequest.headers().get("X-Forwarded-For");
        if (StringUtil.isNullOrEmpty(clientIP)) {
            InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            clientIP = socketAddress.getAddress().getHostAddress();
        }

        if (blackIps.contains(clientIP)) {
            String json = "{\"error msg\":\"FORBIDDEN IP\"}";
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN, Unpooled.wrappedBuffer(json.getBytes(StandardCharsets.UTF_8)));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", json.getBytes(StandardCharsets.UTF_8).length);
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            ctx.flush();
        }
    }
}
