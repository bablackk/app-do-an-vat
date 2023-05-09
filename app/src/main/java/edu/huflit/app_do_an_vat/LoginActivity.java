package edu.huflit.app_do_an_vat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.huflit.app_do_an_vat.API.APIService;
import edu.huflit.app_do_an_vat.API.modelAPI.LoginRequest;
import edu.huflit.app_do_an_vat.API.modelAPI.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView mtvSignup;
    EditText metEmail, metPassword;
    Button btnLogin;
    ImageView mhidePassword;
    SharedPreferences sharedPreferences;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mtvSignup = findViewById(R.id.tvSignup);
        metEmail = findViewById(R.id.etEmail);
        metPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        mhidePassword = findViewById(R.id.hidePass);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if(!token.equals("")) {
            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
            finish();
        }
        mtvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        mhidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // feature hide password
                if(metPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    metPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    metPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // feature login
                if(metEmail.getText().toString().isEmpty() || metPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // call api login
                    CallAPILogin(metEmail.getText().toString(), metPassword.getText().toString());
                }
            }
        });
    }
    public void CallAPILogin(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        APIService.apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() != 200) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginResponse loginResponse= response.body();
                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", loginResponse.getToken());
                editor.putString("email", loginResponse.getEmail());
                editor.putString("name", loginResponse.getPhone());
                editor.putString("role", loginResponse.getRole());
                editor.apply();
                Log.e("token", loginResponse.getToken());
                Intent i = new Intent(LoginActivity.this, Home.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}