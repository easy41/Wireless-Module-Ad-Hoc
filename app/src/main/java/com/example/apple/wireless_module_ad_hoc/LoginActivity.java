package com.example.apple.wireless_module_ad_hoc;

import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements OnClickListener {

    static String name,myID;
    BluetoothSocket _socket = null;
    Data getData;
    String TAG="LoginActivity";
    String toID;
    String message;
    String BROADCASTID="00000000";
    private final static String RESCUE_INFORMATION ="3";


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent=getIntent();

        Button SignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button location= (Button) findViewById(R.id.location);
        Button chat=(Button) findViewById(R.id.chat);
        Button home=(Button) findViewById(R.id.home);
        ImageButton helpButton =(ImageButton)findViewById(R.id.help_button_login);
        location.setOnClickListener(this);
        home.setOnClickListener(this);
        chat.setOnClickListener(this);
        SignInButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

        getData=((Data)getApplicationContext());
        name=getData.getName();
        myID=getData.getFromID();



    }//onCreate end




    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.email_sign_in_button:
                EditText mEmailView = (EditText) findViewById(R.id.email);
                name = mEmailView.getText().toString();
                EditText groupView = (EditText) findViewById(R.id.password);
                myID = groupView.getText().toString();

                getData.setName(name);
                getData.setFromID(myID);

/*
                Intent intent = new Intent(LoginActivity.this, Maptest.class);
                //Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("name", name);
                bundle.putCharSequence("GroupNum", groupNum);
                intent.putExtras(bundle);
                startActivity(intent);*/
                break;

            case R.id.location:

                if(name.equals("FFFF")||myID.equals("FFFF")){
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
            case R.id.help_button_login:
                sendHelp();
                break;
            default:
                break;
        }
    }


    public void sendHelp() {

       /* try{
            if (_socket == null) {
                Log.d(TAG, "Socket is null");
                //Data applicationConstant = ((Data)getApplicationContext());
                _socket = getData.getSocket();
            }

        }catch (Exception e){
            Toast.makeText(LoginActivity.this,"Please connect to the bluetooth module first.",Toast.LENGTH_SHORT).show();
        }*/


        try{
            String name = getData.getName();
            String myID = getData.getFromID();
            Log.d(TAG, "Get data: " + name + "/" + myID);
            if(name.equals("FFFF")||myID.equals("FFFF")){
                Toast.makeText(LoginActivity.this,"Please login first.",Toast.LENGTH_SHORT).show();
                return;
            }

            //The help message will be broadcast.
            message = "Help！";//+location
            String route;
            String broadcastCount="0";

            SendMessage sendMessage = new SendMessage(getApplicationContext());

            toID = BROADCASTID;
            route = myID + "/";
            sendMessage.sendFormatMessage(RESCUE_INFORMATION, broadcastCount, name, myID, toID, route, message);

        }catch (Exception e){
            Toast.makeText(LoginActivity.this,"Please connect to the bluetooth module first.",Toast.LENGTH_SHORT).show();
        }

    }

}











