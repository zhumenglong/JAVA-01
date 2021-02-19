package learn.netty.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpInitializer extends ChannelInitializer<SocketChannel> {

    @Resource(name = "urls")
    private List<String> backServerUrls;

    public HttpInitializer(List<String> backServerUrls) {
        this.backServerUrls = backServerUrls;
    }

    @Autowired
    HttpInboundServerHandler handler;

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(handler);
        //处理过程
//        p.addLast(new HttpInboundServerHandler(this.backServerUrls));
    }
}