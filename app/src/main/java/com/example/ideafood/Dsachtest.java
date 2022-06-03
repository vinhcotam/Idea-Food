package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ideafood.Module.Dsach;

import java.util.Random;

public class Dsachtest extends AppCompatActivity {
    ListView lv_dsach;
    Button btn_add_dsach;
    Random random=new Random();
    EditText et_namedsach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsachtest);
        anhXa();
        setClickAdd();
    }

    private void setClickAdd() {
        btn_add_dsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_namedsach.setVisibility(View.VISIBLE);
                et_namedsach.getText().toString().trim();
                String dsachid=String.valueOf(random.nextInt(10000));
                String date= String.valueOf(java.time.LocalDate.now());
                Dsach dsach=new Dsach();

            }
        });
    }

    private void anhXa() {
        btn_add_dsach=findViewById(R.id.btn_add_dsach);
        lv_dsach=findViewById(R.id.lv_dsach);
        et_namedsach=findViewById(R.id.et_namedsach);
    }
}