package com.naca.mealacle.p13;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.RiderElementBinding;
import com.naca.mealacle.databinding.RiderInfoBinding;
import com.naca.mealacle.databinding.RiderInfoElementBinding;
import com.naca.mealacle.p12.RiderAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class RiderPageAdapter extends RecyclerView.Adapter<RiderPageAdapter.BindingViewHolder> {

    private LinkedList<Store> storeList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public RiderPageAdapter(LinkedList<Store> cartList, Context context) {
        this.storeList = cartList;
        this.context = context;
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
            ReviewLoadTask task = new ReviewLoadTask(store.getImage());
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        private class ReviewLoadTask extends AsyncTask<Void, Void, Bitmap> {
            private String urlStr;

            public ReviewLoadTask(String url){
                this.urlStr = url;
            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap bitmap = null;
                try{
                    URL url = new URL(urlStr);
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }catch (Exception e){}

                return bitmap;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                try{
                    if(bitmap != null) {
                        Glide.with(context).load(bitmap).into(imageView);
                    }
                    else {
                        Glide.with(context).load(itemView.getResources().getDrawable(R.drawable.ic_launcher_background)).into(imageView);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
