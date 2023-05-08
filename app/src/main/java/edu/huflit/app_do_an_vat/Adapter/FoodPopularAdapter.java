package edu.huflit.app_do_an_vat.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.huflit.app_do_an_vat.Database.DBHelper;
import edu.huflit.app_do_an_vat.DetailFood;
import edu.huflit.app_do_an_vat.Model.Food;
import edu.huflit.app_do_an_vat.R;

public class FoodPopularAdapter extends RecyclerView.Adapter<FoodPopularAdapter.FoodPopularViewHolder> {

    ArrayList<Food> mListFoodPopular;
    Context mContext;
    String url_product,type_product,name_product,describe_product;
    int price_product;
    DBHelper db;

    public FoodPopularAdapter(ArrayList<Food> mListFoodPopular, Context mContext) {
        this.mListFoodPopular = mListFoodPopular;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FoodPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal_hot_product,parent,false);
        return new FoodPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPopularViewHolder holder, int position) {
        Food food = mListFoodPopular.get(position);
        if (food == null) {
            return;
        }

            holder.imgvProduct.getLayoutParams().width = 250;
            holder.imgvProduct.getLayoutParams().height = 300;
            Picasso.get().load(mListFoodPopular.get(position).getFood_url()).resize(holder.imgvProduct.getLayoutParams().width, holder.imgvProduct.getLayoutParams().height).centerCrop().into(holder.imgvProduct);

            holder.tvNameProduct.setText(mListFoodPopular.get(position).getFood_name());
            holder.tvPriceProduct.setText(String.valueOf(mListFoodPopular.get(position).getFood_price()));
        url_product=mListFoodPopular.get(position).getFood_url();
        type_product= mListFoodPopular.get(position).getFood_type();
        Integer product_id  = mListFoodPopular.get(position).getFood_id();
        name_product = mListFoodPopular.get(position).getFood_name();
        describe_product = mListFoodPopular.get(position).getFoodDescribe();
        price_product = mListFoodPopular.get(position).getFood_price();
        holder.rltFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = mContext.getSharedPreferences("my_prefs",mContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("product_name", name_product);
                editor.putString("product_price",String.valueOf(price_product));
                editor.putString("product_describe",describe_product);
                editor.putString("product_url",url_product);
                editor.apply();
                Intent i = new Intent(mContext, DetailFood.class);
                mContext.startActivity(i);
            }
        });
        }

    @Override
    public int getItemCount() {
        return mListFoodPopular.size();
    }

    public class FoodPopularViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameProduct,tvPriceProduct;
        private ImageView imgvProduct;
        private RelativeLayout rltFoodItem;
        public FoodPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameProduct = itemView.findViewById(R.id.tv_Name_Product_popular);
            tvPriceProduct = itemView.findViewById(R.id.tv_price_product_popular);
            imgvProduct = itemView.findViewById(R.id.img_Product_popular);
            rltFoodItem = itemView.findViewById(R.id.rltFoodItem_popuular);
        }
    }
}
