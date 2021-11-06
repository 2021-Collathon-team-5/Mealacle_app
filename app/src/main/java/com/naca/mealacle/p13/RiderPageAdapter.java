package com.naca.mealacle.p13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.RiderElementBinding;
import com.naca.mealacle.databinding.RiderInfoBinding;
import com.naca.mealacle.databinding.RiderInfoElementBinding;

import java.util.LinkedList;

public class RiderPageAdapter extends RecyclerView.Adapter<RiderPageAdapter.BindingViewHolder> {

    private LinkedList<Store> storeList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public RiderPageAdapter(LinkedList<Store> cartList) {
        this.storeList = cartList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RiderInfoElementBinding binding = RiderInfoElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        RiderInfoElementBinding binding;

        public BindingViewHolder(@NonNull RiderInfoElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(RiderPageAdapter.mListener != null) {
                            RiderPageAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Store store) {
            binding.setVariable(BR.store_info, store);
        }
    }
}
