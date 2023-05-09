package edu.huflit.app_do_an_vat;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import edu.huflit.app_do_an_vat.Adapter.ToppingAdapter;
import edu.huflit.app_do_an_vat.Database.DBHelper;
import edu.huflit.app_do_an_vat.Model.Topping;

public class DetailFood extends AppCompatActivity {
    SharedPreferences sharedPref;
    TextView tvNameProduct,tvPrice,tvDescribe,tvAmountProduct;
    ArrayList<Topping> toppingDataHolder = new ArrayList<>();

    ImageView imgvProduct;
    RecyclerView rcv_topping;
    int realTimeAmount,priceInt;
    int totalprice;
    Button btnExit,btn_add_to_cart,btnPlus,btnSubtract;
    DBHelper db= new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        tvNameProduct = findViewById(R.id.tv_name_prodcuct);
        tvPrice = findViewById(R.id.tv_name_price);
        tvDescribe = findViewById(R.id.tv_title_product);
        imgvProduct = findViewById(R.id.img_product);
        btnExit = findViewById(R.id.btn_Exit);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        btnPlus =findViewById(R.id.btn_add_product);
        btnSubtract = findViewById(R.id.btn_subtract_product);
        rcv_topping = findViewById(R.id.rcv_topping);
        tvAmountProduct = findViewById(R.id.tvAmountProduct);

        LinearLayoutManager ln1 = new LinearLayoutManager(this);
        sharedPref = getSharedPreferences("my_prefs", FoodList.MODE_PRIVATE);
        String type = sharedPref.getString("product_type","");
        String name = sharedPref.getString("product_name","");
        String url = sharedPref.getString("product_url","");
        String price = sharedPref.getString("product_price","");
        String describe = sharedPref.getString("product_describe","");
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailFood.this,Home.class);
                startActivity(i);
                finish();
            }
        });
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = vndFormat.format(Integer.valueOf(price));
        priceInt = Integer.parseInt(price);
        tvNameProduct.setText(name);
        tvPrice.setText(formattedPrice);
        tvDescribe.setText(describe);
        imgvProduct.getLayoutParams().width = 450;
        imgvProduct.getLayoutParams().height = 600;
        Picasso.get().load(url).resize(imgvProduct.getLayoutParams().width,imgvProduct.getLayoutParams().height).centerCrop().into(imgvProduct);
        updateBuyStatus();
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realTimeAmount = Integer.valueOf(tvAmountProduct.getText().toString())+1;
                tvAmountProduct.setText(String.valueOf(realTimeAmount));
                updateBuyStatus();
            }
        });
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realTimeAmount = (Integer.valueOf(tvAmountProduct.getText().toString()))-1;
                if(realTimeAmount <1) {
                    realTimeAmount =1;
                }
                tvAmountProduct.setText(String.valueOf(realTimeAmount));
                updateBuyStatus();
            }
        });
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref = getSharedPreferences("my_prefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("ordered_product_name", name);
                editor.putString("ordered_product_quantity", tvAmountProduct.getText().toString());
                editor.apply();
                Intent i = new Intent(DetailFood.this,Successful_Food.class);
                startActivity(i);
            }
        });
        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getTopping();
        while(cursor.moveToNext()) {
            int stt = db.getTopping().getCount();
            Topping obj = new Topping(stt+1,cursor.getString(1),cursor.getString(2),cursor.getInt( 3));
            toppingDataHolder.add(obj);
        }
        rcv_topping.setLayoutManager(ln1);
        ToppingAdapter toppingAdapter = new ToppingAdapter(toppingDataHolder,this);
        rcv_topping.setAdapter(toppingAdapter);

    }
    void updateBuyStatus(){
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        totalprice = Integer.valueOf(tvAmountProduct.getText().toString()) * priceInt;
        String formattedTotalPrice = vndFormat.format(totalprice);
        btn_add_to_cart.setText("Thêm vào giỏ hàng "+formattedTotalPrice);
    }

}