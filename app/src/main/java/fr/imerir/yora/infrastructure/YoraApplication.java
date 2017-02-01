package fr.imerir.yora.infrastructure;

import android.app.Application;
import android.net.Uri;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.otto.Bus;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import fr.imerir.yora.services.Module;


public class YoraApplication extends Application {
    public static final Uri API_ENDPOINT = Uri.parse("http://yora-playground.3dbuzz.com");
    public static final String STUDENT_TOKEN = "6a426103394443c5a3ec09e6ed9509f1";

    private Auth auth;
    private Bus bus;
    private Picasso authedPicasso;

    public YoraApplication() {

        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
        creatAuthedPicasso();
        Module.register(this);
    }

    private void creatAuthedPicasso() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + getAuth().getAuthToken())
                        .addHeader("X-Student", STUDENT_TOKEN)
                        .build();

                return chain.proceed(newRequest);
            }
        });

        authedPicasso = new Picasso.Builder(this)
                .downloader(new OkHttpDownloader(client))
                .build();
    }

    public Picasso getAuthedPicasso() {

        return authedPicasso;
    }

    public Auth getAuth() {
        return auth;
    }

    public Bus getBus() {
        return bus;
    }
}
