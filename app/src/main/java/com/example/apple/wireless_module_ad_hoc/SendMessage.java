package com.example.apple.wireless_module_ad_hoc;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Easy_MAI on 2017/4/17.
 * This class is used to build the sending information.
 */

public class SendMessage {

    private final static String ROUTE_DISCOVERY ="1";
    private final static String DIALOGUE ="2";
    private final static String RESCUE_INFORMATION ="3";
    private final static String ROAD_CONDITION ="4";
    private final static String Acknowledgement="5";

    private BluetoothSocket _socket = null;
    Context context;
    String TAG="SendMessage";

    Data getData;
    String fileName="json.txt";


    public SendMessage(Context context){
        //call by: getApplicationContext();
        this.context=context;
    }


    public void sendFormatMessage(String type, String broadcastCount, String name, String fromID, String toID,String route, String message){
        int i;
        int n=0;
        String sendingMessage;
        getData = ((Data)context);
        JSONObject jsonObject=new JSONObject();


        /*getData = ((Data)context);
        String name=getData.getName();
        String fromID=getData.getFromID();
        Log.d(TAG,"Get data: "+name+"/"+fromID);*/



        /*
          *     1.Route Discovery
          *     2.Dialogue
          *     3.Rescue team broadcasting information
          *     4.Road condition broadcasting information
         */

        CacheUtils cacheUtils=new CacheUtils();
        switch (type){
            case ROUTE_DISCOVERY:
                //Log.d(TAG,"Start route discovery.");
                sendingMessage=type+"|"+broadcastCount+"|"+name+"|"+fromID+"|"+toID+"|"+route+"|"+message+"/>";
                try{
                    jsonObject.put("message",message);
                    jsonObject.put("toID",toID);
                    jsonObject.put("fromID",fromID);
                    jsonObject.put("name",name);
                   // jsonObject.put("broadcastCount",broadcastCount);
                    jsonObject.put("type",type);

                }catch (JSONException e){
                    Log.d(TAG,"Failed to set JSON.");
                }
                cacheUtils.writeJson(context,jsonObject.toString(),"dialogue.txt",true);
                break;

            case Acknowledgement:
                //Log.d(TAG,"Start to send Ack.");
                sendingMessage=type+"|"+broadcastCount+"|"+name+"|"+fromID+"|"+toID+"|"+route+"|"+"Ack"+"/>";
                break;

            case DIALOGUE:
                //Log.d(TAG,"Start send dialogue.");
                sendingMessage=type+"|"+broadcastCount+"|"+name+"|"+fromID+"|"+toID+"|"+route+"|"+message+"/>";
                try{
                    jsonObject.put("message",message);
                    jsonObject.put("toID",toID);
                    jsonObject.put("fromID",fromID);
                    jsonObject.put("name",name);
                    // jsonObject.put("broadcastCount",broadcastCount);
                    jsonObject.put("type",type);

                }catch (JSONException e){
                    Log.d(TAG,"Failed to set JSON.");
                }
                cacheUtils.writeJson(context,jsonObject.toString(),"dialogue.txt",true);
                sendingMessage=type+"|"+broadcastCount+"|"+name+"|"+fromID+"|"+toID+"|"+route+"|"+message+"/>";
                break;

            case RESCUE_INFORMATION:
                sendingMessage=type+'|'+broadcastCount+'|'+name+'|'+fromID+'|'+route+"|"+message+"/>";
                //Broadcasting the message.
                break;

            case ROAD_CONDITION:
                sendingMessage=type+'|'+broadcastCount+'|'+name+'|'+fromID+'|'+route+'|'+message+"/>";
                //Broadcasting the message.
                break;

            default:
                Toast.makeText(context,"The data format is invalid.",Toast.LENGTH_SHORT).show();
                return;

        }

        _socket=getData.getSocket();

        try{
            OutputStream os = _socket.getOutputStream();

            byte[] bos=sendingMessage.getBytes();
           // byte[] bos="ttttt".getBytes();

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
            int bCount=Integer.parseInt(broadcastCount);
            //It is unnecessary to get ack when relaying the massages.
            if(bCount==0&&(!type.equals(Acknowledgement))){
                getAckThread.start();
            }

            os.write(bos_new);

           // os.close();
            Log.d(TAG,sendingMessage);
        }catch(IOException e){
            e.getStackTrace();
        }
    }

    private Thread getAckThread = new Thread(){
        public void run(){
            int ackFlag=0;
            long t2;
            getData.setAckFlag(ackFlag);
            long t1 = System.currentTimeMillis();

            while(ackFlag==0){
                t2 = System.currentTimeMillis();
                //Log.d(TAG,"Ack Thread running...");
                if(t2-t1 > 3*1000){//1ms

                    //TODO: simulation to calculate the waiting time.

                    //waiting for response... /5s
                    //Toast.makeText(context,"Failed to reach to the destination.",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Failed to reach to the destination.");
                    break;
                }else{
                    ackFlag=getData.getAckFlag();
                }

            }
            if(ackFlag==1){
                Log.d(TAG,"sent");
            }


        }

    };


}
