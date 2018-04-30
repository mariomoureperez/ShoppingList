package com.example.marionuevo.shoppinglist.addeditproducto;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marionuevo.shoppinglist.R;
import com.example.marionuevo.shoppinglist.data.Producto;
import com.example.marionuevo.shoppinglist.data.ProductosDBHelper;

public class AddEditProductoFragment extends Fragment {
    private static final String ARG_PRODUCTO_ID = "arg_producto_id";

    private String mProductoId;

    private ProductosDBHelper mProductosDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mDescriptionField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mDescriptionLabel;


    public AddEditProductoFragment() {
        // Required empty public constructor
    }

    public static AddEditProductoFragment newInstance(String lawyerId) {
        AddEditProductoFragment fragment = new AddEditProductoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCTO_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductoId = getArguments().getString(ARG_PRODUCTO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_producto, container, false);

        // Referencias UI
        mSaveButton =  getActivity().findViewById(R.id.fab);
        mNameField =  root.findViewById(R.id.et_name);

        mDescriptionField =  root.findViewById(R.id.et_description);
        mNameLabel =  root.findViewById(R.id.til_name);

        mDescriptionLabel =  root.findViewById(R.id.til_description);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProducto();
            }
        });

        mProductosDbHelper = new ProductosDBHelper(getActivity());

        // Carga de datos
        if (mProductoId != null) {
            loadProducto();
        }

        return root;
    }

    private void loadProducto() {
        // AsyncTask
    }

    private void showProductosScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void addEditProducto() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String description = mDescriptionField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError("Ingresa un valor");
            error = true;
        }

        if (TextUtils.isEmpty(description)) {
            mDescriptionLabel.setError("Ingresa un valor");
            error = true;
        }

        if (error) {
            return;
        }

        Producto producto = new Producto(name, description, "");

        new AddEditProductoTask().execute(producto);

    }

    private class AddEditProductoTask extends AsyncTask<Producto, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Producto... lawyers) {
            if (mProductoId != null) {
                return mProductosDbHelper.updateProductos(lawyers[0], mProductoId) > 0;

            } else {
                return mProductosDbHelper.saveProductos(lawyers[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showProductosScreen(result);
        }

    }

}
