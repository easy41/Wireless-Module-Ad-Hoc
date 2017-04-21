package com.example.apple.wireless_module_ad_hoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//
import android.util.Log;
import android.widget.Button;
import android.view.View;
//
import android.location.LocationManager;
import android.location.Location;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
//
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
//
import java.util.ArrayList;
import java.util.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextOptions;

import android.view.View.OnClickListener;



public class MapActivity extends AppCompatActivity {


    static double myLongitude;
    static double myLatitude;
    MapView mMapView = null;
    BaiduMap mBaiduMap=null;

    List<Marker> marker =new ArrayList<Marker>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);




        /*
            activity之间的跳转
         */

        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();





        Button location_map= (Button) findViewById(R.id.location_map);
        Button chat_map=(Button) findViewById(R.id.chat_map);
        Button home_map=(Button) findViewById(R.id.home_map);

        location_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_l = new Intent(MapActivity.this, MapActivity.class);
                startActivity(intent_l);
            }
        });

        home_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent_h=new Intent(MapActivity.this,LoginActivity.class);
                startActivity(intent_h);
            }
        });

        chat_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MapActivity.this,BTClient.class);
                startActivity(intent);
            }
        });

        getLongitudeAndLatitude();
        /*
            地图部分，获取坐标值
         */




        Button OK=(Button)findViewById(R.id.OK);

        //getLongitudeAndLatitude();


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String a = ((EditText) findViewById(R.id.editText)).getText().toString();
                //String l = ((EditText) findViewById(R.id.editText2)).getText().toString();


                Data getLat = ((Data)getApplicationContext());
                String a=getLat.getrLatitude();
                Data getLon=((Data)getApplicationContext());
                String l=getLon.getrLongitude();
                Data gName = ((Data)getApplicationContext());
                String rname=gName.getrName();
                Data gGroup=((Data)getApplicationContext());
                String rgroup=gGroup.getrFromID();



                System.out.printf("\narashiarashiarashiarashiarashi");
                System.out.printf("la "+a+"Lo "+l);

                double longitude = Double.parseDouble(l);
                double latitude = Double.parseDouble(a);

                LatLng point = new LatLng(latitude, longitude);

                //定义Maker坐标点
                // LatLng point = new LatLng(39.963175, 116.400244);北邮：39.966912,116.361968
                //宏福:116.381505,40.114778




                    getLongitudeAndLatitude();


                    BitmapDescriptor bitmap;

                    //switch (bundle.getString("GroupNum")) {
                    //String num="2";
                    switch (rgroup) {
                        case "1": {
                            bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.icon_marka);
                            break;
                        }

                        case "2": {
                            bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.icon_markb);
                            break;
                        }

                        case "3": {
                            bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.icon_markc);
                            break;
                        }

                        default:
                            bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.icon_marknull);

                    }


                    OverlayOptions options = new MarkerOptions()
                            .position(point)
                            .icon(bitmap)
                            .zIndex(9)
                            .draggable(true);

                    marker.add((Marker) mBaiduMap.addOverlay(options));

                    //定义文字所显示的坐标点
                    LatLng llText = new LatLng(latitude, longitude);
                    //构建文字Option对象，用于在地图上添加文字



                OverlayOptions textOption = new TextOptions()
                        .bgColor(0xAAFFFF00)
                        .fontSize(24)
                        .fontColor(0xFFFF00FF)
                                //.text("Position")
                        .text("G" + rgroup + "/" + rname + "\n" + "longitude:" + l + "\n" + "latitude:" + a)
                        .rotate(-0)
                        .position(llText);

                mBaiduMap.addOverlay(textOption);



            }
        });


    }



    /**
     * 获得经纬度函数
     * */
    public void getLongitudeAndLatitude() {


        setGPS(true);

        LocationManager loctionManager;
        String contextService=Context.LOCATION_SERVICE;

        loctionManager=(LocationManager) getSystemService(contextService);
        String provider=LocationManager.GPS_PROVIDER;
        Location location = loctionManager.getLastKnownLocation(provider);


        Button send=(Button)findViewById(R.id.send);
        //getLongitudeAndLatitude();







        //BDLocation location=new BDLocation();


        if(location != null) {
            myLongitude = location.getLongitude();
            myLatitude = location.getLatitude();


            System.out.println("\n运行到这！！！！！！！！！！");
            System.out.println("pine"+myLatitude+"apple"+myLongitude);


            LatLng myPoint = new LatLng(myLatitude, myLongitude);
            //LatLng myPoint = new LatLng(39.963175, 116.400244);


            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_st);//icon_st



            OverlayOptions options = new MarkerOptions()
                    .position(myPoint)
                    .icon(bitmap)
                    .zIndex(9)
                    .draggable(true);

            marker.add((Marker) mBaiduMap.addOverlay(options));


            LatLng llText = new LatLng(myLatitude, myLongitude);


            OverlayOptions textOption = new TextOptions()
                    .bgColor(0xAAFFFF00)
                    .fontSize(24)
                    .fontColor(0xFFFF00FF)
                            //.text("Position")
                    .text("Mylongitude:" + myLongitude + "\n" + "Mylatitude:" + myLatitude)
                    .rotate(-0)
                    .position(llText);

            mBaiduMap.addOverlay(textOption);


            send.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("kya");


            /*
            set values
             */

                   // myLatitude = 39.963175;
                   // myLongitude = 116.400244;
                    String MYLatitude = Double.toString(myLatitude);
                    String MYLongitude = Double.toString(myLongitude);//将double的坐标值转化为字符串

                    Log.i("Edit", "MyLatitude" + myLatitude);//Show the name and group number in the log
                    Log.i("Edit", "MyLongitude" + myLongitude);

                    Data setLat = ((Data)getApplicationContext());
                    setLat.setLatitude(MYLatitude);
                    Data setLon=((Data)getApplicationContext());
                    setLon.setLongitude(MYLongitude);

            /*
            将MapActivity中获取的坐标值传给BluetoothChat
             */


                    Intent blueIntent = new Intent(MapActivity.this, BTClient.class);
                    Bundle blueBundle = new Bundle();
                    blueBundle.putCharSequence("MyLatitude", MYLatitude);
                    blueBundle.putCharSequence("MyLongitude", MYLongitude);
                    blueIntent.putExtras(blueBundle);
                    startActivity(blueIntent);

                }
            });


        }

        else
        {


            //myLatitude = 39.963175;
            //myLongitude = 116.400244;
            //myLatitude = 39.973185;
            //myLongitude = 116.600264;
            myLatitude = 39.903185;
            myLongitude = 116.500264;
            System.out.println("\n运行到这fff！！！！！！！！！！");
            System.out.println("pine"+myLatitude+"apple"+myLongitude);


            LatLng myPoint = new LatLng(myLatitude, myLongitude);
            //LatLng myPoint = new LatLng(39.963175, 116.400244);


            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_st);//icon_st



            OverlayOptions options = new MarkerOptions()
                    .position(myPoint)
                    .icon(bitmap)
                    .zIndex(9)
                    .draggable(true);

            marker.add((Marker) mBaiduMap.addOverlay(options));


            LatLng llText = new LatLng(myLatitude, myLongitude);


            OverlayOptions textOption = new TextOptions()
                    .bgColor(0xAAFFFF00)
                    .fontSize(24)
                    .fontColor(0xFFFF00FF)
                            //.text("Position")
                    .text("Mylongitude:" + myLongitude + "\n" + "Mylatitude:" + myLatitude)
                    .rotate(-0)
                    .position(llText);

            mBaiduMap.addOverlay(textOption);


            send.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("kya");


            /*
            set values
             */

                    // myLatitude = 39.963175;
                    // myLongitude = 116.400244;
                    String MYLatitude = Double.toString(myLatitude);
                    String MYLongitude = Double.toString(myLongitude);//将double的坐标值转化为字符串

                    Log.i("Edit", "MyLatitude" + myLatitude);//Show the name and group number in the log
                    Log.i("Edit", "MyLongitude" + myLongitude);

                    Data setLat = ((Data)getApplicationContext());
                    setLat.setLatitude(MYLatitude);
                    Data setLon=((Data)getApplicationContext());
                    setLon.setLongitude(MYLongitude);

            /*
            将MapActivity中获取的坐标值传给BluetoothChat
             */


                    Intent blueIntent = new Intent(MapActivity.this, BTClient.class);
                    Bundle blueBundle = new Bundle();
                    blueBundle.putCharSequence("MyLatitude", MYLatitude);
                    blueBundle.putCharSequence("MyLongitude", MYLongitude);
                    blueIntent.putExtras(blueBundle);
                    startActivity(blueIntent);


                }
            });

        }


    }

    /**
     * 设置GPS开启或关闭，入口参数为true时开启GPS，为false时关闭GPS
     * */

    public void setGPS(boolean on_off) {
        boolean gpsEnabled = android.provider.Settings
                .Secure.isLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER);
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");

        //LocationClientOption option = new LocationClientOption();
        //option.setOpenGps(true);

        System.out.println("\n \n I'm running");

        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }



}











