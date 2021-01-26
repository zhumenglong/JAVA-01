package gateway.outbound.netty4;

import gateway.router.HttpEndpointRouter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class NettyHttpClient {

    /**
     * channel 信息
     *
     * @since 0.0.4
     */
    private Map<String, ChannelFuture> channelFutureMap;

    private NettyHttpClientOutboundHandler httpClientOutboundHandler;

    private List<String> urls;

    public NettyHttpClient(List<String> urls) {
        this.urls = urls;
    }

    public void start() {
        log.info("开始启动客户端");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();


            ChannelFuture channelFuture = bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new NettyHttpClientOutboundHandler() {
                    })
                    .connect("RpcConstant.ADDRESS", 1111)
                    .syncUninterruptibly();

            channelFutureMap.put("ip",channelFuture);
            log.info("RPC 服务启动客户端完成，监听端口：" + 1111);
        } catch (Exception e) {
            log.error("RPC 客户端遇到异常", e);
            throw new RuntimeException(e);
        }
    }


    public void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {

        String ip = url.split(":")[0];
        ChannelFuture channelFuture = channelFutureMap.get(ip);
        Channel channel = channelFuture.channel();
        log.info("客户端发送请求，url: {}", url);

    }
}
