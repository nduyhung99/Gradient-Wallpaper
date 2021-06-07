package com.example.gradientwallpaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradientwallpaper.fragment.CollectionFragment;

import java.util.List;

public class GradientAdapter extends RecyclerView.Adapter<GradientAdapter.GradientViewHolder> {
    private Context mContext;
    private List<Gradient> mListGradient;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public GradientAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Gradient> list){
        this.mListGradient=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GradientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper,parent,false);
//        return new GradientViewHolder(view);
        View view;
        LayoutInflater mInflater= LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.item_wallpaper,parent,false);
        return new GradientViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GradientAdapter.GradientViewHolder holder, int position) {
        Gradient gradient=mListGradient.get(position);
        if (gradient==null){
            return;
        }
        holder.imgGradient.setImageResource(gradient.getResGradient());
        holder.txtName.setText(gradient.getName());
    }

    @Override
    public int getItemCount() {
        if (mListGradient!=null){
            return mListGradient.size();
        }
        return 0;
    }

    public class GradientViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private ImageView imgGradient;
        public GradientViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            imgGradient=itemView.findViewById(R.id.imgGradient);

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
