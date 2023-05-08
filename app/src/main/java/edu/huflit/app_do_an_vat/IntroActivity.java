package edu.huflit.app_do_an_vat;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {
Button mbtnStart;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mbtnStart = findViewById(R.id.btnStart);
        mbtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IntroActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if(!token.equals("")) {
            Intent i = new Intent(IntroActivity.this, Home.class);
            startActivity(i);
            finish();
        }
    }
}