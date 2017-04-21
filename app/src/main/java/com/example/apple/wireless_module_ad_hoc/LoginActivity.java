package com.example.apple.wireless_module_ad_hoc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;


public class LoginActivity extends AppCompatActivity implements OnClickListener {

    static String name,groupNum;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent=getIntent();

        Button SignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button location= (Button) findViewById(R.id.location);
        Button chat=(Button) findViewById(R.id.chat);
        Button home=(Button) findViewById(R.id.home);

        location.setOnClickListener(this);
        home.setOnClickListener(this);
        chat.setOnClickListener(this);
        SignInButton.setOnClickListener(this);

    }//onCreate end




    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.email_sign_in_button:
                EditText mEmailView = (EditText) findViewById(R.id.email);
                name = mEmailView.getText().toString();
                EditText groupView = (EditText) findViewById(R.id.password);
                groupNum = groupView.getText().toString();

                Data sname = ((Data)getApplicationContext());
                sname.setName(name);
                Data sgroupNum=((Data)getApplicationContext());
                sgroupNum.setFromID(groupNum);


                Log.i("Edit", "User name" + name);//Show the name and group number in the log
                Log.i("Edit", "Group number" + groupNum);

                Intent intent = new Intent(LoginActivity.this, Maptest.class);
                //Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("name", name);
                bundle.putCharSequence("GroupNum", groupNum);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.location:
                if(name==null||groupNum==null){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("Warning")//设置对话框标题

                            .setMessage("请输入组号及用户名并点击登录")//设置显示的内容

                            .show();
                }
                else{
                    //Intent locationIntent = new Intent(LoginActivity.this, MapActivity.class);
                    Intent locationIntent = new Intent(LoginActivity.this, Maptest.class);
                    startActivity(locationIntent);
                }
                break;

            case R.id.home:
                Intent homeIntent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(homeIntent);
                break;

            case R.id.chat:
                //Intent intent=new Intent(LoginActivity.this,BluetoothChat.class);
                Intent chatIntent=new Intent(LoginActivity.this,BTClient.class);
                startActivity(chatIntent);
                break;

            default:
                break;
        }
    }



}











