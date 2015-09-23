package com.amazingcoders_android.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import com.j256.ormlite.dao.Dao;

public class DaoHelper<T> {
    protected Dao<T, Long> dao;

    @SuppressWarnings("unchecked")
    public DaoHelper(Dao<T, ?> dao) {
        this.dao = (Dao<T, Long>) dao;
    }

    public List<T> getObjects() {
        return getObjects(null);
    }

    public List<T> getObjects(List<Integer> ids) {
        List<T> objects = null;
        try {
            if (ids == null) {
                objects = dao.queryForAll();
            } else {
                objects = dao.query(
                        dao.queryBuilder().where().in("id", ids).prepare()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public T getObject(long id) {
        T object = null;
        try {
            object = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    }

    public void createOrUpdateObject(T object) {
        try {
            dao.createOrUpdate(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrUpdateObjects(final Collection<T> objects) {
        try {
            dao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (T object : objects) {
                        dao.createOrUpdate(object);
                    }
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteObject(T object) {
        try {
            dao.delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            dao.delete(dao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
