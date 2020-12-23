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
import com.magsood.medappuser.Activity.TestMapActvity;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.Model.ModelSearch;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;

import java.util.ArrayList;


public class  AdapterSearchResult extends RecyclerView.Adapter<AdapterSearchResult.ViewHolder> {

    ArrayList<ModelSearch> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    public AdapterSearchResult(Activity activity, ArrayList<ModelSearch> arrayList) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_results_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ModelSearch item = arrayList.get(position);

        holder.location.setText(item.getLocation());
        holder.medicineName.setText(item.getPublicName());
        holder.price.setText(item.getPrice());
        holder.description.setText(item.getDescription());
        holder.companyName.setText(item.getCompnayName());
        holder.pharmcyName.setText(item.getPharmacyName());



//

        holder.textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelCart modelCart = new ModelCart();
                modelCart.setId(item.getId());
                modelCart.setName(item.getTradeName());
                modelCart.setPharmacyAddress(item.getLocation());
                modelCart.setPharmacyName(item.getPharmacyName());
                modelCart.setPharmacyID(item.getPharmacyID());
                modelCart.setMedicineID(item.getMedicineID());
                modelCart.setPrice(item.getPrice());
                modelCart.setPharmacyLat(item.getLat());
                modelCart.setPharmacyLong(item.getLng());
             //   Toast.makeText(activity,"قـــــريــــبـــا", Toast.LENGTH_SHORT).show();


                //add to cart code
                if (AddToCart(modelCart)){
                   Toast.makeText(activity, "تم اضافة الدواء الى سلة الادوية", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(activity, "الدواء موجود مسبقا في سلة الادوية", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.txtShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TestMapActvity.class);

                intent.putExtra("medicineID",item.getMedicineID());
                intent.putExtra("id",item.getId());
                intent.putExtra("tradeName",item.getTradeName());


                intent.putExtra("location",item.getLocation());

                intent.putExtra("pharmacyName",item.getCompnayName());

                intent.putExtra("pharmacyID",item.getPharmacyID());

                intent.putExtra("price",item.getPrice());


                intent.putExtra("dropLng",item.getLng());
                intent.putExtra("dropLat",item.getLat());

                activity.startActivity(intent);
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
        LinearLayout container,layDeliev;
        AppCompatButton buttonSubscription;
        TextView textViewAddToCart,txtShowMap,price, medicineName, description,location ,companyName,pharmcyName;
        ImageView imageView;


        ViewHolder(View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            medicineName = itemView.findViewById(R.id.txtPharmachy);
            description = itemView.findViewById(R.id.desc);
            location = itemView.findViewById(R.id.location);
            companyName= itemView.findViewById(R.id.companyName);
            pharmcyName = itemView.findViewById(R.id.pharmcyName);


//            imageView = itemView.findViewById(R.id.img);
//            container = itemView.findViewById(R.id.container);
//            buttonSubscription = itemView.findViewById(R.id.btn_sub);
            txtShowMap = itemView.findViewById(R.id.txtShowMap);
//            layDeliev = itemView.findViewById(R.id.layDeliev);

//            textView_type = itemView.findViewById(R.id.news_item_type);
//            textView_release_type = itemView.findViewById(R.id.news_item_release_type);
            textViewAddToCart = itemView.findViewById(R.id.txtAddToCart);

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

    private boolean AddToCart(ModelCart modelCart){
        return new SqlLiteDataBase(activity).AddToCart(modelCart);
    }

}

