package com.naca.mealacle.p09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Alert;
import com.naca.mealacle.databinding.NotifyElementBinding;

import java.util.LinkedList;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.BindingViewHolder> {

    private LinkedList<Alert> notifyList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public NotifyAdapter(LinkedList<Alert> notifyList) {
        this.notifyList = notifyList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotifyElementBinding binding = NotifyElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(notifyList.get(position));
    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        NotifyElementBinding binding;

        public BindingViewHolder(@NonNull NotifyElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(NotifyAdapter.mListener != null) {
                            NotifyAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Alert alert) {
            binding.setVariable(BR.alert_title, alert.getTitle());
            binding.setVariable(BR.alert_content, alert.getContent());
        }
    }
}
