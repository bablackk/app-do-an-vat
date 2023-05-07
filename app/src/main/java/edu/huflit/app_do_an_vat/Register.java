package edu.huflit.app_do_an_vat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.huflit.app_do_an_vat.Database.DBHelper;

public class Register extends AppCompatActivity {
    EditText edtUsername,edtPassword,edtName,edtSDT;
    Button btnRegister;

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = findViewById(R.id.username_register);
        edtPassword = findViewById(R.id.password_register);
        edtName = findViewById(R.id.name_register);
        edtSDT = findViewById(R.id.SDT_register);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String name = edtName.getText().toString();
                String sdt = edtSDT.getText().toString();
                DB = new DBHelper(Register.this);
                if(username.equals("") || password.equals("") || name.equals("") || sdt.equals("")) {
                    Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    Boolean checkuser = DB.checkUsername(username);
                    if(checkuser == false) {
                        Boolean insert = DB.insertCustomerData(username,password,name,sdt);
                        if(insert == true){
                            Toast.makeText(Register.this, "REGISTER SUCCESSFULLy"  , Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Register.this, LoginTest.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(Register.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Register.this, " User already exists! " ,  Toast.LENGTH_SHORT).show();
                    }

                }





            }
        });
    }

}