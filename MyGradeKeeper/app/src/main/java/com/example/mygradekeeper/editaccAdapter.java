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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;
import java.util.Map;

public class editaccAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater mLayInf;
        List<Map<String, Object>> mItemList;
        public editaccAdapter(Context context, List<Map<String, Object>> itemList)
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
            View v = mLayInf.inflate(R.layout.acc_item, parent, false);

            TextView tv_accname = v.findViewById(R.id.tv_crsid);
            Button btn_delete = v.findViewById(R.id.btn_crsgrade);
            Button btn_edit = v.findViewById(R.id.btn_viewcrs);
            tv_accname.setText(mItemList.get(position).get("username").toString());
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = context.getSharedPreferences("useraccount", 0);
                    preferences.edit().remove(mItemList.get(position).get("username").toString()).commit();
                    Toast.makeText(context,"帳號 "+mItemList.get(position).get("username").toString()+"已被刪除",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,FrontActivity.class);
                    context.startActivity(intent);
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // set the custom layout
                    final View customLayout = editaccActivity.getactivity().getLayoutInflater().inflate(R.layout.accedit_layout, null);
                    builder.setView(customLayout);
                    TextView tv_title=customLayout.findViewById(R.id.tv_title);
                    tv_title.setText("編輯帳戶");
                    EditText et_accname=customLayout.findViewById(R.id.et_choosecrsid);
                    et_accname.setEnabled(false);
                    EditText et_accpwd=customLayout.findViewById(R.id.et_crsname);
                    Button btn_summitchange =customLayout.findViewById(R.id.btn_summitchange);
                    et_accname.setText(mItemList.get(position).get("username").toString());
                    et_accpwd.setText(mItemList.get(position).get("password").toString());
                    btn_summitchange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences preferences = context.getSharedPreferences("useraccount", 0);
                            preferences.edit().putString(et_accname.getText().toString(),et_accpwd.getText().toString()).commit();
                            Toast.makeText(context,"帳號 "+et_accname.getText().toString()+"已被更變",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(context,FrontActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });


            return v;
        }
    }

