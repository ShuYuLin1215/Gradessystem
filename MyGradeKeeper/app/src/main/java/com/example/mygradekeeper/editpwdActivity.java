package com.example.mygradekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class editpwdActivity extends AppCompatActivity {
    TextView tw_edtpwdwelmsg;
    String username;
    String password;
    EditText et_edpwd,et_reedpwd;
    int usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpwd);
        et_edpwd=findViewById(R.id.et_edpwd);
        et_reedpwd=findViewById(R.id.et_reedpwd);
        tw_edtpwdwelmsg=findViewById(R.id.tw_edtpwdwelmsg);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        password=intent.getStringExtra("password");
        usertype=intent.getIntExtra("usertype",4);
        String welcomemsg="使用者 "+username;
        switch (usertype) {
            case 0:
                welcomemsg+= " 教授";
                break;
            case 1:
                welcomemsg+= " 同學";
                break;
            case 2:
                welcomemsg+= " 管理員";
                break;
            default:
                welcomemsg+= " 鬼";
                break;
        }
        tw_edtpwdwelmsg.setText(welcomemsg+" 修改密碼 ");

    }

    public void summitpwd(View view) {
        if(et_edpwd.getText().toString().equals(et_reedpwd.getText().toString())){
            SharedPreferences pref = getSharedPreferences("useraccount", MODE_PRIVATE);
            pref.edit()
                    .putString(username, et_edpwd.getText().toString())
                    .commit();
            Toast.makeText(this,"密碼已更變!",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"密碼不同!",Toast.LENGTH_LONG).show();
        }
        Intent intent=new Intent(this,FrontActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("usertype",usertype);
        startActivity(intent);
    }
}