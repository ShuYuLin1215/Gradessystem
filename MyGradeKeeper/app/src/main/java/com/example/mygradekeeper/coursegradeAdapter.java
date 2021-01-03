package com.example.mygradekeeper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class coursegradeAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater mLayInf;
        List<Map<String, Object>> mItemList;
        public coursegradeAdapter(Context context, List<Map<String, Object>> itemList)
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
            View v = mLayInf.inflate(R.layout.course_grade_item, parent, false);

            TextView tv_crsid = v.findViewById(R.id.tv_crsid);
            TextView tv_crsname = v.findViewById(R.id.tv_crsname);
            tv_crsid.setText(mItemList.get(position).get("courseid").toString());
            tv_crsname.setText(mItemList.get(position).get("coursename").toString());

            Button btn_crsgrade=v.findViewById(R.id.btn_crsgrade);
            btn_crsgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,coursegradeActivity.class);
                    intent.putExtra("courseid",tv_crsid.getText().toString());
                    context.startActivity(intent);
                }

            });

            return v;
        }
    }

