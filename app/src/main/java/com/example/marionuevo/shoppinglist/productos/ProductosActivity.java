package com.example.marionuevo.shoppinglist.productos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.marionuevo.shoppinglist.R;

public class ProductosActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCTO_ID = "extra_producto_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProductosFragment fragment = (ProductosFragment)
                getSupportFragmentManager().findFragmentById(R.id.productos_container);

        if (fragment == null) {
            fragment = ProductosFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.productos_container, fragment)
                    .commit();
        }

}
}
