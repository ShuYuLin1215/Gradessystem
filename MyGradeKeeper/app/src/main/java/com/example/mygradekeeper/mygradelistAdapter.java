package com.example.mygradekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class mygradelistAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater mLayInf;
        List<Map<String, Object>> mItemList;
        public mygradelistAdapter(Context context, List<Map<String, Object>> itemList)
        {
            this.context=context;
            mLayInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mItemList = itemList;
        }

        @Override
        public int getCount()
        {
            //取得 ListView 列表 Item 的數量
            return mItemList.size();
        }

        @Override
        public Object getItem(int position)
        {
            //取得 ListView 列表於 position 位置上的 Item
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            //取得 ListView 列表於 position 位置上的 Item 的 ID
            return position;
        }
        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        @Override
        public boolean isEnabled(int arg0)
        {
            return true;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            //設定與回傳 convertView 作為顯示在這個 position 位置的 Item 的 View。
            View v = mLayInf.inflate(R.layout.grade_item, parent, false);

            TextView tv_crsid = v.findViewById(R.id.tv_crsid);
            TextView tv_crsscore = v.findViewById(R.id.tv_crsscore);
            tv_crsid.setText(mItemList.get(position).get("courseid").toString());
            tv_crsscore.setText(mItemList.get(position).get("coursescore").toString());


            return v;
        }
    }

