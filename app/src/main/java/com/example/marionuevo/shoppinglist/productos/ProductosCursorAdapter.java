package com.example.marionuevo.shoppinglist.productos;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import com.example.marionuevo.shoppinglist.R;
import com.example.marionuevo.shoppinglist.data.ProductosContract;

import static com.example.marionuevo.shoppinglist.R.mipmap.ic_launcher;

/**
 * Created by MarioNuevo on 29/04/2018.
 */

public class ProductosCursorAdapter extends CursorAdapter {

    public ProductosCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_producto, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Referencias UI.
        TextView nameText =  view.findViewById(R.id.tv_name);
        TextView descriptionText = view.findViewById(R.id.tv_description);
        final ImageView photoProducto =  view.findViewById(R.id.iv_photo);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(ProductosContract.ProductosEntry.NAME));
        String description = cursor.getString(cursor.getColumnIndex(ProductosContract.ProductosEntry.DESCRIPTION));
        String photoUri = cursor.getString(cursor.getColumnIndex(ProductosContract.ProductosEntry.PHOTO_URI));

        // Setup.
        nameText.setText(name);
        descriptionText.setText(description);
        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + photoUri))
                .asBitmap()
                .error(ic_launcher)
                .centerCrop()
                .into(new BitmapImageViewTarget(photoProducto) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        photoProducto.setImageDrawable(drawable);
                    }
                });
    }
}
