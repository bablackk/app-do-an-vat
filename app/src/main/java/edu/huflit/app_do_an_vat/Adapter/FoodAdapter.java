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
import edu.huflit.app_do_an_vat.Home;
import edu.huflit.app_do_an_vat.Model.Food;
import edu.huflit.app_do_an_vat.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    ArrayList<Food> mListFood;
    Context mContext;
    String url_product,type_product,name_product,describe_product;
    int price_product;
    DBHelper db;

    public FoodAdapter(ArrayList<Food> mListFood, Context mContext, DBHelper db) {
        this.mListFood = mListFood;
        this.mContext = mContext;
        this.db = db;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_layout,viewGroup,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int i) {
        Food food = mListFood.get(i);
        if (food == null) {
            return;
        }
        holder.imgvProduct.getLayoutParams().width = 250;
        holder.imgvProduct.getLayoutParams().height = 300;
        Picasso.get().load(mListFood.get(i).getFood_url()).resize(holder.imgvProduct.getLayoutParams().width, holder.imgvProduct.getLayoutParams().height).centerCrop().into(holder.imgvProduct);

        holder.tvNameProduct.setText(mListFood.get(i).getFood_name());
        holder.tvPriceProduct.setText(String.valueOf(mListFood.get(i).getFood_price()));
        url_product=mListFood.get(i).getFood_url();
        type_product= mListFood.get(i).getFood_type();
        Integer product_id  = mListFood.get(i).getFood_id();
        name_product = mListFood.get(i).getFood_name();
        describe_product = mListFood.get(i).getFoodDescribe();
        price_product = mListFood.get(i).getFood_price();

        holder.rltFoodItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                handlePopular(product_id);
                return true;
            }
        });
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
        return mListFood.size();
    }

   public class FoodViewHolder extends RecyclerView.ViewHolder{
       private TextView tvNameProduct,tvPriceProduct;
       private ImageView imgvProduct;
       private RelativeLayout rltFoodItem;
       public FoodViewHolder(@NonNull View itemView) {
           super(itemView);
           tvNameProduct = itemView.findViewById(R.id.tv_Name_Product);
           tvPriceProduct = itemView.findViewById(R.id.tv_price_product);
           imgvProduct = itemView.findViewById(R.id.img_Product1);
           rltFoodItem = itemView.findViewById(R.id.rltFoodItem);
       }
   }
    private void handlePopular(int positonid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Hiển thị trên phổ biến")
                .setMessage("Bạn có muốn hiển thị trên trang chính?")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db=new DBHelper(mContext);
                        Cursor cursor = db.getFoodData();
                        while(cursor.moveToNext()) {
                            if(cursor.getInt(0) == (positonid)) {
                                boolean checkUpdate = db.updateFoodRate(positonid,"Popular");
                                if(checkUpdate) {
                                    Toast.makeText(mContext,"Thành công",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(mContext,"failed",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        cursor.close();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }



}
