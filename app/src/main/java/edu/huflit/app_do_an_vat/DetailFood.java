package edu.huflit.app_do_an_vat;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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
import edu.huflit.app_do_an_vat.Model.Topping;

public class DetailFood extends AppCompatActivity {
    SharedPreferences sharedPref;
    TextView tvNameProduct,tvPrice,tvDescribe,tvAmountProduct;
    ArrayList<Topping> toppingDataHolder = new ArrayList<>();

    ImageView imgvProduct;
    RecyclerView rcv_topping;
    int realTimeAmount,priceInt;
    Button btnExit,btn_add_to_cart,btnPlus,btnSubtract;


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



        Topping obj = new Topping("https://cdn.discordapp.com/attachments/941739362222211072/1105049624303108176/1123.png","Topping test",12000);
        Topping obj1 = new Topping("https://cdn.discordapp.com/attachments/941739362222211072/1105049624303108176/1123.png","Topping test",12000);
        Topping obj2 = new Topping("https://cdn.discordapp.com/attachments/941739362222211072/1105049624303108176/1123.png","Topping test",12000);
        toppingDataHolder.add(obj);
        toppingDataHolder.add(obj1);
        toppingDataHolder.add(obj2);
        rcv_topping.setLayoutManager(ln1);
        ToppingAdapter toppingAdapter = new ToppingAdapter(toppingDataHolder,this);
        rcv_topping.setAdapter(toppingAdapter);

    }
    void updateBuyStatus(){
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        sharedPref = getSharedPreferences("my_prefs", FoodList.MODE_PRIVATE);
        int price = sharedPref.getInt("topping_price",0);
        int amount= sharedPref.getInt("topping_amount",0);
        Log.e(TAG, "updateBuyStatus: "+price+ "/" +amount );
        int totalprice = Integer.valueOf(tvAmountProduct.getText().toString()) * priceInt + price*amount;
        String formattedTotalPrice = vndFormat.format(Integer.valueOf(totalprice));

        btn_add_to_cart.setText("Thêm vào giỏ hàng "+formattedTotalPrice);
    }

}