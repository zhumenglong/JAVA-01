package learn.netty.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {

    /**
     * 返回增强
     *
     * @param response
     */
    void filter(FullHttpResponse response);
}
