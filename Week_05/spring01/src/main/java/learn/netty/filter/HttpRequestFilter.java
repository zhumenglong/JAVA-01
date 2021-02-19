package learn.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {

    /**
     * @desc 对输入进行过滤，ctx为上下文
     **/
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
}
