package com.example.baby_shark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookStadiumAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BookStadium> BookStadiumList;

    public BookStadiumAdapter(Context context, int layout, List<BookStadium> bookStadiumList) {
        this.context = context;
        this.layout = layout;
        BookStadiumList = bookStadiumList;
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

    public List<BookStadium> getBookStadiumList() {
        return BookStadiumList;
    }

    public void setBookStadiumList(List<BookStadium> bookStadiumList) {
        BookStadiumList = bookStadiumList;
    }

    @Override
    public int getCount() {
        return BookStadiumList.size();
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
        TextView txtName,txtDescribe,txtPhone,txtTime;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            //lấy phần context nào
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder = new ViewHolder();
            //ánh xạ view
            holder.txtName = (TextView) convertView.findViewById(R.id.textviewName);
            holder.txtDescribe = (TextView) convertView.findViewById(R.id.textviewDescribe);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.textviewPhone);
            holder.txtTime = (TextView) convertView.findViewById(R.id.textviewTimePlay) ;
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //gán giá trị
        BookStadium bookStadium = BookStadiumList.get(position);
        holder.txtName.setText(bookStadium.getName());
        holder.txtDescribe.setText(bookStadium.getDescribe());
        holder.txtPhone.setText(bookStadium.getPhone());
        holder.txtTime.setText(bookStadium.getTime());
        return convertView;
    }
}
