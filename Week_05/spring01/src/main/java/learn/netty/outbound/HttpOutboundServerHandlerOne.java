package learn.netty.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import learn.netty.filter.HttpResponseFilter;
import learn.netty.router.HttpEndpointRouter;
import learn.netty.router.HttpEndpointRouterImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpOutboundServerHandlerOne {

    private CloseableHttpClient httpclient = HttpClients.createDefault();
    private List<String> backendUrls;
    HttpResponseFilter outFilter;
    FullHttpResponse response;
    @Autowired
    OutHandle outHandle;
    /**
     * 路由转发
     */
    HttpEndpointRouter router;

    public HttpOutboundServerHandlerOne(List<String> backServerUrls) {
        router = HttpEndpointRouterImpl.getRoute();
        this.backendUrls = backServerUrls;
    }

    /**
     * @param fullHttpRequest 输入的请求
     * @param ctx             上下文
     * @param backendUrls
     * @author hongzhengwei
     * @create 2021/1/24 上午11:53
     * @desc 对输入的请求进行转发和处理增强，方法前开启路由
     */
    public void handleTaskOne(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, String urlMsg, List<String> backendUrls) throws IOException {
        String s = router.roundRobin(backendUrls);
        String value = getAsString(s);
        response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
        //开始输出增强
        outHandle.handle(fullHttpRequest, response, ctx);
    }

    public String getAsString(String url) throws IOException {
        //MQ进行解耦，将请求路径发消息出去
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = null;
        try {
            response1 = httpclient.execute(httpGet);
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            return EntityUtils.toString(entity1, "UTF-8");
        } finally {
            if (null != response1) {
                response1.close();
            }
        }
    }

}
