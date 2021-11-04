package com.naca.mealacle.p12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.CartElementBinding;
import com.naca.mealacle.databinding.RiderElementBinding;

import java.util.LinkedList;

public class RiderAdapter extends RecyclerView.Adapter<RiderAdapter.BindingViewHolder> {

    private LinkedList<Store> storeList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public RiderAdapter(LinkedList<Store> cartList) {
        this.storeList = cartList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RiderElementBinding binding = RiderElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(storeList.get(position));
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        RiderElementBinding binding;

        public BindingViewHolder(@NonNull RiderElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(RiderAdapter.mListener != null) {
                            RiderAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Store store) {
            binding.setVariable(BR.name_rider, store.getName());
            binding.setVariable(BR.product_rider, store.getProduct());
            binding.setVariable(BR.cost_rider, store.getCost());
            binding.setVariable(BR.time_rider, store.getTime());
            binding.setVariable(BR.address_rider, store.getAddress());
        }
    }
}
