import org.apache.http.client.fluent.Request;
import java.io.IOException;

public class HttpClientDemo {

    public static void main(String[] args) {

        try {
            String s = Request.Get("http://localhost:8801")
                    .connectTimeout(1000)
                    .socketTimeout(1000)
                    .execute().returnContent().asString();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
