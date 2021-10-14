package com.naca.mealacle.p3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.CategoryElementBinding;
import com.naca.mealacle.databinding.FoodElementBinding;

import java.util.LinkedList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BindingViewHolder> {

    private String[] foodList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public CategoryAdapter(String[] foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryElementBinding binding = CategoryElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(foodList[position]);
    }

    @Override
    public int getItemCount() {
        return foodList.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        CategoryElementBinding binding;

        public BindingViewHolder(@NonNull CategoryElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(CategoryAdapter.mListener != null) {
                            CategoryAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(String element) {
            binding.setVariable(BR.category, element);
        }
    }
}
