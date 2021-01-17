import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OKHttpDemo {

    public static OkHttpClient client = new OkHttpClient();


    public static void main(String[] args) {
        Request request = new Request.Builder()
                .url("http://localhost:8801")
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
