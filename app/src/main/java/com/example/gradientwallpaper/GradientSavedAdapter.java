package com.example.gradientwallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradientSavedAdapter extends RecyclerView.Adapter<GradientSavedAdapter.GradientSavedViewHolder>{
    private Context mContext;
    private List<GradientSaved> mList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public GradientSavedAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<GradientSaved> list){
        this.mList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GradientSavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper_saved,parent,false);
        return new GradientSavedViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GradientSavedAdapter.GradientSavedViewHolder holder, int position) {
        GradientSaved gradientSaved=mList.get(position);
        if (gradientSaved==null){
            return;
        }
        Bitmap myBitmap = BitmapFactory.decodeFile(mList.get(position).getImagePath());
        holder.imageSaved.setImageBitmap(myBitmap);
    }

    @Override
    public int getItemCount() {
        if (mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class GradientSavedViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageSaved;

        public GradientSavedViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            imageSaved=itemView.findViewById(R.id.imageSaved);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
