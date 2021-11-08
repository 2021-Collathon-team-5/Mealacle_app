package com.naca.mealacle.p15;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Delivery;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.DeliveryElementBinding;
import com.naca.mealacle.databinding.RiderInfoElementBinding;

import java.util.LinkedList;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.BindingViewHolder> {

    private LinkedList<Delivery> deliveryList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public DeliveryAdapter(LinkedList<Delivery> cartList) {
        this.deliveryList = cartList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryElementBinding binding = DeliveryElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(deliveryList.get(position));
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        DeliveryElementBinding binding;

        public BindingViewHolder(@NonNull DeliveryElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(DeliveryAdapter.mListener != null) {
                            DeliveryAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
            binding.deliverComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(DeliveryAdapter.mListener != null) {
                            DeliveryAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Delivery delivery) {
            binding.setVariable(BR.delivery, delivery);
        }
    }
}
