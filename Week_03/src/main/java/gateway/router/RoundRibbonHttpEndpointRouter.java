package gateway.router;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询
 */
@Slf4j
public class RoundRibbonHttpEndpointRouter implements HttpEndpointRouter {

    AtomicInteger index = new AtomicInteger(0);

    @Override
    public String route(List<String> endpoints) {
        String endpoint = endpoints.get(incrementAndGetModulo(endpoints.size()));
        log.info("round ribbon choose:{}",endpoint);
        return endpoint;
    }

    private int incrementAndGetModulo(int serverCount) {
        int andIncrement = index.getAndIncrement();
        if (andIncrement >= Integer.MAX_VALUE) {
            synchronized (this) {
                index = new AtomicInteger(0);
            }
        }
        return andIncrement % serverCount;
    }
}
