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


		if (_bluetooth == null){
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


		/*Button loc= (Button) findViewById(R.id.location);
		loc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String rLatitude="";
				String rLongitude="";
				String smsg1=smsg;
				String rname="";
				String rgroup="";

				//String smsg1="$2nino#39&116*";

				char a;
				int i,j,k;

				//Setting the string format to be: $+groupnum+name+#+latitude+&+longitude+*

				char b=smsg1.charAt(0);

				if(b=='$'){
					rgroup=rgroup+smsg1.charAt(1);
					for(k=2;k<smsg1.length();k++){
						a=smsg1.charAt(k);
						if(a!='#'){
							rname=rname+a;
						}else {
							k++;
							break;
						}
					}

					for(i=k;i<smsg1.length();i++){

						a=smsg1.charAt(i);
						if(a!='&'){
							rLatitude=rLatitude+a;
						}else {
							i++;
							break;
						}
					}

					for(j=i;j<smsg1.length();j++){

						a=smsg1.charAt(j);
						if(a!='*'){
							rLongitude=rLongitude+a;
						}
						else{
							break;
						}
					}




				}


				Log.d(TAG,"latitude "+rLatitude+"longitude"+rLongitude+"name"+rname+"group"+rgroup);
				Data setLatBt = ((Data)getApplicationContext());
				setLatBt.setrLatitude(rLatitude);
				Data setLonBt=((Data)getApplicationContext());
				setLonBt.setrLongitude(rLongitude);
				Data setrname = ((Data)getApplicationContext());
				setrname.setrName(rname);
				Data setrgroup=((Data)getApplicationContext());
				setrgroup.setrFromID(rgroup);

				Intent intent_map=new Intent(BTClient.this,Maptest.class);
				startActivity(intent_map);


			}
		});



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


	public void onSendButtonClicked(View v){
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

		SendMessage sendMessage=new SendMessage(getApplicationContext());
		//Check the stored routing table.
		//String[] s=getData.getRoute().split("\\|");
		//String storeDest=s[s.length-1];
		/*if(getData.getRoute().equals(toID)){
			sendMessage.sendFormatMessage(DIALOGUE,broadCastCount,toID,message);
		}else {
			sendMessage.sendFormatMessage(ROUTE_DISCOVERY,broadCastCount,toID,"Hello!");
		}*/
		sendMessage.sendFormatMessage(ROUTE_DISCOVERY,broadCastCount,toID,"Hello!");


		//$+groupnum+name+#+latitude+&+longitude+*


		/*Data getLat = ((Data)getApplicationContext());
		String latitudeBt=getLat.getLatitude();
		Data getLon=((Data)getApplicationContext());
		String longitudeBt=getLon.getLongitude();
		Data getGro = ((Data)getApplicationContext());
		String mygr=getGro.getFromID();
		Data getNam=((Data)getApplicationContext());
		String myname=getNam.getName();

		String locationSend=null;
		if(latitudeBt!=null){
			locationSend="$"+mygr+myname+"#"+latitudeBt+"&"+longitudeBt+"*";
			System.out.println("\n \n I have gotten the location!!!");
		}

*/


		/*try{
			OutputStream os = _socket.getOutputStream();   //�������������

			//byte[] bos = edit0.getText().toString().getBytes();
			//byte[] bos =locationSend.getBytes();
			//imageString=imageString+"12345";
			byte[] bos = null;
			if(imageString!=null){
				bos=imageString.getBytes();
				imageString=null;
			}

			*//*else if(locationSend!=null){
				bos=locationSend.getBytes();
				System.out.println("\n \n \nI'm sending the location!!");
				System.out.println("\n\n\n\n"+mygr+myname);
			}*//*
			else if(edit0.getText().toString()!=null){
				Log.d(TAG,edit0.getText().toString());
				bos=("t"+edit0.getText().toString()).getBytes();
				System.out.println("\n \n \nI'm sending the text!!");

			}

			for(i=0;i<bos.length;i++){
				if(bos[i]==0x0a)n++;
			}
			byte[] bos_new = new byte[bos.length+n];
			n=0;
			for(i=0;i<bos.length;i++){
				if(bos[i]==0x0a){
					bos_new[n]=0x0d;
					n++;
					bos_new[n]=0x0a;
				}else{
					bos_new[n]=bos[i];
				}
				n++;
			}
			Log.d(TAG,edit0.getText().toString());
			os.write(bos_new);
		}catch(IOException e){
		}*/
	}




//中继send

/*
	public void send(String smsg){
		int i=0;
		int n=0;
		try{

			OutputStream os = _socket.getOutputStream();

			//byte[] bos = edit0.getText().toString().getBytes();
			//byte[] bos =locationSend.getBytes();
			//imageString=imageString+"12345";
			byte[] bos = null;

			bos=smsg.getBytes();


			for(i=0;i<bos.length;i++){
				if(bos[i]==0x0a)n++;
			}
			byte[] bos_new = new byte[bos.length+n];
			n=0;
			for(i=0;i<bos.length;i++){ //�ֻ��л���Ϊ0a,�����Ϊ0d 0a���ٷ���
				if(bos[i]==0x0a){
					bos_new[n]=0x0d;
					n++;
					bos_new[n]=0x0a;
				}else{
					bos_new[n]=bos[i];
				}
				n++;
			}

			os.write(bos_new);
		}catch(IOException e){
		}
	}

*/


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

/*
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
					}*/

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

/*

	Thread ReadThread=new Thread(){

		public void run(){
			int num = 0;
			byte[] buffer = new byte[1024];
			byte[] buffer_new = new byte[1024];
			int i = 0;
			int n = 0;
			bRun = true;
			//�����߳�
			while(true){
				try{
					while(is.available()==0){
						while(bRun == false){}
					}
					while(true){
						num = is.read(buffer);
						n=0;

						String s0 = new String(buffer,0,num);
						fmsg+=s0;
						for(i=0;i<num;i++){
							if((buffer[i] == 0x0d)&&(buffer[i+1]==0x0a)){
								buffer_new[n] = 0x0a;
								i++;
							}else{
								buffer_new[n] = buffer[i];
							}
							n++;
						}
						String s = new String(buffer_new,0,n);
						smsg+=s;
						if(is.available()==0)break;
					}

					if(smsg.equals("1234"))
						text0.setText("123");
					handler.sendMessage(handler.obtainMessage());
				}catch(IOException e){
				}
			}
		}
	};
*/

/*
	Handler handler= new Handler(){

		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(smsg.charAt(0)=='i') {
				if ((smsg.charAt(smsg.length() - 1) == '5')
						&& (smsg.charAt(smsg.length() - 2) == '4')
						&& (smsg.charAt(smsg.length() - 3) == '3')
						&& (smsg.charAt(smsg.length() - 4) == '2')
						&& (smsg.charAt(smsg.length() - 5) == '1')) {
//					dis.setText(smsg.substring(0,smsg.length()-5));
					bm = string2Bitmap(smsg.substring(0, smsg.length() - 5));
					iv.setImageBitmap(bm);
////////////////					send(smsg);
				}
			}
			if(smsg.charAt(0)=='t'){
				dis.setText(smsg.substring(1));
				//	dis.setText(smsg.substring(1));
//////////////////////				send(smsg.substring(1));
//				clear();
			}
			//dis.setText(smsg);
			sv.scrollTo(0, dis.getMeasuredHeight());

		}
	};*/


	public void onDestroy(){
		super.onDestroy();
		/*if(_socket!=null)
			try{
				_socket.close();
			}catch(IOException e){}*/
		Log.d(TAG,"BTClient destroyed.");

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
	}


	/*public void onClearButtonClicked(View v){
		smsg="";
		fmsg="";
		dis.setText(smsg);
		iv.setImageBitmap(null);
		return;
	}

	public void clear(){
		smsg="";
		fmsg="";
		return;
	}
*/


	/*public static Bitmap string2Bitmap(String st)
	{
		// OutputStream out;
		Bitmap bitmap = null;
		try
		{
			byte[] bitmapArray= Base64.decode(st, Base64.DEFAULT);
			ByteArrayInputStream baos = new ByteArrayInputStream(bitmapArray);
			bitmap = BitmapFactory.decodeStream(baos);
			return bitmap;
		}
		catch (Exception e)
		{
			return null;
		}
	}*/
}