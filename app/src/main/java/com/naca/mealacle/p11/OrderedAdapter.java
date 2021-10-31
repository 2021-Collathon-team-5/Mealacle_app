package com.naca.mealacle.p11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.OrderedElementBinding;

import java.util.LinkedList;

public class OrderedAdapter extends RecyclerView.Adapter<OrderedAdapter.BindingViewHolder> {

    private LinkedList<Food> orderedList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public OrderedAdapter(LinkedList<Food> orderedList) {
        this.orderedList = orderedList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderedElementBinding binding = OrderedElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(orderedList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderedList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        OrderedElementBinding binding;

        public BindingViewHolder(@NonNull OrderedElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(OrderedAdapter.mListener != null) {
                            OrderedAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Food food) {
            binding.setVariable(BR.alert_title, food.getName());
            binding.setVariable(BR.alert_content, Integer.toString(food.getCost()) + "원");
        }
    }
}
