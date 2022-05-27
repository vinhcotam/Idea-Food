    package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

    public class MainActivity extends AppCompatActivity {
    TextView tv_dky;
    EditText edt_tk,edt_mk;
    FirebaseAuth fb;
    Button btn_dnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_dky = findViewById(R.id.tv_dangky);
        edt_tk = findViewById(R.id.edT_taikhoan);
        edt_mk = findViewById(R.id.edT_matkhau);
        btn_dnhap = findViewById(R.id.btn_dangnhap);
        fb = FirebaseAuth.getInstance();
        btn_dnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edt_tk.getText().toString())){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập tài khoản!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_mk.getText().toString())){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập mật khẩu!!",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        tv_dky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,dangky.class);
                startActivity(intent);
            }
        });
    }
}