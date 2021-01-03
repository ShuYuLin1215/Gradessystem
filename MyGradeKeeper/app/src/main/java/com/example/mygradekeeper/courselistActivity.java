package com.example.mygradekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class courselistActivity extends AppCompatActivity {
    static Activity activity;

    public static Activity getactivity(){
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist);
        activity=this;
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.picker2);
        final String[] course_year_list = getResources().getStringArray(R.array.course_year_list);
        courselistAdapter adapter;
        ListView lv_courslist =findViewById(R.id.lv_crsyearlist);
        List<Map<String, Object>> mItemList= new ArrayList<Map<String, Object>>();
        SharedPreferences pref = getSharedPreferences("courses", MODE_PRIVATE);
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String crsinfo = entry.getValue().toString();
            String[] infodata = crsinfo.split(",");
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("courseid", entry.getKey());
            item.put("coursename", infodata[0]);
            item.put("credits", infodata[1]);
            item.put("teacher", infodata[2]);
            item.put("coursetype", infodata[3]);
            item.put("coursetime", infodata[4]);
            mItemList.add(item);
        }
        adapter=new courselistAdapter(this,mItemList);
        lv_courslist.setAdapter(adapter);


        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(course_year_list.length - 1);
        numberPicker.setDisplayedValues(course_year_list);
        numberPicker.setValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mItemList.clear();
                SharedPreferences pref = getSharedPreferences("courses", MODE_PRIVATE);
                Map<String, ?> allEntries = pref.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    String crsinfo= entry.getValue().toString();
                    String[] infodata = crsinfo.split(",");
                    if(course_year_list[newVal].equals(infodata[4])){
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("courseid",entry.getKey());
                        item.put("coursename",infodata[0]);
                        item.put("credits",infodata[1]);
                        item.put("teacher",infodata[2]);
                        item.put("coursetype",infodata[3]);
                        item.put("coursetime",infodata[4]);
                        mItemList.add(item);
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });


    }
}