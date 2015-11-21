package com.tzabochen.homework_007;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentItems extends ListFragment
{
    private ArrayList<HashMap<String, String>> itemList = new ArrayList<>();
    private ItemSelectedListener itemSelectedListener;
    private MySimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // INITIALIZATION
        initItemList();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        itemSelectedListener = (ItemSelectedListener) context;
    }

    // SEND SELECTED POSITION
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        itemSelectedListener.itemSelected(position);
    }

    private void initItemList()
    {
        if (AppMain.data != null)
        {
            itemList = AppMain.data;

            String[] keyArray = new String[]{"day_of_week", "day_of_month", "month", "temp"};
            int[] idArray = new int[]{R.id.list_day_of_week, R.id.list_day_of_month, R.id.list_month, R.id.list_temp};

            adapter = new MySimpleAdapter(getActivity(), itemList, R.layout.list_items, keyArray, idArray);

            this.setListAdapter(adapter);
        }
    }
}
