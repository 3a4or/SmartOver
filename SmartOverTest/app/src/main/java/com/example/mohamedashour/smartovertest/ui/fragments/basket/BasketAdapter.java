package com.example.mohamedashour.smartovertest.ui.fragments.basket;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamedashour.smartovertest.R;
import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;
import com.example.mohamedashour.smartovertest.utils.interfaces.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder>{

    Context context;
    List<BurgerDataModel> list;
    RecyclerViewClickListener recyclerViewClickListener;

    public BasketAdapter(Context context, List<BurgerDataModel> list, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item_photo)
        ImageView itemPhoto;
        @BindView(R.id.tv_item_name)
        TextView nameTextView;
        @BindView(R.id.tv_item_price)
        TextView priceTextView;
        @BindView(R.id.img_delete)
        ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_basket_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.nameTextView.setText(list.get(position).getTitle());
        double newPrice =  list.get(position).getPrice() / (double) 100;
        viewHolder.priceTextView.setText(String.valueOf(newPrice) + "₺");
        Glide.with(context).load(list.get(position).getThumbnail()).placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.itemPhoto);
        // I create interface (like live data) to listen to cart image click in the view
        viewHolder.deleteImageView.setOnClickListener(view -> recyclerViewClickListener.OnItemClick(view, position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
