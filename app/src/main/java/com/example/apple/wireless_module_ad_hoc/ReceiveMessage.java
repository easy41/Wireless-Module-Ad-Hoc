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
        Log.d(TAG,"Entering receiveMessageHandler.");

        SendMessage sendMessage=new SendMessage(context);
        String myID=getData.getFromID();

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
                if(type.equals("1")||type.equals("2")||type.equals("5")){
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
            //When receive an invalid message format.
            Log.d(TAG,"The new message is invalid. It will be ignored.");
            return;
        }

        switch(type){
            case ROUTE_DISCOVERY:
                //  myID=destination
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
                //toID=myID
                if(toID.equals(myID)){
                    getData.setRoute(route);
                    Log.d(TAG,"Store the route to "+rFromID);
                    Log.d(TAG,"The new route is: "+getData.getRoute());
                }
                else{
                    String[] s=route.split("/");
                    int i;
                    int flag=0;
                    //Search whether myID is in the route.
                    for(i=0;i<s.length;i++){
                        if(s[i].equals(myID)){
                            //Avoid transmission loop
                            int calculate=s.length-1;
                            calculate=calculate-i;
                            calculate=calculate-1;
                            if(bCount==calculate)
                            {
                                bCount++;
                                broadcastCount=Integer.toString(bCount);
                                sendMessage.sendFormatMessage(Acknowledgement,broadcastCount,toID,route,message);
                                flag=1;
                            }
                            else {
                                flag=2;
                            }
                            break;
                        }
                    }
                    switch (flag){
                        case 0:
                            Log.d(TAG,"MyID is not in the route. Ignore the message.");
                            break;
                        case 1:
                            Log.d(TAG,"Relay the acknowledgement.");
                            break;
                        case 2:
                            Log.d(TAG,"This ack has been relayed before. Ignore the message.");
                            break;
                        default:
                            break;
                    }

                }

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
