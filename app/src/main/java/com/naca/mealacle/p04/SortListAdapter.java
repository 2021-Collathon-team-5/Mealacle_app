package com.naca.mealacle.p04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.databinding.SortElementBinding;

public class SortListAdapter extends RecyclerView.Adapter<SortListAdapter.BindingViewHolder> {

    private String[] sort;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public SortListAdapter(String[] sort) {
        this.sort = sort;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SortElementBinding binding = SortElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(sort[position]);
    }

    @Override
    public int getItemCount() {
        return sort.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        SortElementBinding binding;

        public BindingViewHolder(@NonNull SortElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(SortListAdapter.mListener != null) {
                            SortListAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(String sort) {
            binding.setVariable(BR.sort, sort);
        }
    }
}
