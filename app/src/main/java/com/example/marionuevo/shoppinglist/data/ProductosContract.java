package com.example.marionuevo.shoppinglist.data;

import android.provider.BaseColumns;

/**
 * Created by MarioNuevo on 29/04/2018.
 */

public class ProductosContract {

    public static abstract class ProductosEntry implements BaseColumns{
        public static final String TABLE_NAME = "Productos";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String PHOTO_URI = "photoUri";
    }
}
