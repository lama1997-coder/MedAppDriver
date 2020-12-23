package com.magsood.medappuser.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.magsood.medappuser.Model.ModelProcessOrder;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class AdapterProgressRequests  extends RecyclerView.Adapter<AdapterProgressRequests.ViewHolder> {


    ArrayList<ModelProcessOrder> processeArrayList;
    AdapterPharmacy adapterPharmacy;
    private LayoutInflater mInflater;
    private AdapterMyOrder.ItemClickListener mClickListener;
    private Activity activity;
    Dialog dialog;
    public AdapterProgressRequests(Activity activity, ArrayList<ModelProcessOrder> newsPaperArrayList) {
        this.mInflater = LayoutInflater.from(activity);
        this.processeArrayList = newsPaperArrayList;
        this.activity = activity;
        this.dialog=dialog;
    }


    @Override
    public AdapterProgressRequests.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.items_progress_request, parent, false);
        return new AdapterProgressRequests.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final AdapterProgressRequests.ViewHolder holder, int position) {
        final ModelProcessOrder item = processeArrayList.get(position);

//


        holder.nameCaptin.setText(item.getName_of_captin());
        holder.price.setText(item.getPrice());
        holder.order_number.setText(String.valueOf(item.getOrder_number()));

        holder.phone_number.setText(item.getPhoneNumber());





        if (processeArrayList.size()>0){
            adapterPharmacy = new AdapterPharmacy(activity,item.getPharmacy_name(),item.getPharmacy_stage());
            holder.rec.setAdapter(adapterPharmacy);
        }


    }


    private boolean delFromCart(String id){
        return new SqlLiteDataBase(activity).deleteRowFromCart(id);
    }

    @Override
    public int getItemCount() {
        return processeArrayList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(AdapterMyOrder.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {


        TextView nameCaptin,price , phone_number,order_number;
        RecyclerView rec;



        ViewHolder(View itemView) {
            super(itemView);



//            buttonSubscription = itemView.findViewById(R.id.btn_sub);
//            layDeliev = itemView.findViewById(R.id.layDeliev);

            nameCaptin = itemView.findViewById(R.id.captin_name);
            price = itemView.findViewById(R.id.price);
            phone_number = itemView.findViewById(R.id.captin_number);
            rec = itemView.findViewById(R.id.pharmacy_items_rec);
            order_number = itemView.findViewById(R.id.order_num);


        }


    }


}


