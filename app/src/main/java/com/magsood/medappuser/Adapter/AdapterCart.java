package com.magsood.medappuser.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.magsood.medappuser.Activity.MapsActivity;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.Model.ModelSearch;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;

import java.util.ArrayList;


public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    ArrayList<ModelCart> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    public AdapterCart(Activity activity, ArrayList<ModelCart> arrayList) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_items, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ModelCart item = arrayList.get(position);

        holder.textViewName.setText(item.getName());
        holder.textViewPrice.setText(item.getPrice());
        holder.textViewPhName.setText(item.getPharmacyName());
        holder.textViewAddress.setText(item.getPharmacyAddress());
        holder.textViewDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delFromCart(item.getId())){
                    Toast.makeText(activity, "تم الحذف", Toast.LENGTH_SHORT).show();
                    arrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arrayList.size());
                }else{
                    Toast.makeText(activity, "حدث خطأ حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.txtShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, MapsActivity.class));
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout textViewDel;
        AppCompatButton buttonSubscription;
        TextView textViewName,textViewPrice,textViewPhName,textViewAddress,txtShowMap;
        ImageView imageView;


        ViewHolder(View itemView) {
            super(itemView);


            textViewName = itemView.findViewById(R.id.med_name);
            textViewPrice = itemView.findViewById(R.id.price);
            textViewPhName = itemView.findViewById(R.id.ph_name);
            textViewAddress = itemView.findViewById(R.id.address);
            textViewDel = itemView.findViewById(R.id.del);

            txtShowMap = itemView.findViewById(R.id.txtShowMap);
//            textView_release_type = itemView.findViewById(R.id.news_item_release_type);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    private void showDialog(){
        LayoutInflater factory = LayoutInflater.from(activity);
        final View deleteDialogView = factory.inflate(R.layout.dialog_deliev, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(activity).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
            }
        });


        deleteDialog.show();
    }

    private boolean delFromCart(String id){
        return new SqlLiteDataBase(activity).deleteRowFromCart(id);
    }

}

