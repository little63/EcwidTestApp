package ru.panov.testapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by vetalpanov on 09.12.15.
 */

public class DBManager {
    private static DBManager instance;

    public SQLiteDatabase db;
    public ProductDao productDao;

    public static synchronized DBManager getInstance( Context context ) {
        if (instance == null) {
            instance = new DBManager( context );
        }
        return instance;
    }

    public DBManager( Context context ) {
        //get database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper( context, "ecwid-test-db", null );
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        productDao = daoSession.getProductDao();
    }

    public List getProducts(){
        return productDao.loadAll();
    }

    public void removeProduct( Product product ){
        productDao.delete( product );
    }

    public void editProduct( Product product ){
        productDao.update( product );
    }

    public void addProduct( Product product ){
        productDao.insert( product );
    }
}
