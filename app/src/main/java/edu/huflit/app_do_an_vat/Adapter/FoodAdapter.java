package edu.huflit.app_do_an_vat.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.huflit.app_do_an_vat.Database.DBHelper;
import edu.huflit.app_do_an_vat.DetailFood;
import edu.huflit.app_do_an_vat.FoodList;
import edu.huflit.app_do_an_vat.Home;
import edu.huflit.app_do_an_vat.Model.Food;
import edu.huflit.app_do_an_vat.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    ArrayList<Food> mListFood;
    Context mContext;
    Dialog dialog;
    String url_product,type_product,name_product,describe_product;
    int price_product;
    String Loai[] = {"Trà sữa" , "Bánh ngọt" , "Trà trái cây" , "Cà phê"};
    String procate;
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
        String product_rate = mListFood.get(i).getFood_rate();
        Integer product_id  = mListFood.get(i).getFood_id();
        Log.e(TAG, "onBindViewHolder: " +product_id );
        name_product = mListFood.get(i).getFood_name();
        describe_product = mListFood.get(i).getFoodDescribe();
        price_product = mListFood.get(i).getFood_price();

        holder.rltFoodItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                handlePopular(product_id,url_product,price_product,product_rate,describe_product,name_product);
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
    private void handlePopular(int positonid,String url,int price,String rate,String describe_product,String name) {
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
                .setNegativeButton("Chỉnh sửa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogUpdate(positonid,url,name,price,rate,describe_product);
                    }
                }).show();
    }
    private void dialogUpdate(int id,String url,String name, int price, String product_rate, String descripe) {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_update_food);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        db = new DBHelper(mContext);
        EditText edtNameEditProduct = dialog.findViewById(R.id.edt_name_update_product);
        EditText edtPriceEditProduct = dialog.findViewById(R.id.edt_price_update_product);
        Spinner spnCategoryEditProduct = dialog.findViewById(R.id.spn_update_product_category);
        EditText edtDescipeEditProduct = dialog.findViewById(R.id.edt_descripe_update_product);
        Button btnUpdate = dialog.findViewById(R.id.btn_update_product);
        Button btnCancel = dialog.findViewById(R.id.btn_cancelUpdateProduct);
        edtNameEditProduct.setText(name);
        edtPriceEditProduct.setText(String.valueOf(price));
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(mContext , android.R.layout.simple_spinner_dropdown_item,Loai);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnCategoryEditProduct.setAdapter(arrayAdapter);
        edtDescipeEditProduct.setText(descripe);



        spnCategoryEditProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                procate = Loai[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameEdited = edtNameEditProduct.getText().toString();
                String descripeEdited = edtDescipeEditProduct.getText().toString();
                int priceEdited =Integer.parseInt( edtPriceEditProduct.getText().toString());
                DBHelper db=  new DBHelper(mContext);
                Cursor cursor  = db.getFoodData();
                while (cursor.moveToNext()) {
                    Log.e(TAG, "onClick: " + cursor.getInt(0) + "/" + id );
                    if (cursor.getInt(0) == id) {
                        Log.e(TAG, "onClick: " + "helolo" );
                        db.updateFoodData(id,url,nameEdited,priceEdited,product_rate ,procate ,descripeEdited);
                        Intent i = new Intent(mContext, FoodList.class);
                        mContext.startActivity(i);
                    }
                }
                cursor.close();
            }



        });
        dialog.show();



    }


}
