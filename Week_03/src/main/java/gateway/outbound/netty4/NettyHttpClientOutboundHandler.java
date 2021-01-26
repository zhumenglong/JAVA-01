package gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;


import java.net.InetSocketAddress;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
@ChannelHandler.Sharable
public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        int port = ipSocket.getPort();
        String host = ipSocket.getHostString();
        log.info("与" + host + ":" + port + "连接成功!");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        int port = ipSocket.getPort();
        String host = ipSocket.getHostString();
        ByteBuf byteBuf = (ByteBuf) msg;
        handleResponse( ctx, byteBuf);

    }

    private void handleResponse(final ChannelHandlerContext ctx, final ByteBuf byteBuf) throws Exception {
        FullHttpResponse response = null;
        try {
            byte[] body = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(body);
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            ctx.write(response);
            ctx.flush();
            //ctx.close();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}