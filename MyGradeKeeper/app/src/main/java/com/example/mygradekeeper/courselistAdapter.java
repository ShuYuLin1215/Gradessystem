package com.example.mygradekeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class courselistAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater mLayInf;
        List<Map<String, Object>> mItemList;
        public courselistAdapter(Context context, List<Map<String, Object>> itemList)
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
            View v = mLayInf.inflate(R.layout.course_item_uneditable, parent, false);

            TextView tv_crsid = v.findViewById(R.id.tv_crsid);
            TextView tv_crsname = v.findViewById(R.id.tv_crsname);
            TextView tv_crscredit = v.findViewById(R.id.tv_crscredit);
            TextView tv_crsteacher = v.findViewById(R.id.tv_crsteacher);
            TextView tv_crsyear = v.findViewById(R.id.tv_crsyear);
            tv_crsid.setText(mItemList.get(position).get("courseid").toString());
            tv_crsname.setText(mItemList.get(position).get("coursename").toString());
            tv_crscredit.setText(mItemList.get(position).get("credits").toString());
            tv_crsteacher.setText(mItemList.get(position).get("teacher").toString());
            tv_crsyear.setText(mItemList.get(position).get("coursetime").toString());
            Button btn_viewcrsstd=v.findViewById(R.id.btn_viewcrsstd);
            btn_viewcrsstd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Map<String, Object>> enrolledstudentList= new ArrayList<Map<String, Object>>();
                    SharedPreferences pref = courselistActivity.getactivity().getSharedPreferences("choosecourses", MODE_PRIVATE);
                    Map<String, ?> allEntries = pref.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        String choosecrsinfo= entry.getValue().toString();
                        if(choosecrsinfo.contains(mItemList.get(position).get("courseid").toString())){
                            Map<String, Object> item = new HashMap<String, Object>();
                            item.put("studentid",entry.getKey());
                            enrolledstudentList.add(item);
                        }
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // set the custom layout
                    final View customLayout = courselistActivity.getactivity().getLayoutInflater().inflate(R.layout.viewstd_layout, null);
                    builder.setView(customLayout);

                    ListView lv_enrollstdlist =customLayout.findViewById(R.id.lv_enrollstdlist);
                    enrollstdAdapter adapter=new enrollstdAdapter(context,enrolledstudentList);
                    lv_enrollstdlist.setAdapter(adapter);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

            });

            return v;
        }
    }

