package edu.huflit.app_do_an_vat.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.huflit.app_do_an_vat.Model.Food;
import edu.huflit.app_do_an_vat.Model.Topping;
import edu.huflit.app_do_an_vat.R;

public class ToppingAdapter  extends RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder> {
    ArrayList<Topping> mListTopping;
    SharedPreferences sharedPref;
    Context mContext;

    public ToppingAdapter(ArrayList<Topping> mListTopping, Context mContext) {
        this.mListTopping = mListTopping;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topping,parent,false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = mListTopping.get(position);
        if (topping == null) {
            return;
        }
        holder.imgvTopping.getLayoutParams().width = 150;
        holder.imgvTopping.getLayoutParams().height = 250;
        holder.tvToppingName.setText(mListTopping.get(position).getTopping_name());
        holder.tvToppingPrice.setText(String.valueOf(mListTopping.get(position).getTopping_price()));
        Picasso.get().load(mListTopping.get(position).getTopping_url()).resize(holder.imgvTopping.getLayoutParams().width, holder.imgvTopping.getLayoutParams().height).centerCrop().into(holder.imgvTopping);
        holder.tvAmountTopping.setText("0");
        int toppingPrice= mListTopping.get(position).getTopping_price();
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amountTopping = Integer.parseInt(holder.tvAmountTopping.getText().toString()) +1;
                holder.tvAmountTopping.setText(String.valueOf(amountTopping));
                sharedPref = mContext.getSharedPreferences("my_prefs",mContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
            }
        });
        holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amountTopping = Integer.parseInt(holder.tvAmountTopping.getText().toString()) -1;
                if(amountTopping <0){
                    amountTopping= 0;
                }
                holder.tvAmountTopping.setText(String.valueOf(amountTopping));
            }
        });



    }

    @Override
    public int getItemCount() {
        return mListTopping.size();
    }

    public class ToppingViewHolder extends RecyclerView.ViewHolder{
        private TextView tvToppingName,tvToppingPrice,tvAmountTopping;
        private ImageView imgvTopping;
        private Button btnAdd,btnSubtract;
        public ToppingViewHolder(@NonNull View itemView){
            super(itemView);
            tvToppingName = itemView.findViewById(R.id.tv_name_topping);
            tvToppingPrice = itemView.findViewById(R.id.tv_price_topping);
            imgvTopping = itemView.findViewById(R.id.img_topping);
            btnAdd = itemView.findViewById(R.id.btn_add_topping);
            btnSubtract = itemView.findViewById(R.id.btn_subtract_topping);
            tvAmountTopping = itemView.findViewById(R.id.tv_quantity_topping);

        }
    }
}
