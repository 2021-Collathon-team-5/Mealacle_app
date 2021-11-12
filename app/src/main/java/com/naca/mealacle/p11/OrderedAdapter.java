package com.naca.mealacle.p11;

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
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.OrderedElementBinding;
import com.naca.mealacle.p12.RiderAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class OrderedAdapter extends RecyclerView.Adapter<OrderedAdapter.BindingViewHolder> {

    private LinkedList<Food> orderedList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public OrderedAdapter(LinkedList<Food> orderedList, Context context) {
        this.orderedList = orderedList;
        this.context = context;
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
            ReviewLoadTask task = new ReviewLoadTask(food.getImages());
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
