package learn.bytebuddy;

import java.util.Random;

public class BizMethod {

    public String queryUserInfo(String uid, String token) throws InterruptedException {
        Thread.sleep(new Random().nextInt(500));
        return "蒙多，想去哪儿就去哪儿";
    }

}
