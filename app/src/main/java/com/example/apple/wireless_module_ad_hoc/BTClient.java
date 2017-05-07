package com.example.apple.wireless_module_ad_hoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class BTClient extends AppCompatActivity {

	private final static int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_INVOKE_IMAGE = 3;

	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";

	private InputStream is;
	private TextView text0;
	private EditText edit0;
	private TextView dis;
	private ScrollView sv;
	private String smsg = "";
	private String fmsg = "";

	private Button pic;

	private ImageView iv;
	private Bitmap bm;
	private String imageString;
	private String accept="";


	public String filename="";
	BluetoothDevice _device = null;
	BluetoothSocket _socket = null;
	boolean _discoveryFinished = false;
	boolean bRun = true;
	boolean bThread = false;
	String TAG="蓝牙：";

	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();

	Data getData;
	EditText toUser;
	String toID;
	String message;
	private final static String ROUTE_DISCOVERY ="1";
	private final static String DIALOGUE ="2";
	private final static String RESCUE_INFORMATION ="3";
	private final static String ROAD_CONDITION ="4";

	//final static String smsg1;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		/*
        get the location values from MapActivity
         */
		Intent blueIntent=getIntent();
		final Bundle blueBundle=blueIntent.getExtras();


		setContentView(R.layout.blue_main);

		text0 = (TextView)findViewById(R.id.Text0);
		edit0 = (EditText)findViewById(R.id.Edit0);
		sv = (ScrollView)findViewById(R.id.ScrollView01);
		dis = (TextView) findViewById(R.id.in);

		iv = (ImageView)findViewById(R.id.image);

		pic=(Button)findViewById(R.id.Button_pic);
		pic.setOnClickListener(listener);
		pic.setText("pic");


		getData = ((Data)getApplicationContext());
		toUser=(EditText)findViewById(R.id.to_user);


		/*if (_bluetooth == null){
			Toast.makeText(this, "无法打开蓝牙，请确认是否有蓝牙功能", Toast.LENGTH_LONG).show();
			finish();
			return;
		}


		new Thread(){
			public void run(){
				if(_bluetooth.isEnabled()==false){
					_bluetooth.enable();
				}
			}
		}.start();
*/



	}

	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(BTClient.this, Image.class);
			startActivityForResult(intent, REQUEST_INVOKE_IMAGE);

		}
	};

//正常send


	/*public void onSendButtonClicked(View v){
		int i=0;
		int n=0;

		if(_socket==null){
			Log.d(TAG,"Socket is null");
			//Data applicationConstant = ((Data)getApplicationContext());
			_socket=getData.getSocket();
		}

		String broadCastCount="0";
		toID=toUser.getText().toString();
		message=edit0.getText().toString();
		String route;

		String name=getData.getName();
		String fromID=getData.getFromID();
		Log.d(TAG,"Get data: "+name+"/"+fromID);

		SendMessage sendMessage=new SendMessage(getApplicationContext());

		//Check the stored routing table.
		if(!getData.getRoute().equals("0000")){
			String[] s=getData.getRoute().split("/");
			String storeDest=s[s.length-1];
			if(storeDest.equals(toID)){
				route=getData.getRoute();
				Log.d(TAG,"Get stored route: "+route);
				sendMessage.sendFormatMessage(DIALOGUE,broadCastCount,name,fromID,toID,route,message);
			}else {
				route=getData.getFromID()+"/";
				sendMessage.sendFormatMessage(ROUTE_DISCOVERY,broadCastCount,name,fromID,toID,route,message);
			}
		}
		else {
			route=getData.getFromID()+"/";
			sendMessage.sendFormatMessage(ROUTE_DISCOVERY,broadCastCount,name,fromID,toID,route,message);
		}

		//sendMessage.sendFormatMessage(ROUTE_DISCOVERY,broadCastCount,toID,route,"Hello!");


		//$+groupnum+name+#+latitude+&+longitude+*


	}*/



//Start

/*
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){

			case REQUEST_CONNECT_DEVICE:

				if (resultCode == Activity.RESULT_OK) {

					String address = data.getExtras()
							.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

					_device = _bluetooth.getRemoteDevice(address);

					try{
						_socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
					}catch(IOException e){
						Toast.makeText(this, "连接失败，请连接蓝牙模块", Toast.LENGTH_SHORT).show();
					}

					Button btn = (Button) findViewById(R.id.Button03);
					try{
						_socket.connect();
						Toast.makeText(this, "连接"+_device.getName()+"成功", Toast.LENGTH_SHORT).show();
						btn.setText("断开");
					}catch(IOException e){
						try{
							Toast.makeText(this, "连接失败，请连接蓝牙模块", Toast.LENGTH_SHORT).show();
							_socket.close();
							_socket = null;
						}catch(IOException ee){
							Toast.makeText(this, "连接失败，请连接蓝牙模块", Toast.LENGTH_SHORT).show();
						}

						return;
					}

*//*
					try{
						is = _socket.getInputStream();
					}catch(IOException e){
						Toast.makeText(this, "接收数据失败", Toast.LENGTH_SHORT).show();
						return;
					}
					if(bThread==false){
						ReadThread.start();
						bThread=true;
					}else{
						bRun = true;
					}*//*

					getData.setSocket(_socket);
					Log.d(TAG,"Setting socket.");

					Intent intent=new Intent(BTClient.this, BTService.class);
					startService(intent);

				}
				break;

			case REQUEST_INVOKE_IMAGE:
				// get a image from the gallery and store it in string type
				imageString = data.getStringExtra("Data")+"12345";
				break;

			default:break;
		}
	}


	public void onConnectButtonClicked(View v){
		if(_bluetooth.isEnabled()==false){
			Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
			return;
		}


		//DeviceListActivity
		Button btn = (Button) findViewById(R.id.Button03);
		if(_socket==null){
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		}
		else{

			try{

				is.close();
				_socket.close();
				_socket = null;
				bRun = false;
				btn.setText("连接");
			}catch(IOException e){}
		}
		return;
	}*/

//End

	public void onDestroy(){
		super.onDestroy();
		/*if(_socket!=null)
			try{
				_socket.close();
			}catch(IOException e){}*/
		Log.d(TAG,"BTClient destroyed.");

	}



}