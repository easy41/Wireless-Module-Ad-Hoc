package com.example.apple.wireless_module_ad_hoc;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Easy_MAI on 2017/4/17.
 * This class is used to parse the receiving information.
 */

public class ReceiveMessage {

    private Context context;
    private String type;
    /*private String broadcastCount;
    private String name;
    private String fromID;
    private String toID;
    private String route;
    private String message;*/
    private Data getData;
    private JSONObject jsonObject;



    public ReceiveMessage(Context context){

        this.context=context;//called by getApplicationContext();
        getData=((Data)context);
        jsonObject=new JSONObject();

    }

    public void receiveMessage(String receiveInfo){

        String[] infoArray=receiveInfo.split("\\|");
        if(infoArray.length==5||infoArray.length==7){
            type=infoArray[0];
            try {
                jsonObject.put("type",infoArray[0]);
                jsonObject.put("broadcastCount",infoArray[1]);
                jsonObject.put("name",infoArray[2]);
                jsonObject.put("fromID",infoArray[3]);
                //'1' is a char;"1" is a String.
                if(type.equals("1")||type.equals("2")){
                    jsonObject.put("toID",infoArray[4]);
                    jsonObject.put("route",infoArray[5]);
                    jsonObject.put("message",infoArray[6]);
                }
                else {
                    jsonObject.put("message",infoArray[4]);
                }
                getData.setrJson(jsonObject);
            }catch (JSONException e){
                e.getStackTrace();
            }
        }

        switch(type){
            case "1":


                break;
            case "2":

                break;
            case "3":

                break;
            case "4":

                break;
            default:
                break;

        }



    }


}
