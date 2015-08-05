package com.sample.orm.ormsample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sample.orm.ormsample.model.Contact;

import java.sql.SQLException;

/**
 * Created by Thanh Nguyen on 8/4/2015.
 */
public class OrmDatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private static final String DB_NAME = "contact.db";
    private static final int DB_VERSION = 1;

    public OrmDatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Contact.class);
        } catch (SQLException e) {
            Log.e(TAG, "Can't create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Contact.class, true);
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop database", e);
        }
    }
}
