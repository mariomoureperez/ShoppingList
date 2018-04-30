package com.example.marionuevo.shoppinglist.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MarioNuevo on 29/04/2018.
 */

public class ProductosDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Productos.db";

    public ProductosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ProductosContract.ProductosEntry.TABLE_NAME+" ("
        + ProductosContract.ProductosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + ProductosContract.ProductosEntry.ID + " TEXT NOT NULL,"
        + ProductosContract.ProductosEntry.NAME + " TEXT NOT NULL,"
        + ProductosContract.ProductosEntry.DESCRIPTION + " TEXT NOT NULL,"
        + ProductosContract.ProductosEntry.PHOTO_URI + " TEXT,"
        + "UNIQUE (" + ProductosContract.ProductosEntry.ID + "))"
        );

        mockData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockProductos(sqLiteDatabase, new Producto("Pasta", "Macarrones añsld",
                "macarrones.jpg"));
        mockProductos(sqLiteDatabase, new Producto("Leche", "Entera y desnatada",
                "leche.jpg"));
        mockProductos(sqLiteDatabase, new Producto("Tomate", "1 kilo",
                "tomate.jpg"));
        mockProductos(sqLiteDatabase, new Producto("Patatas", "Patatas blancas",
                "patatas.jpg"));
        mockProductos(sqLiteDatabase, new Producto("Pan", "300 gramos",
                "pan.jpg"));
        mockProductos(sqLiteDatabase, new Producto("aa", "300 gramos",
                "pan.jpg"));
        mockProductos(sqLiteDatabase, new Producto("Paaan", "300 gramos",
                "pan.jpg"));
        mockProductos(sqLiteDatabase, new Producto("aaaaagg", "300 gramos",
                "pan.jpg"));

    }

    public long mockProductos(SQLiteDatabase db, Producto producto){
        return db.insert(ProductosContract.ProductosEntry.TABLE_NAME,null,producto.toContentValues());
    }

    public long saveProductos(Producto producto){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(ProductosContract.ProductosEntry.TABLE_NAME,null,producto.toContentValues());
    }

    public Cursor getAllProductos(){
        return getReadableDatabase().query(ProductosContract.ProductosEntry.TABLE_NAME,
                null,null,null,null,null,null);
    }
    public Cursor getProductosById(String productoId){
        Cursor cursor = getReadableDatabase().query(
                ProductosContract.ProductosEntry.TABLE_NAME,null,
                ProductosContract.ProductosEntry.ID+ " LIKE ?",
                new String[]{productoId},null,null,null);
        return cursor;
    }

    public int deleteProductos(String lawyerId) {
        return getWritableDatabase().delete(
                ProductosContract.ProductosEntry.TABLE_NAME,
                ProductosContract.ProductosEntry.ID + " LIKE ?",
                new String[]{lawyerId});
    }

    public int updateProductos(Producto producto, String productoId) {
        return getWritableDatabase().update(
                ProductosContract.ProductosEntry.TABLE_NAME,
                producto.toContentValues(),
                ProductosContract.ProductosEntry.ID + " LIKE ?",
                new String[]{productoId}
        );
    }
}
