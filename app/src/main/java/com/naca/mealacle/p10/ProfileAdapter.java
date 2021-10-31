package com.naca.mealacle.p10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.databinding.ProfileElementBinding;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.BindingViewHolder> {

    private String[] inform;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public ProfileAdapter(String[] inform) {
        this.inform = inform;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProfileElementBinding binding = ProfileElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(inform[position]);
    }

    @Override
    public int getItemCount() {
        return inform.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        ProfileElementBinding binding;

        public BindingViewHolder(@NonNull ProfileElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(ProfileAdapter.mListener != null) {
                            ProfileAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(String sort) {
            binding.setVariable(BR.inform, sort);
        }
    }
}
