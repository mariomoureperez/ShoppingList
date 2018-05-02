package com.example.marionuevo.shoppinglist.productos;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marionuevo.shoppinglist.R;
import com.example.marionuevo.shoppinglist.addeditproducto.AddEditProductoActivity;
import com.example.marionuevo.shoppinglist.data.ProductosContract;
import com.example.marionuevo.shoppinglist.data.ProductosDBHelper;
import com.example.marionuevo.shoppinglist.productodetail.ProductosDetailActivity;


public class ProductosFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_PRODUCTO= 2;

    private ProductosDBHelper mProductosDBHelper;

    private ListView mProductossList;
    private ProductosCursorAdapter mProductosAdapter;
    private FloatingActionButton mAddButton;

    public ProductosFragment() {
        // Required empty public constructor
    }

    public static ProductosFragment newInstance() {
      return new ProductosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos, container, false);

        // Referencias UI
        mProductossList = root.findViewById(R.id.productos_list);
        mProductosAdapter = new ProductosCursorAdapter(getActivity(), null);
        mAddButton = getActivity().findViewById(R.id.fab);

        // Setup
        mProductossList.setAdapter(mProductosAdapter);

        mProductossList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mProductosAdapter.getItem(i);
                String currentProductoId = currentItem.getString(
                        currentItem.getColumnIndex(ProductosContract.ProductosEntry.ID));

                mProductosDBHelper.deleteProductos(currentProductoId);
                loadProductos();
                return true;
            }

        });

        // Eventos
        mProductossList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mProductosAdapter.getItem(i);
                String currentProductoId = currentItem.getString(
                        currentItem.getColumnIndex(ProductosContract.ProductosEntry.ID));

                showDetailScreen(currentProductoId);

            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        // Instancia de helper
        mProductosDBHelper = new ProductosDBHelper(getActivity());

        // Carga de datos
        loadProductos();
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditProductoActivity.REQUEST_ADD_PRODUCTO:
                    showSuccessfullSavedMessage();
                    loadProductos();
                    break;
                case REQUEST_UPDATE_DELETE_PRODUCTO:
                    loadProductos();
                    break;
            }
        }
    }

    private void loadProductos() {
        new ProductosLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductoActivity.class);
        startActivityForResult(intent, AddEditProductoActivity.REQUEST_ADD_PRODUCTO);
    }

    private void showDetailScreen(String productoId) {
        Intent intent = new Intent(getActivity(), ProductosDetailActivity.class);
        intent.putExtra(ProductosActivity.EXTRA_PRODUCTO_ID, productoId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PRODUCTO);}

    private class ProductosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mProductosDBHelper.getAllProductos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mProductosAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }



}
