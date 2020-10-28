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


public class AdapterSearchResult extends RecyclerView.Adapter<AdapterSearchResult.ViewHolder> {

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

//

        holder.textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelCart modelCart = new ModelCart();
                modelCart.setId(position+"");
                modelCart.setName("انسولين"+position);
                modelCart.setPharmacyAddress("بحري المحطة الوسطي"+position);
                modelCart.setPharmacyName("صيدلية يقين"+position);
                modelCart.setPrice("200 SDG"+position);
                modelCart.setPharmacyLat("");
                modelCart.setPharmacyLong("");
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
        LinearLayout textViewAddToCart,layDeliev;
        AppCompatButton buttonSubscription;
        TextView txtShowMap,textView_release_type,textView_release_time;
        ImageView imageView;


        ViewHolder(View itemView) {
            super(itemView);


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

