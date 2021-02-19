package learn.netty.router;

import java.util.List;
import java.util.Random;

public class HttpEndpointRouterImpl implements HttpEndpointRouter {

    private static class RouterHolder {
        private static HttpEndpointRouterImpl singleRoute = new HttpEndpointRouterImpl();
    }

    private static HttpEndpointRouterImpl singleRoute = null;

    public static HttpEndpointRouterImpl getRoute() {
        if (singleRoute == null)
            singleRoute = RouterHolder.singleRoute;
        return RouterHolder.singleRoute;
    }


    @Override
    public String randomRoute(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random(System.currentTimeMillis());
        return endpoints.get(random.nextInt(size));
    }

    /**
     * 轮训的下标
     */
    private int randomRobbinIndex = 0;

    @Override
    public String roundRobin(List<String> endpoints) {
        int index = (randomRobbinIndex + 1) % endpoints.size();
        randomRobbinIndex = randomRobbinIndex + 1;
        System.out.println("本次轮训的下标是：" + index + "游标是：" + randomRobbinIndex);
        return endpoints.get(index);
    }
}
