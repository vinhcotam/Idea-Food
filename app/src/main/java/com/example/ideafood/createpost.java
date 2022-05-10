package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class createpost extends AppCompatActivity {
    EditText et_nd1,et_nd2,et_nd3,et_nd4,et_nd5,et_nd6,et_nd7,et_nd8,et_nd9,et_nd10;
    Button bt_addcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);
        anhXa();
        setClickAddContent();
    }
    int d=1;
    private void setClickAddContent() {

        bt_addcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d++;
                switch (d){
                    case 2:
                        et_nd2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        et_nd3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        et_nd4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        et_nd5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        et_nd6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        et_nd7.setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        et_nd8.setVisibility(View.VISIBLE);
                        break;
                    case 9:
                        et_nd9.setVisibility(View.VISIBLE);
                        break;
                    case 10:
                        et_nd10.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });
    }

    private void anhXa() {
        bt_addcontent=findViewById(R.id.bt_addcontent);
        et_nd1=findViewById(R.id.et_nd1);
        et_nd2=findViewById(R.id.et_nd2);
        et_nd3=findViewById(R.id.et_nd3);
        et_nd4=findViewById(R.id.et_nd4);
        et_nd5=findViewById(R.id.et_nd5);
        et_nd6=findViewById(R.id.et_nd6);
        et_nd7=findViewById(R.id.et_nd7);
        et_nd8=findViewById(R.id.et_nd8);
        et_nd9=findViewById(R.id.et_nd9);
        et_nd10=findViewById(R.id.et_nd10);
        et_nd2.setVisibility(View.INVISIBLE);
        et_nd3.setVisibility(View.INVISIBLE);
        et_nd4.setVisibility(View.INVISIBLE);
        et_nd5.setVisibility(View.INVISIBLE);
        et_nd6.setVisibility(View.INVISIBLE);
        et_nd7.setVisibility(View.INVISIBLE);
        et_nd8.setVisibility(View.INVISIBLE);
        et_nd9.setVisibility(View.INVISIBLE);
        et_nd10.setVisibility(View.INVISIBLE);
    }
}