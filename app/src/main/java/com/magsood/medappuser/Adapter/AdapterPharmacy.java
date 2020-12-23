package com.magsood.medappuser.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.magsood.medappuser.Model.ModelProcessOrder;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class AdapterPharmacy extends RecyclerView.Adapter<AdapterPharmacy.ViewHolder> {


    ArrayList<ModelProcessOrder> processeArrayList;
    private LayoutInflater mInflater;
    private AdapterMyOrder.ItemClickListener mClickListener;
    private Activity activity;
    Dialog dialog;
    ArrayList<String> pharmacy_name;
    ArrayList<String> pharmacy_stage;
    public AdapterPharmacy(Activity activity, ArrayList<ModelProcessOrder> newsPaperArrayList) {
        this.mInflater = LayoutInflater.from(activity);
        this.processeArrayList = newsPaperArrayList;
        this.activity = activity;
        this.dialog=dialog;
    }

    public AdapterPharmacy(Activity activity, ArrayList<String> pharmacy_name, ArrayList<String> pharmacy_stage) {
        this.mInflater = LayoutInflater.from(activity);
        this.activity=activity;
        this.pharmacy_name = pharmacy_name;
        this.pharmacy_stage = pharmacy_stage;
    }


    @Override
    public AdapterPharmacy.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.items_progress_request_pharmacy, parent, false);
        return new AdapterPharmacy.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final AdapterPharmacy.ViewHolder holder, int position) {
//        final ModelProcessOrder item = processeArrayList.get(position);

//

        if (pharmacy_stage.get(position).equals("1")){
            holder.atatus.setText("تم");
            holder.imageStatus.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);


        }







        holder.pharmacy.setText(pharmacy_name.get(position));
//        holder.imageStatus.setText(String.valueOf(item.getOrder_number()));
//
//        holder.phone_number.setText(item.getPhone_number());




    }


    private boolean delFromCart(String id){
        return new SqlLiteDataBase(activity).deleteRowFromCart(id);
    }

    @Override
    public int getItemCount() {
        return pharmacy_name.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder  {


        TextView atatus,pharmacy ;
        ImageView imageStatus;
        ProgressBar progressBar;



        ViewHolder(View itemView) {
            super(itemView);



//            buttonSubscription = itemView.findViewById(R.id.btn_sub);
//            layDeliev = itemView.findViewById(R.id.layDeliev);

            atatus = itemView.findViewById(R.id.status);
            pharmacy = itemView.findViewById(R.id.pharmacy_name);
            imageStatus = itemView.findViewById(R.id.image_status);
            progressBar = itemView.findViewById(R.id.progresse);


        }


    }


}


