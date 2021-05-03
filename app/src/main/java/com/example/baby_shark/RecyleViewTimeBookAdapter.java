package com.example.baby_shark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyleViewTimeBookAdapter extends RecyclerView.Adapter<RecyleViewTimeBookAdapter.ViewHolder> {
    private Context context;

    private List<RecycleViewTimeBook> recycleViewTimeBookList;

    public RecyleViewTimeBookAdapter(Context context, List<RecycleViewTimeBook> recycleViewTimeBookList) {
        this.context = context;

        this.recycleViewTimeBookList = recycleViewTimeBookList;
    }

    public RecyleViewTimeBookAdapter() {
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public List<RecycleViewTimeBook> getRecycleViewTimeBookList() {
        return recycleViewTimeBookList;
    }

    public void setRecycleViewTimeBookList(List<RecycleViewTimeBook> recycleViewTimeBookList) {
        this.recycleViewTimeBookList = recycleViewTimeBookList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View view = inflater.inflate(R.layout.recycle_time_book_stadium, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecycleViewTimeBook recycleViewTimeBook = recycleViewTimeBookList.get(position);
        holder.txtTimeS.setText(recycleViewTimeBook.getTimeS());
        holder.txtTimeE.setText(recycleViewTimeBook.getTimeE());
    }

    @Override
    public int getItemCount() {
        return recycleViewTimeBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTimeS,txtTimeE;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTimeS = (TextView) itemView.findViewById(R.id.textviewRCLTimeS);
            txtTimeE = (TextView) itemView.findViewById(R.id.textviewRCLTimeE);
        }
    }

}
