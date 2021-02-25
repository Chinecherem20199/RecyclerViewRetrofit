package nigeriandailies.com.ng.recyclerviewretrofit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    // initialization variable
     private ArrayList<MainData> mDataArrayList;
     private Activity mActivity;

     //create construct


    public MainAdapter(ArrayList<MainData> dataArrayList, Activity activity) {
        mDataArrayList = dataArrayList;
        mActivity = activity;
    }

    @NonNull
     @Override
     public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);

         return new ViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //Initialize main data
         MainData data = mDataArrayList.get(position);

         //set image on image view
         Glide.with(mActivity).load(data.getImage())
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .into(holder.mImageView);
         //set name on text view
         holder.mTextView.setText(data.getName());

     }

     @Override
     public int getItemCount() {
         return mDataArrayList.size();
     }

     public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView mImageView;
         TextView mTextView;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             mImageView = itemView.findViewById(R.id.image_view);
             mTextView = itemView.findViewById(R.id.text_view);
         }
     }
 }
