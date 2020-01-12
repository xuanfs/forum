package cn.xuanfs.forum.provider;

import cn.xuanfs.forum.entity.AccessToken;
import cn.xuanfs.forum.entity.GithubUser;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * github登录验证
 * @author xzj
 */
@Component
public class GithubProvider {

    private Gson gson = new Gson();

    /**
     * 获取github的token
     * @param accessToken
     * @return
     */
    public String getAccessToken(AccessToken accessToken){

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, gson.toJson(accessToken));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String[] split = response.body().string().split("&");
            String[] strings = split[0].split("=");
            return strings[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取github用户信息
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = gson.fromJson(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
