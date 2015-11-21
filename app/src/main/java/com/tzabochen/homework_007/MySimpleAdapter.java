package com.tzabochen.homework_007;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends SimpleAdapter
{
    private Context context;

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
    {
        super(context, data, resource, from, to);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = super.getView(position, convertView, parent);
        ImageView imageView = (ImageView) view.findViewById(R.id.list_icon);
        String icon = AppMain.data.get(position).get("weather_icon").toString();
        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
        Picasso.with(this.context).load(iconUrl).into(imageView);
        return view;
    }
}
