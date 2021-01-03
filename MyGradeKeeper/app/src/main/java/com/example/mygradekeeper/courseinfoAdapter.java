package com.example.mygradekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class courseinfoAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater mLayInf;
        List<Map<String, Object>> mItemList;
        public courseinfoAdapter(Context context, List<Map<String, Object>> itemList)
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
            View v = mLayInf.inflate(R.layout.course_item, parent, false);

            TextView tv_crsid = v.findViewById(R.id.tv_crsid);
            TextView tv_crsname = v.findViewById(R.id.tv_crsname);
            Button btn_delete = v.findViewById(R.id.btn_crsgrade);
            Button btn_edit = v.findViewById(R.id.btn_viewcrs);
            Button btn_viewstd = v.findViewById(R.id.btn_viewstd);
            tv_crsid.setText(mItemList.get(position).get("courseid").toString());
            tv_crsname.setText(mItemList.get(position).get("coursename").toString());
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = context.getSharedPreferences("courses", 0);
                    preferences.edit().remove(mItemList.get(position).get("courseid").toString()).commit();
                    Toast.makeText(context,"課程 "+mItemList.get(position).get("coursename").toString()+"已被刪除",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,FrontActivity.class);
                    context.startActivity(intent);
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // set the custom layout
                    final View customLayout = courseinfoActivity.getactivity().getLayoutInflater().inflate(R.layout.crsedit_layout, null);
                    builder.setView(customLayout);
                    TextView tv_title=customLayout.findViewById(R.id.tv_title);
                    tv_title.setText("編輯課程資料");
                    EditText et_crsid=customLayout.findViewById(R.id.et_choosecrsid);
                    et_crsid.setEnabled(false);
                    EditText et_crsname=customLayout.findViewById(R.id.et_crsname);
                    EditText et_crsteacher=customLayout.findViewById(R.id.et_crsteacher);
                    EditText et_crscredit=customLayout.findViewById(R.id.et_crscredit);
                    EditText et_crstype=customLayout.findViewById(R.id.et_crstype);
                    EditText et_crstime=customLayout.findViewById(R.id.et_crstime);
                    Button btn_summitchange =customLayout.findViewById(R.id.btn_summitchange);
                    et_crsid.setText(mItemList.get(position).get("courseid").toString());
                    et_crsname.setText(mItemList.get(position).get("coursename").toString());
                    et_crsteacher.setText(mItemList.get(position).get("teacher").toString());
                    et_crscredit.setText(mItemList.get(position).get("credits").toString());
                    et_crstype.setText(mItemList.get(position).get("coursetype").toString());
                    et_crstime.setText(mItemList.get(position).get("coursetime").toString());
                    btn_summitchange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String crsset =et_crsname.getText().toString()+","+et_crscredit.getText().toString()+","+et_crsteacher.getText().toString()+","+et_crstype.getText().toString()+","+et_crstime.getText().toString();
                            SharedPreferences pref2 = context.getSharedPreferences("courses", MODE_PRIVATE);
                            pref2.edit()
                                    .putString(et_crsid.getText().toString(), crsset)
                                    .commit();
                            Toast.makeText(context,"課程代號 "+et_crsid.getText().toString()+"已被更變",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(context,FrontActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
            btn_viewstd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Map<String, Object>> enrolledstudentList= new ArrayList<Map<String, Object>>();
                    SharedPreferences pref = courseinfoActivity.getactivity().getSharedPreferences("choosecourses", MODE_PRIVATE);
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
                    final View customLayout = courseinfoActivity.getactivity().getLayoutInflater().inflate(R.layout.viewstd_layout, null);
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

