package edu.huflit.app_do_an_vat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.huflit.app_do_an_vat.Database.DBHelper;

public class LoginTest extends AppCompatActivity {
    EditText edtUsername,edtPassword,edtNameCustomer,edtPhoneNumber;
    Button btnLogin;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(username.equals("") || password.equals("")) {
                    Toast.makeText(LoginTest.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkAll = db.checkUsernameandpassword(username,password);
                    if(checkAll == true){
                        Toast.makeText(LoginTest.this, "Login successfully" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginTest.this, MainActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(LoginTest.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}