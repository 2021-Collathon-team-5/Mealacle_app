package com.naca.mealacle.p03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Category;
import com.naca.mealacle.databinding.CategoryElementBinding;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BindingViewHolder> {

    private Category[] categories;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public CategoryAdapter(Category[] categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryElementBinding binding = CategoryElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(categories[position]);
    }

    @Override
    public int getItemCount() {
        return categories.length;
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

        public void bind(Category category) {
            binding.setVariable(BR.category, category);
        }
    }
}
