package com.example.marionuevo.shoppinglist.addeditproducto;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.marionuevo.shoppinglist.BuildConfig;
import com.example.marionuevo.shoppinglist.R;
import com.example.marionuevo.shoppinglist.data.Constants;
import com.example.marionuevo.shoppinglist.data.Producto;
import com.example.marionuevo.shoppinglist.data.ProductosDBHelper;
import com.example.marionuevo.shoppinglist.productos.ProductosCursorAdapter;

import java.io.File;
import java.text.SimpleDateFormat;

public class AddEditProductoFragment extends Fragment {
    private static final String ARG_PRODUCTO_ID = "arg_producto_id";

    private String mProductoId;

    private ProductosDBHelper mProductosDbHelper;

    private ProductosCursorAdapter mProductosAdapter;

    private FloatingActionButton mCamera;
    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mDescriptionField;
    private ImageView mPhoto;
    private TextInputLayout mNameLabel;
    private TextInputLayout mDescriptionLabel;
    private String namePhoto;



    private static final int REQUEST_CODE = 100;


    public AddEditProductoFragment() {
        // Required empty public constructor
    }

    public static AddEditProductoFragment newInstance(String productosId) {
        AddEditProductoFragment fragment = new AddEditProductoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCTO_ID, productosId);
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
        mCamera = getActivity().findViewById(R.id.camera);



        mDescriptionField =  root.findViewById(R.id.et_description);
        mNameLabel =  root.findViewById(R.id.til_name);

        mDescriptionLabel =  root.findViewById(R.id.til_description);

        namedPhoto();

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProducto();
            }
        });
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo(namePhoto);

            }
        });

        mProductosDbHelper = new ProductosDBHelper(getActivity());

        // Carga de datos
        if (mProductoId != null) {
            loadProducto();
        }

        return root;
    }

    private void namedPhoto(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        namePhoto=timeStamp+".jpg";
    }

    private void loadProducto() {
        new GetProductoByIdTask().execute();
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

        Producto producto = new Producto(name, description,namePhoto);

        new AddEditProductoTask().execute(producto);

    }

    private void showProducto(Producto producto) {
        mPhoto = getActivity().findViewById(R.id.iv_photo);
        mNameField.setText(producto.getName());
        try{
            Glide.with(this)
                    .load(Uri.parse("file:///"+ Constants.URI_PHOTOS + producto.getPhotoUri()))
                    .centerCrop()
                    .into(mPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }

        mDescriptionField.setText(producto.getDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar Producto", Toast.LENGTH_SHORT).show();
    }

    private class AddEditProductoTask extends AsyncTask<Producto, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Producto... prductos) {
            if (mProductoId != null) {
                return mProductosDbHelper.updateProductos(prductos[0], mProductoId) > 0;

            } else {
                return mProductosDbHelper.saveProductos(prductos[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showProductosScreen(result);
        }

    }

    private class GetProductoByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mProductosDbHelper.getProductosById(mProductoId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showProducto(new Producto(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    public void photo(String name){


        File newdir = new File(Constants.URI_PHOTOS);
        if (!newdir.exists()) {
            newdir.mkdir();
        }
        String file = Constants.URI_PHOTOS+name;
        File newFile = new File(file);

        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID+".com.example.marionuevo.shoppinglist", newFile);

        Intent capturarImagenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        capturarImagenIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //Empezamos el activity para que se nos abra la cámara.
        startActivityForResult(capturarImagenIntent,
                REQUEST_CODE);


    }


}
