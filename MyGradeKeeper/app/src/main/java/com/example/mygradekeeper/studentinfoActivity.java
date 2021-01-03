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

public class studentinfoActivity extends AppCompatActivity {
    Context context;
    static Activity activity;

    public static Activity getactivity(){
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        context=this;
        setContentView(R.layout.activity_studentinfo);
        ListView lv_stdinfo=findViewById(R.id.lv_stdlist);

        List<Map<String, Object>> mItemList= new ArrayList<Map<String, Object>>();
        SharedPreferences pref = getSharedPreferences("students", MODE_PRIVATE);
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Map<String, Object> item = new HashMap<String, Object>();

            String stdinfo= entry.getValue().toString();
            String[] infodata = stdinfo.split(",");
            item.put("userid",entry.getKey());
            item.put("studentid",infodata[0]);
            item.put("enrollyear",infodata[2]);
            item.put("studentname",infodata[1]);
            mItemList.add(item);
        }
        stdinfoAdapter adapter=new stdinfoAdapter(this,mItemList);
        lv_stdinfo.setAdapter(adapter);
    }

    public void addstd(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.stdedit_layout, null);
            builder.setView(customLayout);
            TextView tv_title=customLayout.findViewById(R.id.tv_title);
            tv_title.setText("新增學生帳戶");
            EditText et_accid=customLayout.findViewById(R.id.et_choosecrsid);
            et_accid.setEnabled(true);
            EditText et_stdid=customLayout.findViewById(R.id.et_crsname);
            EditText et_stdname=customLayout.findViewById(R.id.et_crsteacher);
            EditText et_enrollyear=customLayout.findViewById(R.id.et_crscredit);
            Button btn_summitchange =customLayout.findViewById(R.id.btn_summitchange);
            et_accid.setText("");
            et_stdid.setText("");
            et_stdname.setText("");
            et_enrollyear.setText("");
            btn_summitchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stdset =et_stdid.getText().toString()+","+et_stdname.getText().toString()+","+et_enrollyear.getText().toString();
                    SharedPreferences preferences = getSharedPreferences("students", 0);
                    preferences.edit().putString(et_accid.getText().toString(),stdset).commit();
                    Toast.makeText(context,"帳號 "+et_accid.getText().toString()+"已新增",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,FrontActivity.class);
                    getactivity().startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
    }
}