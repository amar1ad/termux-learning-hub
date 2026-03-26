package com.termux.learning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.termux.learning.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * CategoryAdapter - RecyclerView adapter for categories
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<HashMap<String, String>> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(HashMap<String, String> category);
    }

    public CategoryAdapter(Context context, OnCategoryClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.categories = new ArrayList<>();
    }

    public void setCategories(List<HashMap<String, String>> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        HashMap<String, String> category = categories.get(position);
        
        String title = category.get("title_ar");
        String icon = category.get("icon");
        
        holder.titleView.setText(title);
        // Set icon based on category
        setIcon(holder.iconView, icon);
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private void setIcon(ImageView imageView, String iconName) {
        int iconRes = context.getResources().getIdentifier(
                "ic_" + iconName, "drawable", context.getPackageName());
        if (iconRes != 0) {
            imageView.setImageResource(iconRes);
        }
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        ImageView iconView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            iconView = itemView.findViewById(R.id.iconView);
        }
    }
}
