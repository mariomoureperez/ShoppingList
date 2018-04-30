/*package com.example.marionuevo.shoppinglist;

import android.app.Activity;
import android.app.ListActivity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.example.marionuevo.shoppinglist.R.mipmap.ic_launcher;

public class MainActivity extends Activity {

    private String shoppingList[] = new String[]{"Leche", "Agua", "Pasta", "Pan", "Tomates", "Patatas",
            "Harina","Leche","Agua","Pasta","Pan","Tomates","Patatas",
            "Harina"
    };

    private ArrayList<Integer> imagId = new ArrayList<>();


    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addArray();

        int lenghtImage = imagId.size();
        int lenghtList = shoppingList.length;
        if (lenghtImage < lenghtList) {

            for (int i = 0; i < lenghtList - lenghtImage; i++) {

                imagId.add(ic_launcher);

            }
        }

        LenguajeListAdapter adapter = new LenguajeListAdapter(this, shoppingList, imagId);
        list = findViewById(R.id.shopping);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem = shoppingList[+position];
                Toast.makeText(getApplicationContext(), Slecteditem + "\n ¿ Eliminar ? ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addArray() {
        imagId.add(R.drawable.leche);
        imagId.add(R.drawable.agua);
        imagId.add(R.drawable.macarrones);
        imagId.add(R.drawable.pan);
        imagId.add(R.drawable.tomate);
        imagId.add(R.drawable.patatas);
        imagId.add(R.drawable.harina);


    }
}
*/