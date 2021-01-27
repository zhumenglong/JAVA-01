## 作业内容

> Week03 作业题目（周三）：

1）周三作业：（必做）整合你上次作业的httpclient/okhttp；
[HttpOutboundHandler.java](src/main/java/gateway/outbound/httpclient4/HttpOutboundHandler.java)

2）周三作业（可选）:使用netty实现后端http访问（代替上一步骤）；
[NettyHttpClient.java](src/main/java/gateway/outbound/netty4/NettyHttpClient.java)

```java
public class HttpOutboundHandler {
    
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        filter.filter(fullRequest, ctx);
        // proxyService.submit(() -> fetchGet(fullRequest, ctx, url));

        /**
         * 使用netty实现后端http访问
         */
        try {
            NettyHttpClient.connect(url, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

> Week03 作业题目（周五）：

3）周日作业：（必做）实现过滤器
[IpHttpRequestFilter.java](src/main/java/gateway/filter/IpHttpRequestFilter.java)

4）周日作业（可选）：实现路由
[RoundRibbonHttpEndpointRouter.java](src/main/java/gateway/router/RoundRibbonHttpEndpointRouter.java)