package com.example.marionuevo.shoppinglist.addeditproducto;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.marionuevo.shoppinglist.R;
import com.example.marionuevo.shoppinglist.data.Producto;
import com.example.marionuevo.shoppinglist.productos.ProductosActivity;

public class AddEditProductoActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_PRODUCTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_producto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String productoId = getIntent().getStringExtra(ProductosActivity.EXTRA_PRODUCTO_ID);

        setTitle(productoId == null ? "Añadir Producto" : "Editar producto");

        AddEditProductoFragment addEditLawyerFragment = (AddEditProductoFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_producto_container);
        if (addEditLawyerFragment == null) {
            addEditLawyerFragment = AddEditProductoFragment.newInstance(productoId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_producto_container, addEditLawyerFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
