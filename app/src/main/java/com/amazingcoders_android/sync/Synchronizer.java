package com.amazingcoders_android.sync;

import android.util.Log;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by junwen29 on 9/25/2015.
 */
public abstract class Synchronizer<T extends Synchronizable> {

    private WeakHashMap<T, Long> map = new WeakHashMap<T, Long>();

    public void put(Collection<T> objects) {
        for (T o : objects) {
            put(o);
        }
    }

    public void put(T object) {
        if (object == null) return;
        map.put(object, object.id());
    }

    public void remove(Collection<T> objects) {
        for (T o : objects) {
            map.remove(o);
        }
    }

    public void remove(T object) {
        if (object == null) return;
        map.remove(object);
    }

    public boolean has(T object) {
        return map.containsKey(object);
    }

    public boolean has(long id) {
        return map.containsValue(id);
    }

    public Set<T> getObjects() {
        return map.keySet();
    }

    public void clear() {
        map.clear();
    }

    public void peek() {
        for (Map.Entry<T, Long> entry : map.entrySet()) {
            Log.d(getClass().getSimpleName(), entry.getKey().toString() + " " + entry.getValue().toString());
        }
    }
}
