package com.example.marionuevo.shoppinglist.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

/**
 * Created by MarioNuevo on 29/04/2018.
 */

public class Producto {

    private String id;
    private String name;
    private String description;
    private String photoUri;

    public Producto(String name, String description, String photoUri){
        this.id = UUID.randomUUID().toString();
        this.name=name;
        this.description= description;
        this.photoUri=photoUri;
    }

    public Producto(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(ProductosContract.ProductosEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(ProductosContract.ProductosEntry.NAME));
        description = cursor.getString(cursor.getColumnIndex(ProductosContract.ProductosEntry.DESCRIPTION));
        photoUri = cursor.getString(cursor.getColumnIndex(ProductosContract.ProductosEntry.PHOTO_URI));

    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getPhotoUri() {
        return photoUri;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(ProductosContract.ProductosEntry.ID,id);
        values.put(ProductosContract.ProductosEntry.NAME, name);
        values.put(ProductosContract.ProductosEntry.DESCRIPTION, description);
        values.put(ProductosContract.ProductosEntry.PHOTO_URI, photoUri);
        return values;

    }

}
