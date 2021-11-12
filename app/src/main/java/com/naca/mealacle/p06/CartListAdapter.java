package com.naca.mealacle.p06;

import android.annotation.SuppressLint;
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
import com.naca.mealacle.data.CartProduct;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.CartElementBinding;
import com.naca.mealacle.p04.FoodListAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.BindingViewHolder> {

    private LinkedList<CartProduct> cartList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public CartListAdapter(LinkedList<CartProduct> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartElementBinding binding = CartElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(cartList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        CartElementBinding binding;
        ImageView imageView;
        Bitmap bitmap;

        public BindingViewHolder(@NonNull CartElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        cartList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
            binding.increase.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        cartList.get(position).setCount(cartList.get(position).getCount() + 1);
                        binding.count.setText(Integer.toString(cartList.get(position).getCount()));
                        binding.total.setText(cartList.get(position).getTotal());

                        if(CartListAdapter.mListener != null) {
                            CartListAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
            binding.decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        cartList.get(position).setCount(cartList.get(position).getCount() - 1);
                        binding.count.setText(Integer.toString(cartList.get(position).getCount()));
                        binding.total.setText(cartList.get(position).getTotal());
                        if(CartListAdapter.mListener != null) {
                            CartListAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(CartProduct cartProduct) {
            binding.setVariable(BR.cart, cartProduct);

            imageView = binding.foodimage;
            ReviewLoadTask task = new ReviewLoadTask(cartProduct.getFood().getImages());
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
