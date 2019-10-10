package com.developer.yogesh.bustracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private String listitems[];
    private LayoutInflater layoutInflater;

    MyAdapter(Context context,String[] listitems){
        this.context=context;
        this.listitems=listitems;
        layoutInflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return listitems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listitems,null);
        TextView textView=convertView.findViewById(R.id.listitem);
        textView.setText(listitems[position]);
        return convertView;
    }
}
