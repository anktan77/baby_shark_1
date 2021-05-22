package com.example.baby_shark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baby_shark.OOP.InForResponseStadium;
import com.example.baby_shark.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleViewNTFAccount extends RecyclerView.Adapter<RecycleViewNTFAccount.ViewHolder> {
    private Context context;
    private List<InForResponseStadium> inForResponseStadiumList;

    public RecycleViewNTFAccount(Context context, List<InForResponseStadium> inForResponseStadiumList) {
        this.context = context;
        this.inForResponseStadiumList = inForResponseStadiumList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<InForResponseStadium> getInForResponseStadiumList() {
        return inForResponseStadiumList;
    }

    public void setInForResponseStadiumList(List<InForResponseStadium> inForResponseStadiumList) {
        this.inForResponseStadiumList = inForResponseStadiumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View view = inflater.inflate(R.layout.item_notification_account, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewNTFAccount.ViewHolder holder, int position) {
        InForResponseStadium inForResponseStadium = inForResponseStadiumList.get(position);
        holder.txtNameStadium.setText(inForResponseStadium.getNameStadium());
        holder.txtDay.setText(inForResponseStadium.getDayPlay());
        holder.txtDescribe.setText(inForResponseStadium.getDescribePlay());
        holder.txtTimeS.setText(inForResponseStadium.getTimeS());
        holder.txtTimeE.setText(inForResponseStadium.getTimeE());
        Picasso.with(getContext()).load(inForResponseStadium.getPicture()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return inForResponseStadiumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameStadium, txtAccept, txtTimeS, txtTimeE, txtDay, txtDescribe;
        RoundedImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameStadium = (TextView) itemView.findViewById(R.id.textViewNTFNameAccount);
            txtAccept = (TextView) itemView.findViewById(R.id.textViewNTFPhoneAccount);
            txtDay = (TextView) itemView.findViewById(R.id.textViewNTFDayAccount);
            txtDescribe = (TextView) itemView.findViewById(R.id.textViewNTFDescribeAccount);
            txtTimeS = (TextView) itemView.findViewById(R.id.textViewNTFTimeStartAccount);
            txtTimeE = (TextView) itemView.findViewById(R.id.textViewNTFTimeEndAccount);
            img = (RoundedImageView) itemView.findViewById(R.id.imageViewNTFAccount);
        }
    }
}
