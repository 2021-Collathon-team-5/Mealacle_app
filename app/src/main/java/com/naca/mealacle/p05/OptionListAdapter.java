package com.naca.mealacle.p05;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.databinding.OptionElementBinding;
import com.naca.mealacle.databinding.SortElementBinding;

import java.util.HashMap;
import java.util.List;

public class OptionListAdapter extends RecyclerView.Adapter<OptionListAdapter.BindingViewHolder> {

    private List<HashMap<String, Object>> list;
    private int selected_position = 0;

    public int getSelected_position(){
        return selected_position;
    }

    public void setSelected_position(int selected_position){
        this.selected_position = selected_position;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public OptionListAdapter(List<HashMap<String, Object>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OptionElementBinding binding = OptionElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        if(position == selected_position){
            holder.binding.getRoot().setSelected(true);
        } else {
            holder.binding.getRoot().setSelected(false);
        }
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        OptionElementBinding binding;

        public BindingViewHolder(@NonNull OptionElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(OptionListAdapter.mListener != null) {
                            OptionListAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(HashMap<String, Object> option) {
            binding.setVariable(BR.option, String.valueOf(option.get("option")));
        }
    }
}
