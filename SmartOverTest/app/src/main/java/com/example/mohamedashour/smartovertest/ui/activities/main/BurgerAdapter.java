package com.example.mohamedashour.smartovertest.ui.activities.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamedashour.smartovertest.MyApplication;
import com.example.mohamedashour.smartovertest.R;
import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;
import com.example.mohamedashour.smartovertest.utils.AppTools;
import com.example.mohamedashour.smartovertest.utils.AppUtils;
import com.example.mohamedashour.smartovertest.utils.interfaces.RecyclerViewClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.ViewHolder>{

    Context context;
    List<BurgerDataModel> list;
    RecyclerViewClickListener recyclerViewClickListener;
    Gson gson;

    public BurgerAdapter(Context context, List<BurgerDataModel> list, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickListener = recyclerViewClickListener;
        gson = new Gson();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item_photo)
        ImageView itemPhoto;
        @BindView(R.id.tv_item_name)
        TextView nameTextView;
        @BindView(R.id.tv_item_desc)
        TextView descTextView;
        @BindView(R.id.tv_item_price)
        TextView priceTextView;
        @BindView(R.id.img_cart)
        ImageView addToCartImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_burger_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // this to check if item already in  basket or not
        String data = AppUtils.getFromSharedPreference(MyApplication.getContext(), AppTools.BASKET_KEY);
        if (!data.equals("")) {
            Type type = new TypeToken<List<BurgerDataModel>>(){}.getType();
            List<BurgerDataModel> localList = gson.fromJson(data, type);
            for (int i = 0; i < localList.size(); i++) {
                if (list.get(position).getRef().equals(localList.get(i).getRef())) {
                    viewHolder.addToCartImageView.setImageResource(R.drawable.ic_shopping_cart);
                    break;
                }
            }
        }
        // set list data to widgets
        viewHolder.nameTextView.setText(list.get(position).getTitle());
        viewHolder.descTextView.setText(list.get(position).getDescription());
        double newPrice =  list.get(position).getPrice() / (double) 100;
        viewHolder.priceTextView.setText(String.valueOf(newPrice) + "₺");
        Glide.with(context).load(list.get(position).getThumbnail()).placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.itemPhoto);
        // I create interface (like live data) to listen to cart image click in the view
        viewHolder.addToCartImageView.setOnClickListener(view -> {
            viewHolder.addToCartImageView.setImageResource(R.drawable.ic_shopping_cart);
            recyclerViewClickListener.OnItemClick(view, position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
