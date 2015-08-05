package com.sample.orm.ormsample.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.sample.orm.ormsample.database.OrmDatabaseOpenHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Thanh Nguyen on 8/4/2015.
 */
public abstract class DataDao<Model, Id> implements IDataDao<Model, Id> {
    protected Dao<Model, Id> dao;
    private Class<Model> modelClass;

    public DataDao(Context context, Class<Model> modelClass) {
        this.modelClass = modelClass;
        try {
            dao = OpenHelperManager.getHelper(context, OrmDatabaseOpenHelper.class).getDao(modelClass);
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "create DAO failed: " + e.getMessage());
        }
    }

    public boolean isExist(Id id) {
        try {
            return dao.idExists(id);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean insert(Model model) {
        try {
            dao.create(model);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "insert failed: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Model model) {
        try {
            dao.update(model);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "update failed: " + e.getMessage());
            return false;
        }
    }

    public Model getById(Id id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "get by id = " + id + " failed: " + e.getMessage());
            return null;
        }
    }

    public List<Model> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "get all failed: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteById(Id id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "delete by id " + id + " failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAll() {
        try {
            TableUtils.clearTable(dao.getConnectionSource(), modelClass);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "delete all failed: " + e.getMessage());
            return false;
        }
    }
}
