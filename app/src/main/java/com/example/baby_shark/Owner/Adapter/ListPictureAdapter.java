package com.example.baby_shark.Owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baby_shark.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListPictureAdapter extends RecyclerView.Adapter<ListPictureAdapter.ImageHolder> {
    private List<String> imageList;
    private Context context;
    private OnNoteListener mOnNoteListener;
    public ListPictureAdapter(List<String> imageList, Context context,OnNoteListener onNoteListener) {
        this.imageList = imageList;
        this.context = context;
        this.mOnNoteListener = onNoteListener;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử
        View view = inflater.inflate(R.layout.image_row, parent, false);

        ImageHolder imageHolder = new ImageHolder(view,mOnNoteListener);
        return imageHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        String image = imageList.get(position);
        Picasso.with(context).load(image).fit().centerCrop().into(holder.imageView);

    }



    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private ImageView imageView;
        OnNoteListener onNoteListener;

        private ImageHolder(View view,OnNoteListener onNoteListener) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageListPicStadium);
            this.onNoteListener = onNoteListener;
            view.setOnLongClickListener(this);


        }

        @Override
        public boolean onLongClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());

            return false;
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
