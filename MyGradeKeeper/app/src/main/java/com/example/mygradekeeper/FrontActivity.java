package com.example.mygradekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FrontActivity extends AppCompatActivity {
    String username;
    String password;
    int usertype;
    TextView tv_name;
    Button btn_pwd,btn_accinfo,btn_courseinfo,btn_courselist,btn_grade,btn_mygrade,btn_stdinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        btn_pwd=findViewById(R.id.btn_pwd);
        btn_accinfo=findViewById(R.id.btn_accinfo);
        btn_courseinfo=findViewById(R.id.btn_courseinfo);
        btn_courselist=findViewById(R.id.btn_courselist);
        btn_grade=findViewById(R.id.btn_grade);
        btn_mygrade=findViewById(R.id.btn_mygrade);

        btn_stdinfo=findViewById(R.id.btn_stdinfo);


        tv_name=findViewById(R.id.tv_name);
        Intent intent=getIntent();
        User user=User.getUser();
        username=user.getName();
        password=user.getPwd();

         usertype=user.getUsertype();

        String welcomemsg="您好! "+username;
        switch (usertype) {
            case 0:
                welcomemsg+= "教授";
                btn_pwd.setVisibility(View.VISIBLE);
                btn_courseinfo.setVisibility(View.VISIBLE);
                btn_courselist.setVisibility(View.VISIBLE);
                btn_grade.setVisibility(View.VISIBLE);

                btn_stdinfo.setVisibility(View.VISIBLE);
                btn_accinfo.setVisibility(View.INVISIBLE);
                btn_mygrade.setVisibility(View.INVISIBLE);
                break;
            case 1:
                welcomemsg+= "同學";
                btn_pwd.setVisibility(View.VISIBLE);
                btn_courselist.setVisibility(View.VISIBLE);

                btn_courseinfo.setVisibility(View.INVISIBLE);
                btn_grade.setVisibility(View.INVISIBLE);
                btn_stdinfo.setVisibility(View.INVISIBLE);
                btn_accinfo.setVisibility(View.INVISIBLE);
                btn_mygrade.setVisibility(View.VISIBLE);
                break;
            case 2:
                welcomemsg+= "管理員";
                btn_pwd.setVisibility(View.VISIBLE);
                btn_courseinfo.setVisibility(View.VISIBLE);
                btn_courselist.setVisibility(View.VISIBLE);
                btn_grade.setVisibility(View.VISIBLE);

                btn_stdinfo.setVisibility(View.VISIBLE);
                btn_accinfo.setVisibility(View.VISIBLE);
                btn_mygrade.setVisibility(View.INVISIBLE);
                break;
            default:
                welcomemsg+= "鬼";
                break;
        }
        tv_name.setText(welcomemsg);
    }

    public void changepwd(View view) {
        Intent intent=new Intent(this,editpwdActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("usertype",usertype);
        startActivity(intent);
    }

    public void editacc(View view) {
        Intent intent=new Intent(this,editaccActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("usertype",usertype);
        startActivity(intent);
    }

    public void stdinfo(View view) {
        Intent intent=new Intent(this,studentinfoActivity.class);
        startActivity(intent);
    }
    public void courseinfo(View view) {
        Intent intent=new Intent(this,courseinfoActivity.class);
        startActivity(intent);
    }

    public void courselist(View view) {
        Intent intent=new Intent(this,courselistActivity.class);
        startActivity(intent);
    }

    public void gradecourse(View view) {
        Intent intent=new Intent(this,gradeActivity.class);
        startActivity(intent);
    }

    public void mygrade(View view) {
        Intent intent=new Intent(this,mygradeActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        User.logout();
        startActivity(intent);
    }
}