package com.example.and103_labs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText i_username,i_password,i_remember_password;
    Button btn_rLogin,btn_rBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findID();
        btnRegister();
        btnBack();
    }
    private void findID() {
        i_username = findViewById(R.id.i_username);
        i_password = findViewById(R.id.i_password);
        i_remember_password = findViewById(R.id.i_remember_password);

        btn_rLogin = findViewById(R.id.btn_rLogin);
        btn_rBack = findViewById(R.id.btn_rBack);
    }

    private void btnBack() {
        btn_rBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void btnRegister() {
        btn_rLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i_username.getText().toString().trim().equals("")
                        || i_password.getText().toString().equals("")
                        || i_remember_password.getText().toString().trim().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Không được để trống thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    String username = i_username.getText().toString();
                    String password = i_password.getText().toString();
                    String rememberpassword = i_remember_password.getText().toString();
                    if (password.equals(rememberpassword)) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(username, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công !", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Email đã được sử dụng.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu phải giống nhau!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}