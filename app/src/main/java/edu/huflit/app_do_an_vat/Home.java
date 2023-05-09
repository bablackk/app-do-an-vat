package edu.huflit.app_do_an_vat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.huflit.app_do_an_vat.Adapter.FoodPopularAdapter;
import edu.huflit.app_do_an_vat.Database.DBHelper;
import edu.huflit.app_do_an_vat.Model.Food;
import edu.huflit.app_do_an_vat.Model.Topping;

public class Home extends AppCompatActivity {

    Button btnTraSua,btnBanhNgot,btnTraTraiCay,btnCaPhe,btnAdd;
    TextView tvTypeFood;
    ImageView img_Search;
    ArrayList<Food> PopularFoodDataHolder = new ArrayList<>();
    EditText edtSearch;
    DBHelper db = new DBHelper(this);
    RecyclerView rcvPopular;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fragment bar = new bottom_bar();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        fragmentTransaction.add(R.id.action_bar, bar).commit();
        btnBanhNgot = findViewById(R.id.btnBanhNgot);
        btnCaPhe= findViewById(R.id.btnCoffe);
        btnTraSua = findViewById(R.id.btnTraSua);
        btnTraTraiCay = findViewById(R.id.btnTraTraiCay);
        rcvPopular = findViewById(R.id.rcv_Product_popular);
        btnAdd = findViewById(R.id.btnADD);
        img_Search = findViewById(R.id.img_Search);
        edtSearch = findViewById(R.id.edt_search);
        if(db.getTopping().getCount() == 0) {
            createToppingData();
        }

        Cursor cursor = db.getFoodData();
        while(cursor.moveToNext()) {
            if(cursor.getString(4).equals("Popular")){
                Food obj = new Food(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(6),cursor.getString(4),cursor.getString(3),cursor.getInt(5));
                PopularFoodDataHolder.add(obj);
            }
        }
        rcvPopular.setLayoutManager(gridLayoutManager);
        FoodPopularAdapter foodPopularAdapter = new FoodPopularAdapter(PopularFoodDataHolder,this);

        rcvPopular.setAdapter(foodPopularAdapter);

        btnTraTraiCay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = Home.this.getSharedPreferences("my_prefs",Home.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("product_type", btnTraTraiCay.getText().toString());
                editor.apply();
                Intent i = new Intent(Home.this,FoodList.class);
                startActivity(i);


            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,AddFood.class);
                startActivity(i);
            }
        });
        btnTraSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = Home.this.getSharedPreferences("my_prefs",Home.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("product_type", btnTraSua.getText().toString());
                editor.apply();
                Intent i = new Intent(Home.this,FoodList.class);
                startActivity(i);


            }
        });
        btnCaPhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = Home.this.getSharedPreferences("my_prefs",Home.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("product_type", btnCaPhe.getText().toString());
                editor.apply();
                Intent i = new Intent(Home.this,FoodList.class);
                startActivity(i);


            }
        });
        btnBanhNgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = Home.this.getSharedPreferences("my_prefs",Home.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("product_type", btnBanhNgot.getText().toString());
                editor.apply();
                Intent i = new Intent(Home.this,FoodList.class);
                startActivity(i);


            }
        });
        img_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = Home.this.getSharedPreferences("my_prefs",Home.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("search_input", edtSearch.getText().toString());
                editor.apply();
                Intent i = new Intent(Home.this,SearchList.class);
                startActivity(i);
                edtSearch.setText("");
            }
        });



    }
    public void createToppingData() {

        DBHelper db = new DBHelper(this);

        db.insertToppingData(1,"https://cdn.discordapp.com/attachments/941739362222211072/1105049624303108176/1123.png","Topping test 1",12000,0);

        db.insertToppingData(2,"https://cdn.discordapp.com/attachments/941739362222211072/1105049624303108176/1123.png","Topping test 2",14000,0);

        db.insertToppingData(3,"https://cdn.discordapp.com/attachments/941739362222211072/1105049624303108176/1123.png","Topping test 3",16000,0);
    }
}