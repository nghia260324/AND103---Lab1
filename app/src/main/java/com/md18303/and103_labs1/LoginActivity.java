package com.md18303.and103_labs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button btn_login,btn_register,btn_forgotPassword;
    EditText txt_username,txt_password;
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;
    CheckBox chk_rememberAccount;
    TextInputLayout w_username,w_password;
    ProgressDialog progressDialog;
    ImageButton login_withPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findID();
        btnLogin();
        btnRegister();
        btnForgotPassword();
        login_withPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginWithPhoneNumberActivity.class));
            }
        });
    }
    private void btnForgotPassword() {
        btn_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String getUsername = txt_username.getText().toString().trim();
                if (getUsername.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(getUsername).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra Email để lấy lại Mật khẩu!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void findID() {
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_forgotPassword = findViewById(R.id.btn_forgotPassword);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        chk_rememberAccount = findViewById(R.id.chk_rememberAccount);
        login_withPhoneNumber = findViewById(R.id.login_withPhoneNumber);

        w_username = findViewById(R.id.w_username);
        w_password = findViewById(R.id.w_password);
        progressDialog = new ProgressDialog(this);
    }

    private void btnLogin() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_username.getText().toString().trim().isEmpty() && txt_password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Không được để trống tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                } else {
                    String getUsername = txt_username.getText().toString().trim();
                    String getPassword = txt_password.getText().toString().trim();
                    SignInWithFirebase(getUsername,getPassword);
                }
            }
        });
    }
    private void SignInWithFirebase(String email,String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!",
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    private void btnRegister() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE_EXAMPLE);
            }
        });
    }
}