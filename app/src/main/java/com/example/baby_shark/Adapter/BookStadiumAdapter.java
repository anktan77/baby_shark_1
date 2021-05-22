package com.example.baby_shark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.baby_shark.InforBookStadium;
import com.example.baby_shark.R;

import java.util.List;

public class BookStadiumAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<InforBookStadium> inforBookStadiumList;


    public BookStadiumAdapter(Context context, int layout, List<InforBookStadium> inforBookStadiumList) {
        this.context = context;
        this.layout = layout;
        this.inforBookStadiumList = inforBookStadiumList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<InforBookStadium> getInforBookStadiumList() {
        return inforBookStadiumList;
    }

    public void setInforBookStadiumList(List<InforBookStadium> inforBookStadiumList) {
        this.inforBookStadiumList = inforBookStadiumList;
    }

    @Override
    public int getCount() {
        return inforBookStadiumList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{//sử dụng holder để tối ưu cho listview
        TextView txtName,txtDescribe,txtPhone,txtTimeS,txtTimeE,txtDay;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            //lấy phần context nào
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder = new ViewHolder();
            //ánh xạ view
            holder.txtDay = (TextView) convertView.findViewById(R.id.textViewLSDay);
            holder.txtName = (TextView) convertView.findViewById(R.id.textviewName);
            holder.txtDescribe = (TextView) convertView.findViewById(R.id.textviewDescribe);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.textviewPhone);
            holder.txtTimeS = (TextView) convertView.findViewById(R.id.textviewLSTimeStart) ;
            holder.txtTimeE = (TextView) convertView.findViewById(R.id.textViewLSTimeEnd);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //gán giá trị
        InforBookStadium inforBookStadium = inforBookStadiumList.get(position);
        holder.txtDay.setText(inforBookStadium.getDayPlay());
        holder.txtName.setText(inforBookStadium.getNamePlay());
        holder.txtDescribe.setText(inforBookStadium.getDescribePlay());
        holder.txtPhone.setText(inforBookStadium.getPhonePlay());
        holder.txtTimeS.setText(inforBookStadium.getTimeStart());
        holder.txtTimeE.setText(inforBookStadium.getTimeEnd());
        return convertView;
    }
}
