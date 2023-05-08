package edu.huflit.app_do_an_vat;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.huflit.app_do_an_vat.API.APIService;
import edu.huflit.app_do_an_vat.API.modelAPI.RegisterRequest;
import edu.huflit.app_do_an_vat.API.modelAPI.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
TextView mtvLogin;
EditText metEmail, metPhone, metPassword, metConfirmPassword;
Button mbtnSignup;
ImageView hidePass, hidePassAgain;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mtvLogin = findViewById(R.id.tvLogin);
        metEmail = findViewById(R.id.etEmail);
        metPhone = findViewById(R.id.etPhone);
        metPassword = findViewById(R.id.etPassword);
        metConfirmPassword = findViewById(R.id.etPassAgain);
        mbtnSignup = findViewById(R.id.btnSignup);
        hidePass = findViewById(R.id.hidePass);
        hidePassAgain = findViewById(R.id.hidePassAgain);
        hidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(metPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    metPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    metPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
        hidePassAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(metConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    metConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    metConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
        mtvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        mbtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!metPassword.getText().toString().equals(metConfirmPassword.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(metEmail.getText().toString().isEmpty() || metPhone.getText().toString().isEmpty() || metPassword.getText().toString().isEmpty() || metConfirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                CallAPI(metEmail.getText().toString(), metPhone.getText().toString(), metPassword.getText().toString());

            }
        });

    }
    public void CallAPI(String email, String phone, String password) {
        RegisterRequest registerRequest = new RegisterRequest(email, phone, password);
        APIService.apiService.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.code() !=200) {
                    Toast.makeText(SignupActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                RegisterResponse registerResponse = response.body();
                Toast.makeText(SignupActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Có lỗi xảy ra với server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}