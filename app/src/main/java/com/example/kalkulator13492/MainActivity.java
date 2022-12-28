package com.example.kalkulator13492;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ItemList> Listcontoh;
    private RecyclerView Ulangview;
    private SharedPreferenceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RadioGroup operasiGroup;
    RadioButton tambahRadio, kurangRadio, kaliRadio, bagiRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        buildRecyclerView();
        setInsertButton();

        Button buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        operasiGroup = findViewById(R.id.operasiGroup);
        tambahRadio = findViewById(R.id.tambahRadio);
        kurangRadio = findViewById(R.id.kurangRadio);
        kaliRadio = findViewById(R.id.kaliRadio);
        bagiRadio = findViewById(R.id.bagiRadio);

        operasiGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (tambahRadio.isChecked()) {
                    Toast.makeText(MainActivity.this, "Tambah", Toast.LENGTH_SHORT).show();
                } else if (kurangRadio.isChecked()) {
                    Toast.makeText(MainActivity.this, "Kurang", Toast.LENGTH_SHORT).show();
                } else if (kaliRadio.isChecked()) {
                    Toast.makeText(MainActivity.this, "Kali", Toast.LENGTH_SHORT).show();
                } else if (bagiRadio.isChecked()) {
                    Toast.makeText(MainActivity.this, "Bagi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Listcontoh);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ItemList>>() {
        }.getType();
        Listcontoh = gson.fromJson(json, type);

        if (Listcontoh == null) {
            Listcontoh = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {
        Ulangview = findViewById(R.id.recyclerview);
        Ulangview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SharedPreferenceAdapter(Listcontoh);

        Ulangview.setLayoutManager(mLayoutManager);
        Ulangview.setAdapter(mAdapter);
    }

    private void setInsertButton() {
        Button buttonInsert = findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText line1 = findViewById(R.id.edittext_line_1);
                EditText line2 = findViewById(R.id.edittext_line_2);
                insertItem(line1.getText().toString(), line2.getText().toString());
                saveData();
            }
        });
    }

    private void insertItem(String line1, String line2) {
        int hasil = 0;
        if (tambahRadio.isChecked()) {
            hasil = Integer.parseInt(line1) + Integer.parseInt(line2);
        } else if (kurangRadio.isChecked()) {
            hasil = Integer.parseInt(line1) - Integer.parseInt(line2);
        } else if (kaliRadio.isChecked()) {
            hasil = Integer.parseInt(line1) * Integer.parseInt(line2);
        } else if (bagiRadio.isChecked()) {
            hasil = Integer.parseInt(line1) / Integer.parseInt(line2);
        }

        // memunculkan operasi yang dipilih dari operasiGroup ke dalam hasil
        String operasi = "";
        if (tambahRadio.isChecked()) {
            operasi = "+";
        } else if (kurangRadio.isChecked()) {
            operasi = "-";
        } else if (kaliRadio.isChecked()) {
            operasi = "x";
        } else if (bagiRadio.isChecked()) {
            operasi = ":";
        }

        Listcontoh.add(new ItemList(line1, operasi, line2, String.valueOf(hasil)));
        mAdapter.notifyItemInserted(Listcontoh.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Listcontoh.clear();
        mAdapter.notifyDataSetChanged();
    }

}