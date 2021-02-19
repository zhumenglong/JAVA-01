package learn.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {
    private FullHttpRequest fullRequest;
    private ChannelHandlerContext ctx;

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        this.fullRequest = fullRequest;
        this.ctx = ctx;
        fullRequest.headers().set("requestFilter", "requestFilter");
    }
}