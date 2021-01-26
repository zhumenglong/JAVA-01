package gateway.outbound.netty4;

import gateway.router.HttpEndpointRouter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

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
        this.channelFutureMap = new HashMap<>(urls.size());
    }

    public void start() {
        log.info("开始启动客户端");
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new NettyHttpClientOutboundHandler() {
                });
        for (int i = 0; i < urls.size(); i++) {
            Pattern p = compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)");
            Matcher m = p.matcher(urls.get(i));
            String host = null;
            String port = null;
            while (m.find()) {
                host = m.group(1);
                port = m.group(2);
            }
            try {
                ChannelFuture channelFuture = bootstrap.connect(host, Integer.parseInt(port)).syncUninterruptibly();
                channelFutureMap.put(urls.get(i), channelFuture);
                log.info("RPC 服务启动客户端完成，监听端口：" + port);
            } catch (Exception e) {
                log.error("RPC 客户端遇到异常", e);
                throw new RuntimeException(e);
            }
        }
    }


    public void execute(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        ChannelFuture channelFuture = channelFutureMap.get(url);
        Channel channel = channelFuture.channel();
        log.info("客户端发送请求，url: {}", url);
        String msg = "{\"msgType\":\"req\",\"clientId\":\"请求数据\"}";
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.getBytes());
        channel.writeAndFlush(buf);
    }
}
