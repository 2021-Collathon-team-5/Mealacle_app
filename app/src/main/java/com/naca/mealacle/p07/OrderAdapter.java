package com.naca.mealacle.p07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.CartElementBinding;
import com.naca.mealacle.databinding.ReceiptElementBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.BindingViewHolder> {

    private List<Food> cartList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public OrderAdapter(List<Food> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReceiptElementBinding binding = ReceiptElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(cartList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        ReceiptElementBinding binding;

        public BindingViewHolder(@NonNull ReceiptElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(OrderAdapter.mListener != null) {
                            OrderAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Food food) {
            binding.setVariable(BR.food_receipt, food);
        }
    }
}
