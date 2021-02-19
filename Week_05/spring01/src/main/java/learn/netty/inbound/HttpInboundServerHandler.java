package learn.netty.inbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import learn.netty.filter.HeaderHttpRequestFilter;
import learn.netty.filter.HttpRequestFilter;
import learn.netty.outbound.HttpOutboundServerHandlerOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ChannelHandler.Sharable
public class HttpInboundServerHandler extends ChannelInboundHandlerAdapter{

    /**
     * 输入过滤器
     */
    private HttpRequestFilter inputFilter;

    /**
     * 后台请求处理
     */
    private HttpOutboundServerHandlerOne handlerOne;

    public HttpInboundServerHandler(List<String> backServerUrls) {
        inputFilter = new HeaderHttpRequestFilter();
        handlerOne = new HttpOutboundServerHandlerOne(backServerUrls);
    }

    /**
     * @desc 读取输入的数据
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("开始读取channel内的数据流; 接受到:" + msg);
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            handlerOne.handleTaskOne(fullHttpRequest, ctx, "", handlerOne.getBackendUrls());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * channel读取完毕事件
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channelReadComplete..");
        //写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * @desc 异常的处理机制
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭发生异常的连接
        System.out.println("server occur exception:" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

}
