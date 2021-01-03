package com.example.mygradekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coursegradeActivity extends AppCompatActivity {
    List<Map<String, Object>> mItemList;
    ListView lv_gradinglist;
    String courseid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursegrade);
        Intent intent=getIntent();
        courseid=intent.getStringExtra("courseid");
        mItemList= new ArrayList<Map<String, Object>>();

        SharedPreferences pref = getSharedPreferences("choosecourses", MODE_PRIVATE);
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {


            String crsinfo= entry.getValue().toString();
            if(crsinfo.contains(courseid)){
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("studentid",entry.getKey());
                mItemList.add(item);
            }

            }
        gradinglistAdapter adapter =new gradinglistAdapter(this,mItemList);
        lv_gradinglist=findViewById(R.id.lv_gradinglist);
        lv_gradinglist.setAdapter(adapter);

        }


    public void sendscore(View view) {
        SharedPreferences scorepref= getSharedPreferences("coursescore", MODE_PRIVATE);
        for(int i=0;i<mItemList.size();i++){
            View v= lv_gradinglist.getChildAt(i);
            TextView tv_sid=v.findViewById(R.id.tv_crsid);
            EditText et_score=v.findViewById(R.id.et_score);
            String sid=tv_sid.getText().toString()+","+courseid;
            String score=et_score.getText().toString();
            scorepref.edit().putString(sid,score).commit();
        }
        Toast.makeText(this,"已送出 "+courseid+" 成績!",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,gradeActivity.class);
        startActivity(intent);
    }

}
