package com.amazingcoders_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import com.amazingcoders_android.models.Owner;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "amazing_db";
    private static final int DB_VERSION = 01;

    private static DatabaseHelper sInstance;

    private DaoHelper<Owner> ownerDao;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        try {

            ownerDao = new DaoHelper<>(getDao(Owner.class));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.i(DatabaseHelper.class.getName(), "Creating database...");
        try {
            TableUtils.createTableIfNotExists(connectionSource, Owner.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(DatabaseHelper.class.getName(), "Upgrading database from " + oldVersion + " to " + newVersion);

        try {
            switch (oldVersion) {
                case 1:
                    TableUtils.createTableIfNotExists(connectionSource, Owner.class);
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't upgrade database", e);
            throw new RuntimeException(e);
        }
    }

    public void clearTables() {
        ownerDao.clear();
        // NOTE put new clear dao methods here
    }

    public DaoHelper<Owner> getOwnerDao() {
        return ownerDao;
    }
}
