package com.naca.mealacle.p04;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.MenuElementBinding;
import com.naca.mealacle.p12.RiderAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.BindingViewHolder> {

    private LinkedList<Food> foodList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public FoodListAdapter(LinkedList<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MenuElementBinding binding = MenuElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(foodList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {

        MenuElementBinding binding;
        ImageView imageView;

        public BindingViewHolder(@NonNull MenuElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(FoodListAdapter.mListener != null) {
                            FoodListAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });

        }

        public void bind(Food food) {
            binding.setVariable(BR.food_menu, food);

            imageView = binding.foodimage;
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
