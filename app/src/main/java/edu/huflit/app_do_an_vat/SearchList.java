package edu.huflit.app_do_an_vat;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.huflit.app_do_an_vat.Adapter.FoodAdapter;
import edu.huflit.app_do_an_vat.Database.DBHelper;
import edu.huflit.app_do_an_vat.Model.Food;

public class SearchList extends AppCompatActivity {

    SharedPreferences sharedPref;
    DBHelper db = new DBHelper(SearchList.this);
    ArrayList<Food> FoodDataHolder = new ArrayList<>();
    RecyclerView rcvHolder;
    TextView tvHolder;
    ImageView btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        rcvHolder = findViewById(R.id.rcv_foodListSearch);
        tvHolder = findViewById(R.id.tvHolderSearch);
        btnBackToHome = findViewById(R.id.back_to_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        sharedPref = getSharedPreferences("my_prefs", FoodList.MODE_PRIVATE);
        String search_input = sharedPref.getString("search_input","");
        tvHolder.setText("Các mục liên quan tới " +search_input);
        Cursor cursor = db.getFoodData();
        while(cursor.moveToNext()) {
            if(cursor.getString(2).contains(search_input))  {
                Food obj = new Food(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(6),cursor.getString(4),cursor.getString(3),cursor.getInt(5));
                FoodDataHolder.add(obj);
            }
        }
        cursor.close();
        FoodAdapter foodAdapter = new FoodAdapter(FoodDataHolder,this,db);
        rcvHolder.setLayoutManager(gridLayoutManager);
        rcvHolder.setAdapter(foodAdapter);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchList.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}