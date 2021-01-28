package gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> proxyServer;

    public HttpInboundInitializer(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }


    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}

        /**
         * 调用#new HttpServerCodec()方法，编解码器支持部分 HTTP 请求解析，比如 HTTP GET请求所传递的参数是包含在 uri 中的，因此通过 HttpRequest 既能解析出请求参数。
         *
         * HttpRequestDecoder  | 即把 ByteBuf 解码到 HttpRequest 和 HttpContent。
         * HttpResponseEncoder | 即把 HttpResponse 或 HttpContent 编码到 ByteBuf。
         * HttpServerCodec     | HttpRequestDecoder 和 HttpResponseEncoder 的结合。
         */
        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        /**
         * 调用#new HttpObjectAggregator(1024*1024)方法,即通过它可以把 HttpMessage 和 HttpContent 聚合成一个 FullHttpRequest 或者 FullHttpResponse （取决于是处理请求还是响应），
         * 而且它还可以帮助你在解码时忽略是否为“块”传输方式。因此，在解析 HTTP POST 请求时，请务必在 ChannelPipeline 中加上 HttpObjectAggregator。（具体细节请自行查阅代码）
         */
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpInboundHandler(this.proxyServer));
    }
}