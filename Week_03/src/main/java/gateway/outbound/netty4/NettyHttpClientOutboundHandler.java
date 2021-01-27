package gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
// @ChannelHandler.Sharable
public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {

    ChannelHandlerContext gateCtx;

    public NettyHttpClientOutboundHandler(ChannelHandlerContext gateCtx) {
        this.gateCtx = gateCtx;
    }

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
        if (msg instanceof DefaultHttpResponse) {
            DefaultHttpResponse response = (DefaultHttpResponse) msg;
            // ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            gateCtx.write(response).addListener(ChannelFutureListener.CLOSE);
        }
        if (msg instanceof DefaultLastHttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String res = buf.toString(io.netty.util.CharsetUtil.UTF_8);
            buf.release();

            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "application/json");
            response.headers().set(CONTENT_LENGTH, res.getBytes(StandardCharsets.UTF_8).length);
            gateCtx.write(response).addListener(ChannelFutureListener.CLOSE);;
            gateCtx.flush();
        }

    }

}