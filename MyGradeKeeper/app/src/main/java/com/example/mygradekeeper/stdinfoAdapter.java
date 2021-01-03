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

public class stdinfoAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater mLayInf;
        List<Map<String, Object>> mItemList;
        public stdinfoAdapter(Context context, List<Map<String, Object>> itemList)
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
            View v = mLayInf.inflate(R.layout.std_item, parent, false);

            TextView tv_accname = v.findViewById(R.id.tv_crsid);
            Button btn_delete = v.findViewById(R.id.btn_crsgrade);
            Button btn_edit = v.findViewById(R.id.btn_edit);
            Button btn_viewcrs = v.findViewById(R.id.btn_viewcrs);

            tv_accname.setText(mItemList.get(position).get("studentname").toString());
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = context.getSharedPreferences("students", 0);
                    preferences.edit().remove(mItemList.get(position).get("userid").toString()).commit();
                    Toast.makeText(context,"帳號 "+mItemList.get(position).get("studentname").toString()+"已被刪除",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,FrontActivity.class);
                    context.startActivity(intent);
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // set the custom layout
                    final View customLayout = studentinfoActivity.getactivity().getLayoutInflater().inflate(R.layout.stdedit_layout, null);
                    builder.setView(customLayout);
                    TextView tv_title=customLayout.findViewById(R.id.tv_title);
                    tv_title.setText("編輯學生資料");
                    EditText et_accid=customLayout.findViewById(R.id.et_choosecrsid);
                    et_accid.setEnabled(false);
                    EditText et_stdid=customLayout.findViewById(R.id.et_crsname);
                    EditText et_stdname=customLayout.findViewById(R.id.et_crsteacher);
                    EditText et_enrollyear=customLayout.findViewById(R.id.et_crscredit);
                    Button btn_summitchange =customLayout.findViewById(R.id.btn_summitchange);
                    et_accid.setText(mItemList.get(position).get("userid").toString());
                    et_stdid.setText(mItemList.get(position).get("studentid").toString());
                    et_stdname.setText(mItemList.get(position).get("studentname").toString());
                    et_enrollyear.setText(mItemList.get(position).get("enrollyear").toString());
                    btn_summitchange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String stdset =et_stdid.getText().toString()+","+et_stdname.getText().toString()+","+et_enrollyear.getText().toString();

                            SharedPreferences pref2 = context.getSharedPreferences("students", MODE_PRIVATE);
                            pref2.edit()
                                    .putString(et_accid.getText().toString(), stdset)
                                    .commit();
                            Toast.makeText(context,"學生帳號 "+et_accid.getText().toString()+"已被更變",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(context,FrontActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
            btn_viewcrs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // set the custom layout
                    final View customLayout = studentinfoActivity.getactivity().getLayoutInflater().inflate(R.layout.courseinfo_layout, null);
                    builder.setView(customLayout);

                    List<Map<String, Object>> mItemList2= new ArrayList<Map<String, Object>>();
                    SharedPreferences pref = studentinfoActivity.getactivity().getSharedPreferences("choosecourses", MODE_PRIVATE);
                    Map<String, ?> allEntries = pref.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        if(entry.getKey().equals(mItemList.get(position).get("studentid").toString())){

                            String choosecrsinfo= entry.getValue().toString();
                            String[] choosecrslist = choosecrsinfo.split(",");
                            for(String course:choosecrslist){
                                Map<String, Object> item = new HashMap<String, Object>();
                                item.put("courseid",course);
                                mItemList2.add(item);
                            }
                        }
                    }
                    ListView lv_seletedcourse=customLayout.findViewById(R.id.lv_enrollstdlist);
                    selectedcourseAdapter adapter =new selectedcourseAdapter(context,mItemList2);
                    lv_seletedcourse.setAdapter(adapter);
                    Button btn_addcourse=customLayout.findViewById(R.id.btn_addcrs);
                    btn_addcourse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            // set the custom layout
                            final View customLayout2 = studentinfoActivity.getactivity().getLayoutInflater().inflate(R.layout.choosecourse_layout, null);
                            builder.setView(customLayout2);
                            TextView tv_title=customLayout2.findViewById(R.id.tv_title);
                            tv_title.setText("新增課程");
                            EditText et_choosecrsid= customLayout2.findViewById(R.id.et_choosecrsid);
                            Button btn_summitchange =customLayout2.findViewById(R.id.btn_summitchange);
                            et_choosecrsid.setText("");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            btn_summitchange.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Map<String, Object> item = new HashMap<String, Object>();
                                    item.put("courseid",et_choosecrsid.getText());
                                    mItemList2.add(item);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            return v;
        }
    }

