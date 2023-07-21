package com.demoapp.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<String> dataList;
    private LayoutInflater inflater;

    public MyAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewItem = convertView.findViewById(R.id.textViewItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String item = dataList.get(position);
        viewHolder.textViewItem.setText(item);

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewItem;
    }
}
