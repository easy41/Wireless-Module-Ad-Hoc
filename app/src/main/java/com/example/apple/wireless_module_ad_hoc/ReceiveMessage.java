package com.example.apple.wireless_module_ad_hoc;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Easy_MAI on 2017/4/17.
 * This class is used to parse the receiving information.
 */

public class ReceiveMessage {

    private Context context;
    private String type;
    private String broadcastCount;
    private String name;
    private String rFromID;
    private String toID;
    private String route;
    private String message;
    private Data getData;
    private JSONObject jsonObject;
    private int bCount;
    private final static String ROUTE_DISCOVERY ="1";
    private final static String DIALOGUE ="2";
    private final static String RESCUE_INFORMATION ="3";
    private final static String ROAD_CONDITION ="4";
    private final static String Acknowledgement="5";

    String TAG="ReceiveMessage";



    public ReceiveMessage(Context context){

        this.context=context;//called by getApplicationContext();
        getData=((Data)context);
        jsonObject=new JSONObject();

    }

    public void receiveMessageHandler(String receiveInfo){

        String[] infoArray=receiveInfo.split("\\|");
        //Check the validity of receiving message.
        if(infoArray.length==5||infoArray.length==7){
            type=infoArray[0];
            try {
                broadcastCount=infoArray[1];
                bCount=Integer.parseInt(broadcastCount);
                name=infoArray[2];
                rFromID=infoArray[3];
                jsonObject.put("type",infoArray[0]);
                jsonObject.put("broadcastCount",infoArray[1]);
                jsonObject.put("name",infoArray[2]);
                jsonObject.put("fromID",infoArray[3]);

                //'1' is a char;"1" is a String.
                if(type.equals("1")||type.equals("2")){
                    toID=infoArray[4];
                    route=infoArray[5];
                    message=infoArray[6];
                    jsonObject.put("toID",infoArray[4]);
                    jsonObject.put("route",infoArray[5]);
                    jsonObject.put("message",infoArray[6]);
                }
                else {
                    message=infoArray[4];
                    jsonObject.put("message",infoArray[4]);
                }
                getData.setrJson(jsonObject);

                //TODO: Store the message into the log.xml.

            }catch (JSONException e){
                e.getStackTrace();
            }
        }
        else {
            Log.d(TAG,"The new message is invalid. It will be ignored.");
            return;
        }

        switch(type){
            case ROUTE_DISCOVERY:

                SendMessage sendMessage=new SendMessage(context);
                //  myID=destination
                String myID=getData.getFromID();
                if(toID.equals(myID)){
                    Log.d(TAG,"I'm the destination.");

                    //TODO: display the message to the user here.

                    //Send acknowledgement and complete route back to the source.
                    //hypothesis: There is no delay for the Ack.
                    route=route+myID;
                    sendMessage.sendFormatMessage(Acknowledgement,"0",rFromID,route,"Ack");
                    Log.d(TAG,"Send back ack and show message.");
                    Log.d(TAG,"The received message is: "+message);

                    //TODO: Handle th delay of the Ack.

                }
                //Relay the route discovery  information.
                else if(bCount<8){
                    route=route+myID+"/";
                    bCount++;
                    broadcastCount=Integer.toString(bCount);
                    sendMessage.sendFormatMessage(ROUTE_DISCOVERY,broadcastCount,toID,route,message);
                    Log.d(TAG,"Relay route discovery.");
                }
                else{
                    Log.d(TAG,"This is the end of this broadcasting route discovery.");
                }
                break;

            case Acknowledgement:


                break;

            case DIALOGUE:

                break;

            case RESCUE_INFORMATION:

                break;

            case ROAD_CONDITION:

                break;

            default:
                break;

        }



    }


}
