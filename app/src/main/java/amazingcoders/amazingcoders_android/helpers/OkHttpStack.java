package amazingcoders.amazingcoders_android.helpers;

import android.content.Context;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tfleo on 2/17/14.
 */
public class OkHttpStack extends HurlStack {

    private final OkHttpClient client;

    public OkHttpStack(Context context) {
        client = new OkHttpClient();
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        try {
            Cache cache = new Cache(new File(context.getCacheDir(), "http"), cacheSize);
            client.setCache(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        //TODO: don't use okhttp-urlconnection? see https://github.com/square/okhttp/blob/master/CHANGELOG.md#version-200-rc1
        return new OkUrlFactory(client).open(url);
    }
}
