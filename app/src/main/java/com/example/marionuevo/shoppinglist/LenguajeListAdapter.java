/*package com.example.marionuevo.shoppinglist;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.marionuevo.shoppinglist.R.mipmap.ic_launcher;

/**
 * Created by MarioNuevo on 27/04/2018.
 */

/*public class LenguajeListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final ArrayList<Integer> integers;

    public LenguajeListAdapter(Activity context, String[] itemname, ArrayList<Integer> integers) {
        super(context, R.layout.activity_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.integers=integers;
    }

    public View getView(int posicion, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_list,null,true);


        TextView txtTitle =  rowView.findViewById(R.id.txtFirst);
        ImageView imageView =  rowView.findViewById(R.id.icon);
        TextView etxDescripcion =  rowView.findViewById(R.id.txtSecond);

        txtTitle.setText(itemname[posicion]);

        imageView.setImageResource(integers.get(posicion));
        etxDescripcion.setText("Anotación "+itemname[posicion]);

        return rowView;

    }

}
*/