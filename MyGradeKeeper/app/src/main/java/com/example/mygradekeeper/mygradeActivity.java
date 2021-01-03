package com.example.mygradekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mygradeActivity extends AppCompatActivity {
    List<Map<String, Object>> mItemList;
    String stdid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygrade);
        TextView tv_stdid=findViewById(R.id.tv_stdid);
        mItemList= new ArrayList<Map<String, Object>>();

        SharedPreferences pref2 = getSharedPreferences("students", MODE_PRIVATE);
        Map<String, ?> allEntries = pref2.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(entry.getKey().contains(User.getUser().getName())){
                String stdinfo= entry.getValue().toString();
                String std[]=stdinfo.split(",");
                stdid=std[0];
            }
        }
        tv_stdid.setText(stdid);

        SharedPreferences pref = getSharedPreferences("coursescore", MODE_PRIVATE);
        Map<String, ?> allEntries2 = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries2.entrySet()) {
            if(entry.getKey().contains(stdid)){
                Map<String, Object> item = new HashMap<String, Object>();
                String gradestr= entry.getKey();
                String gradeinfo[]=gradestr.split(",");
                item.put("courseid",gradeinfo[1]);
                item.put("coursescore",entry.getValue());
                mItemList.add(item);
            }

        }

        ListView lv_gradelist=findViewById(R.id.lv_gradelist);
        mygradelistAdapter adapter =new mygradelistAdapter(this,mItemList);
        lv_gradelist.setAdapter(adapter);
    }

    public void print(View view) {
        Toast.makeText(this,"已送出列印請求",Toast.LENGTH_SHORT).show();
    }

}