package com.naca.mealacle.p02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.databinding.LocationElementBinding;
import com.naca.mealacle.databinding.UnivElementBinding;

import java.util.LinkedList;

public class UnivAdapter extends RecyclerView.Adapter<UnivAdapter.BindingViewHolder> {

    private LinkedList<String> univs;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public UnivAdapter(LinkedList<String> univs) {
        this.univs = univs;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UnivElementBinding binding = UnivElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(univs.get(position));
    }

    @Override
    public int getItemCount() {
        return univs.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        UnivElementBinding binding;

        public BindingViewHolder(@NonNull UnivElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(UnivAdapter.mListener != null) {
                            UnivAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(String univ) {
            binding.setVariable(BR.univ, univ);
        }
    }
}
