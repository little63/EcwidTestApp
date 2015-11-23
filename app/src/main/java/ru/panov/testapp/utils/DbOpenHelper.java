package ru.panov.testapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ru.panov.testapp.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vetalpanov
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "EcwidTest";

    public static final String ID = "_id";

    public static final String PRODUCT_TABLE_NAME = "products";
    public static final String PRODUCT_VIEW = "viewProducts";
    
    public static final String PRODUCT_FK_TRIGGER = "fk_productcategory_categoryid";

    public static final String PRODUCT_NAME_FIELD = "name";
    public static final String PRODUCT_PRICE_FIELD = "price";
    public static final String PRODUCT_COUNT_FIELD = "count";

    private static final String CREATE_PRODUCT_TABLE = "create table " + PRODUCT_TABLE_NAME + " ( " + ID + " integer primary key autoincrement, " + PRODUCT_NAME_FIELD + " TEXT, " + PRODUCT_COUNT_FIELD + " integer, " + PRODUCT_PRICE_FIELD + " real );";

    public static final String SELECT_PRODUCT_ROWS = "select " + ID + ", " + PRODUCT_NAME_FIELD + ", " + PRODUCT_PRICE_FIELD + ", " + PRODUCT_COUNT_FIELD + " from " + PRODUCT_TABLE_NAME;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase){
        super.onOpen(sqLiteDatabase);
        if (!sqLiteDatabase.isReadOnly()) {
            // Enable foreign key constraints
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);

        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS " + PRODUCT_FK_TRIGGER);
        sqLiteDatabase.execSQL("DROP VIEW IF EXISTS view" + PRODUCT_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addProductItem(ProductItem productItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_NAME_FIELD, productItem.getName());
        Float price = productItem.getPrice();
        if(price != null)
            cv.put(PRODUCT_PRICE_FIELD, price);
        Integer count = productItem.getCount();
        if(count != null)
            cv.put(PRODUCT_COUNT_FIELD, count);

        db.insert(PRODUCT_TABLE_NAME, null, cv);
        db.close();
    }

    public int editProductItem(ProductItem productItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_NAME_FIELD, productItem.getName());
        Float price = productItem.getPrice();
        cv.put(PRODUCT_PRICE_FIELD, price);
        Integer count = productItem.getCount();
        cv.put(PRODUCT_COUNT_FIELD, count);

        int ret = db.update(PRODUCT_TABLE_NAME, cv, ID+"=?",
                new String []{String.valueOf(productItem.getId())});

        db.close();
        return ret;
    }
    
    public void deleteProductItem(ProductItem deletedProductItems){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(PRODUCT_TABLE_NAME,ID+"=?", new String [] {String.valueOf(deletedProductItems.getId())});
        db.close();
    }

    public List<ProductItem> getProductItems() {
        List<ProductItem> ret = new ArrayList<ProductItem>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_PRODUCT_ROWS, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Integer productItemId = cursor.getInt(cursor.getColumnIndex(ID));
                    String name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME_FIELD));
                    Float price = cursor.getFloat(cursor.getColumnIndex(PRODUCT_PRICE_FIELD));
                    Integer count = cursor.getInt(cursor.getColumnIndex(PRODUCT_COUNT_FIELD));

                    ProductItem productItem = new ProductItem(
                            productItemId,
                            name,
                            price,
                            count
                    );

                    ret.add(productItem);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return ret;
    }
}
