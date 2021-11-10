package com.naca.mealacle.p11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.OrderedElementBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class OrderedAdapter extends RecyclerView.Adapter<OrderedAdapter.BindingViewHolder> {

    private LinkedList<Food> orderedList;
    private OrderedElementBinding binding;

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
        Bitmap bitmap;
        ImageView imageView;

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
            binding.setVariable(BR.food_ordered, food);
            imageView = binding.foodImage;
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(food.getImages());

                        HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
                        connect.setDoInput(true);
                        connect.connect();

                        InputStream is = connect.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            mThread.start();

            try {
                mThread.join();
                imageView.setImageBitmap(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
