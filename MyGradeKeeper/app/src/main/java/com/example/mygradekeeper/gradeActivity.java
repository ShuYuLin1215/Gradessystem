package com.example.mygradekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        ListView lv_crsgradelist =findViewById(R.id.lv_crsgradelist);
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
        coursegradeAdapter adapter = new coursegradeAdapter(this,mItemList);
        lv_crsgradelist.setAdapter(adapter);
    }

    public void back(View view) {
        Intent intent = new Intent(this,FrontActivity.class);
        startActivity(intent);
    }
}