package com.example.mygradekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText editTextTextPersonName,editTextTextPassword;
    NumberPicker numberPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberPicker = (NumberPicker) findViewById(R.id.picker);
        inituseraccount();
        final String[] user_type = getResources().getStringArray(R.array.user_type);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(user_type.length - 1);
        numberPicker.setDisplayedValues(user_type);
        numberPicker.setValue(1);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        editTextTextPersonName=findViewById(R.id.editTextTextPersonName);
        editTextTextPassword=findViewById(R.id.editTextTextPassword);

    }

    public void onclick(View view) {
        String username=editTextTextPersonName.getText().toString();
        String password=editTextTextPassword.getText().toString();
        int usertype=numberPicker.getValue();
        if(logincheck(username,password)){
            User user=new User(username,password,usertype);
            Intent intent=new Intent(this,FrontActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("password",password);
            intent.putExtra("usertype",usertype);
            startActivity(intent);
        }else {
            Toast.makeText(this,"帳號密碼錯誤!",Toast.LENGTH_LONG).show();
        }

    }
    public Boolean logincheck(String username,String password){
        SharedPreferences pref = getSharedPreferences("useraccount", MODE_PRIVATE);
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().equals(username) && entry.getValue().equals(password))
                return true;
        }
        return false;
    }
    public void inituseraccount(){

        SharedPreferences pref = getSharedPreferences("useraccount", MODE_PRIVATE);
        pref.edit()
                .putString("admin", "adminpwd")
                .putString("std", "stdpwd")
                .putString("teach", "teachpwd")
        .commit();

        String stdset ="11027051,夏天天,110";
        String stdset2 ="11027010,藍天天,107";
        String stdset3 ="11027041,東天天,109";
        String stdset4 ="11027011,林天天,108";
        SharedPreferences pref2 = getSharedPreferences("students", MODE_PRIVATE);
        pref2.edit()
                .putString("std", stdset)
                .putString("blue", stdset2)
                .putString("east", stdset3)
                .putString("lin", stdset4)
                .commit();

        String crsset3 ="程式設計-1,3學分,王進添,選修,110-1";
        String crsset4 ="人工智慧2,3學分,添王進,必修,109-1";
        String crsset5 ="大數據,2學分,林添進,選修,109-1";
        String crsset6 ="高階演算法,2學分,王進添,選修,109-2";
        String crsset ="程式設計-5,3學分,王添進,必修,109-2";
        String crsset2 ="演算法,2學分,林儀,選修,109-2";
        SharedPreferences pref3 = getSharedPreferences("courses", MODE_PRIVATE);
        pref3.edit()
                .putString("CS101", crsset)
                .putString("CS121", crsset2)
                .putString("CS123", crsset3)
                .putString("CS234", crsset6)
                .putString("CS231", crsset4)
                .putString("CS221", crsset5)
                .commit();


        String choosecrsset1 ="CS101,CS123,CS234";
        String choosecrsset2 ="CS234,CS121,CS231,CS221";
        String choosecrsset3 ="CS101,CS123,CS234,CS234,CS121,CS231";
        String choosecrsset4 ="CS234,CS121,CS231,CS221,CS123";


        SharedPreferences pref4 = getSharedPreferences("choosecourses", MODE_PRIVATE);
        pref4.edit()
                .putString("11027041", choosecrsset1)
                .putString("11027010", choosecrsset2)
                .putString("11027051", choosecrsset3)
                .putString("11027011", choosecrsset4)
                .commit();
        SharedPreferences scorepref= getSharedPreferences("coursescore", MODE_PRIVATE);
            scorepref.edit()
                    .putString("11027051,CS234","90")
                    .putString("11027051,CS101","89")
                    .putString("11027051,CS123","96")
                    .commit();
        }
    }
