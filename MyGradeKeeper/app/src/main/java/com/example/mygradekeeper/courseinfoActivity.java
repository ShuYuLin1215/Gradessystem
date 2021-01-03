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

public class courseinfoActivity extends AppCompatActivity {
    static Activity activity;
    Context context;
    public static Activity getactivity(){
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseinfo);
        ListView lv_crslist =findViewById(R.id.lv_enrollstdlist);
        activity=this;
        context=this;
        List<Map<String, Object>> mItemList= new ArrayList<Map<String, Object>>();
        SharedPreferences pref = getSharedPreferences("courses", MODE_PRIVATE);
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Map<String, Object> item = new HashMap<String, Object>();

            String crsinfo= entry.getValue().toString();
            String[] infodata = crsinfo.split(",");
            item.put("courseid",entry.getKey());
            item.put("coursename",infodata[0]);
            item.put("credits",infodata[1]);
            item.put("teacher",infodata[2]);
            item.put("coursetype",infodata[3]);
            item.put("coursetime",infodata[4]);
            mItemList.add(item);
        }
        courseinfoAdapter adapter = new courseinfoAdapter(this,mItemList);
        lv_crslist.setAdapter(adapter);
    }

    public void addcourse(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.crsedit_layout, null);
            builder.setView(customLayout);
            TextView tv_title=customLayout.findViewById(R.id.tv_title);
            tv_title.setText("新增課程資訊");
            EditText et_crsid=customLayout.findViewById(R.id.et_choosecrsid);
            et_crsid.setEnabled(true);
            EditText et_crsname=customLayout.findViewById(R.id.et_crsname);
            EditText et_crsteacher=customLayout.findViewById(R.id.et_crsteacher);
            EditText et_crscredit=customLayout.findViewById(R.id.et_crscredit);
        EditText et_crstime=customLayout.findViewById(R.id.et_crstime);
        EditText et_crstype=customLayout.findViewById(R.id.et_crstype);
            Button btn_summitchange =customLayout.findViewById(R.id.btn_summitchange);
        et_crstime.setText("");
            et_crsid.setText("");
            et_crsname.setText("");
            et_crsteacher.setText("");
            et_crscredit.setText("");
            et_crstype.setText("");
            btn_summitchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String crsset =et_crsname.getText().toString()+","+et_crscredit.getText().toString()+","+et_crsteacher.getText().toString()+","+et_crstype.getText().toString()+","+et_crstime.getText().toString();
                    SharedPreferences pref2 = context.getSharedPreferences("courses", MODE_PRIVATE);
                    pref2.edit()
                            .putString(et_crsid.getText().toString(), crsset)
                            .commit();
                    Toast.makeText(context,"課程代號 "+et_crsid.getText().toString()+"已新增",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,FrontActivity.class);
                    context.startActivity(intent);

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

    }
}