package edu.huflit.app_do_an_vat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Successful_Food extends AppCompatActivity {
    Button btnBackToHome;
    TextView tvFoodOrdered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_food);
        btnBackToHome = findViewById(R.id.btn_back_to_home);
        tvFoodOrdered = findViewById(R.id.food_ordered);
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", FoodList.MODE_PRIVATE);
        String quantity = sharedPref.getString("ordered_product_quantity","");
        String name = sharedPref.getString("ordered_product_name","");
        tvFoodOrdered.setText(name + "   x  " + quantity);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Successful_Food.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}