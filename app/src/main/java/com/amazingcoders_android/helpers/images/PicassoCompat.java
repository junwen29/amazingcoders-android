package com.amazingcoders_android.helpers.images;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class PicassoCompat {
    private static Picasso singleton = null;

    public static synchronized Picasso with(Context context) {
        // Mimicking Picasso's new OkHttpLoader(context), but with our custom OkHttpClient
        if (singleton == null) {
            OkHttpClient client = new OkHttpClient();
            try {
                client.setCache(createResponseCache(context));
            } catch (IOException ignored) {
            }
            singleton = new Picasso.Builder(context).downloader(new OkHttpDownloader(client)).build();
        }
        return singleton;
    }

    private static File createDefaultCacheDir(Context context) {
        try {
            final Class<?> clazz = Class.forName("com.squareup.picasso.Utils");
            final Method method = clazz.getDeclaredMethod("createDefaultCacheDir", Context.class);
            method.setAccessible(true);
            return (File)method.invoke(null, context);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); // shouldn't happen
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // shouldn't happen
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e); // shouldn't happen
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e); // shouldn't happen
        }
    }

    private static long calculateDiskCacheSize(File dir) {
        try {
            final Class<?> clazz = Class.forName("com.squareup.picasso.Utils");
            final Method method = clazz.getDeclaredMethod("calculateDiskCacheSize", File.class);
            method.setAccessible(true);
            return (Long) method.invoke(null, dir);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); // shouldn't happen
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // shouldn't happen
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e); // shouldn't happen
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e); // shouldn't happen
        }
    }

    private static Cache createResponseCache(Context context) throws IOException {
        File cacheDir = createDefaultCacheDir(context);
        long maxSize = calculateDiskCacheSize(cacheDir);
        return new Cache(cacheDir, maxSize);
    }
}
