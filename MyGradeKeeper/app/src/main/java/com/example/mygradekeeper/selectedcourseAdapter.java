package com.example.mygradekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class selectedcourseAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater mLayInf;
        List<Map<String, Object>> mItemList;
        public selectedcourseAdapter(Context context, List<Map<String, Object>> itemList)
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
            View v = mLayInf.inflate(R.layout.selectedcourse_item, parent, false);

            TextView tv_crsid = v.findViewById(R.id.tv_crsid);
            Button btn_delete = v.findViewById(R.id.btn_crsgrade);
            Button btn_edit = v.findViewById(R.id.btn_edit);
            tv_crsid.setText(mItemList.get(position).get("courseid").toString());
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemList.remove(position);
                    notifyDataSetChanged();
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // set the custom layout
                    final View customLayout2 = studentinfoActivity.getactivity().getLayoutInflater().inflate(R.layout.choosecourse_layout, null);
                    builder.setView(customLayout2);
                    TextView tv_title=customLayout2.findViewById(R.id.tv_title);
                    AlertDialog dialog;
                    EditText et_choosecrsid= customLayout2.findViewById(R.id.et_choosecrsid);
                    Button btn_summitchange =customLayout2.findViewById(R.id.btn_summitchange);
                    et_choosecrsid.setText(mItemList.get(position).get("courseid").toString());
                    dialog = builder.create();
                    dialog.show();
                    btn_summitchange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemList.remove(position);
                            Map<String, Object> item = new HashMap<String, Object>();
                            item.put("courseid",et_choosecrsid.getText());
                            mItemList.add(item);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                }
            });



            return v;
        }
    }

