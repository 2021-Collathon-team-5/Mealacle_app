package com.naca.mealacle.p13;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.RiderElementBinding;
import com.naca.mealacle.databinding.RiderInfoBinding;
import com.naca.mealacle.databinding.RiderInfoElementBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

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
        Bitmap bitmap;
        ImageView imageView;

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
            imageView = binding.image;
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(store.getImage());

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
