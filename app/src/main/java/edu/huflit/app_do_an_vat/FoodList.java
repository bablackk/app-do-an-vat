package edu.huflit.app_do_an_vat;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.huflit.app_do_an_vat.Adapter.FoodAdapter;
import edu.huflit.app_do_an_vat.Database.DBHelper;
import edu.huflit.app_do_an_vat.Model.Food;

public class FoodList extends AppCompatActivity {
    SharedPreferences sharedPref;
    DBHelper db = new DBHelper(FoodList.this);
    ArrayList<Food> FoodDataHolder = new ArrayList<>();
    RecyclerView rcvHolder;
    TextView tvHolder;
    ImageView btnBackToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list2);
        rcvHolder = findViewById(R.id.rcv_foodList);
        tvHolder = findViewById(R.id.tvHolder);
        btnBackToHome = findViewById(R.id.back_to_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        sharedPref = getSharedPreferences("my_prefs", FoodList.MODE_PRIVATE);
        String type = sharedPref.getString("product_type","");
        tvHolder.setText(type);
        Log.e(TAG, "onCreate: " + type   );
        Cursor cursor = db.getFoodData();
        if(type.equals("Tất cả")){
            while (cursor.moveToNext()){
                Food obj = new Food(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(6),cursor.getString(4),cursor.getString(3),cursor.getInt(5));
                FoodDataHolder.add(obj);
            }

        }else {
            while (cursor.moveToNext()) {
                if (cursor.getString(3).equals(type)) {
                    Food obj = new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(6), cursor.getString(4), cursor.getString(3), cursor.getInt(5));
                    FoodDataHolder.add(obj);
                }
            }
        }
        cursor.close();
        FoodAdapter foodAdapter = new FoodAdapter(FoodDataHolder,this,db);
        rcvHolder.setLayoutManager(gridLayoutManager);
        rcvHolder.setAdapter(foodAdapter);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodList.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

}