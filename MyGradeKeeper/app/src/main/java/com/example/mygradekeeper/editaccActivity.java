package com.example.mygradekeeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class editaccActivity extends AppCompatActivity {
    ListView lv_acclist;
    Context context;
    static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editacc);
        activity=this;
        context=this;

        List<Map<String, Object>> mItemList= new ArrayList<Map<String, Object>>();
        SharedPreferences pref = getSharedPreferences("useraccount", MODE_PRIVATE);
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("username",entry.getKey());
            item.put("password",entry.getValue());
            mItemList.add(item);
        }


        lv_acclist=findViewById(R.id.lv_crsyearlist);
        editaccAdapter editaccAdapter=new editaccAdapter(this,mItemList);
        lv_acclist.setAdapter(editaccAdapter);


    }
    public static Activity getactivity(){
        return activity;
    }

    public void addacc(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the custom layout
        final View customLayout = editaccActivity.getactivity().getLayoutInflater().inflate(R.layout.accedit_layout, null);
        builder.setView(customLayout);
        TextView tv_title=customLayout.findViewById(R.id.tv_title);
        tv_title.setText("新增帳戶");
        EditText et_accname=customLayout.findViewById(R.id.et_choosecrsid);
        et_accname.setEnabled(true);
        EditText et_accpwd=customLayout.findViewById(R.id.et_crsname);
        Button btn_summitchange =customLayout.findViewById(R.id.btn_summitchange);
        et_accname.setText("");
        et_accpwd.setText("");
        btn_summitchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences("useraccount", 0);
                preferences.edit().putString(et_accname.getText().toString(),et_accpwd.getText().toString()).commit();
                Toast.makeText(context,"帳號 "+et_accname.getText().toString()+"已新增",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context,FrontActivity.class);
                getactivity().startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}