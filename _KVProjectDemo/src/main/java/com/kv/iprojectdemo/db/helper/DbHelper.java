package com.kv.iprojectdemo.db.helper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kv.iprojectdemo.db.model.Person;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    /* 数据库名称 */
    private static final String DB_NAME = "iporj_test.db";

    /* 数据库版本号 */
    private static final int DB_VERSION = 1;

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Person.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Person.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DbHelper instance;

    /** 
     * 单例获取该Helper 
     *  
     * @param context 
     * @return 
     */
    public static synchronized DbHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            instance = new DbHelper(context);
        }

        return instance;
    }

    /**
     * 获取数据操作类.
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /** 
     * 释放资源 
     */
    @Override
    public void close() {
        super.close();

        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
        instance = null;
    }

}
