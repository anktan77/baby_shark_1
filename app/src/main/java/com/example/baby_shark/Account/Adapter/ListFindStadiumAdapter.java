package com.example.baby_shark.Account.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baby_shark.Account.OOP.ListFindStadium;
import com.example.baby_shark.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListFindStadiumAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private int layout;
    private List<ListFindStadium> listFindStadiums;
    private List<ListFindStadium> mlistOld;


    public ListFindStadiumAdapter(Context context, int layout, List<ListFindStadium> listFindStadiums) {
        this.context = context;
        this.layout = layout;
        this.listFindStadiums = listFindStadiums;
        this.mlistOld = listFindStadiums;
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

    public List<ListFindStadium> getListFindStadiums() {
        return listFindStadiums;
    }

    public void setListFindStadiums(List<ListFindStadium> listFindStadiums) {
        this.listFindStadiums = listFindStadiums;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    listFindStadiums = mlistOld;
                }else {
                    List<ListFindStadium> list = new ArrayList<>();
                    for (ListFindStadium s : mlistOld){
                        if (s.getNameStadium().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(s);
                        }
                    }
                    listFindStadiums = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFindStadiums;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFindStadiums = (List<ListFindStadium>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getCount() {
        return listFindStadiums.size();
    }

    @Override
    public Object getItem(int position) {
        return listFindStadiums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class FindHolder{
        TextView txtNameStadium, txtAddressStadium;
        ImageView imgStadium;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         FindHolder holder;
        if (convertView == null){
            //lấy phần context nào
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder = new FindHolder();
            holder.txtAddressStadium = (TextView) convertView.findViewById(R.id.textViewFindAddress);
            holder.txtNameStadium = (TextView) convertView.findViewById(R.id.textViewFindName);
            holder.imgStadium = (ImageView) convertView.findViewById(R.id.imageViewFindPicture);
            convertView.setTag(holder);
        }
        else {
            holder = (FindHolder) convertView.getTag();;
        }

        ListFindStadium listFindStadium = listFindStadiums.get(position);
        holder.txtNameStadium.setText(listFindStadium.getNameStadium());
        holder.txtAddressStadium.setText(listFindStadium.getAddressStadium());
        Picasso.with(getContext()).load(listFindStadium.getPictureStadium()).into(holder.imgStadium);
        return convertView;
    }
}
