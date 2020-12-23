package com.magsood.medappuser.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.magsood.medappuser.Activity.MainActivity;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {


    ArrayList<ModelCart> newsPaperArrayList;
    private LayoutInflater mInflater;
    private AdapterMyOrder.ItemClickListener mClickListener;
    private Activity activity;
    Dialog dialog;
    AdapterCart adapterCart;
    public DialogAdapter(Activity activity, ArrayList<ModelCart> newsPaperArrayList, Dialog dialog, AdapterCart adapterCart) {
        this.mInflater = LayoutInflater.from(activity);
        this.newsPaperArrayList = newsPaperArrayList;
        this.activity = activity;
        this.dialog=dialog;
        this.adapterCart = adapterCart;
    }


    @Override
    public DialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.items_dialog, parent, false);
        return new DialogAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final DialogAdapter.ViewHolder holder, int position) {
        final ModelCart item = newsPaperArrayList.get(position);

//


        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.qun.setText(String.valueOf(item.getAmount()));


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delFromCart(item.getId());

                Toast.makeText(activity, "تم الحذف", Toast.LENGTH_SHORT).show();
                newsPaperArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, newsPaperArrayList.size());
                adapterCart.notifyItemRemoved(position);
                adapterCart.notifyItemRangeChanged(position, newsPaperArrayList.size()

                );
                adapterCart.notifyDataSetChanged();
                if (newsPaperArrayList.size()==0){
                   Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);}

            }
        });


    }


    private boolean delFromCart(String id){
        return new SqlLiteDataBase(activity).deleteRowFromCart(id);
    }

    @Override










    public int getItemCount() {
        return newsPaperArrayList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(AdapterMyOrder.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatButton buttonSubscription;
        TextView txtDetails,textView_type,textView_release_type,textView_release_time;
        TextView name ,price , qun;
        ImageView imageView;


        ViewHolder(View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.delete);
//            container = itemView.findViewById(R.id.container);
//            buttonSubscription = itemView.findViewById(R.id.btn_sub);
//            layDeliev = itemView.findViewById(R.id.layDeliev);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            qun = itemView.findViewById(R.id.qun);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}

