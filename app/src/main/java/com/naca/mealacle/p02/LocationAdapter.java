package com.naca.mealacle.p02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.naca.mealacle.BR;
import com.naca.mealacle.databinding.LocationElementBinding;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.BindingViewHolder> {

    private String[] locations;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public LocationAdapter(String[] locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LocationElementBinding binding = LocationElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(locations[position]);
    }

    @Override
    public int getItemCount() {
        return locations.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        LocationElementBinding binding;

        public BindingViewHolder(@NonNull LocationElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(LocationAdapter.mListener != null) {
                            LocationAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(String location) {
            binding.setVariable(BR.location, location);
        }
    }
}
