package com.example.marionuevo.shoppinglist.productodetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.marionuevo.shoppinglist.R;
import com.example.marionuevo.shoppinglist.addeditproducto.AddEditProductoActivity;
import com.example.marionuevo.shoppinglist.data.Constants;
import com.example.marionuevo.shoppinglist.data.Producto;
import com.example.marionuevo.shoppinglist.data.ProductosDBHelper;
import com.example.marionuevo.shoppinglist.productos.ProductosActivity;
import com.example.marionuevo.shoppinglist.productos.ProductosFragment;

public class ProductosDetailFragment extends Fragment {

    private static final String ARG_PRODUCTO_ID = "productoId";
    private String mProductoId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mPhoto;
    private TextView mName;
    private TextView mDescription;

    private ProductosDBHelper productosDBHelper;


    public ProductosDetailFragment() {
        // Required empty public constructor
    }

    public static ProductosDetailFragment newInstance(String lawyerId) {
        ProductosDetailFragment fragment = new ProductosDetailFragment();
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

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos_detail, container, false);
        mCollapsingView = getActivity().findViewById(R.id.toolbar_layout);
        mPhoto = getActivity().findViewById(R.id.iv_photo);
        mName = root.findViewById(R.id.tv_name);
        mDescription = root.findViewById(R.id.tv_description);


        productosDBHelper = new ProductosDBHelper(getActivity());

        loadProductos();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ProductosFragment.REQUEST_UPDATE_DELETE_PRODUCTO) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }


    private void loadProductos() {
        new GetProductosByIdTask().execute();
    }

    private void showProducto(Producto producto) {
        mCollapsingView.setTitle(producto.getName());
        Glide.with(this)
                .load(Uri.parse("file:///"+ Constants.URI_PHOTOS+ producto.getPhotoUri()))
                .centerCrop()
                .into(mPhoto);
        mName.setText(producto.getName());
        mDescription.setText(producto.getDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar producto", Toast.LENGTH_SHORT).show();
    }

    private void showProductosScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductoActivity.class);
        intent.putExtra(ProductosActivity.EXTRA_PRODUCTO_ID, mProductoId);
        startActivityForResult(intent, ProductosFragment.REQUEST_UPDATE_DELETE_PRODUCTO);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteLawyerTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetProductosByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return productosDBHelper.getProductosById(mProductoId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showProducto(new Producto(cursor));
            } else {
                showLoadError();
            }
        }

    }


    private class DeleteLawyerTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return productosDBHelper.deleteProductos(mProductoId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showProductosScreen(integer > 0);
        }

    }


}
