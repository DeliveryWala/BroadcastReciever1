package com.example.abhishekshah1.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Abhishek.Shah1 on 7/9/2015.
 */
public class Broadcast extends BroadcastReceiver {
    String address;
    String sent;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Broadcast", "message received");
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String messages = "";
        SmsMessage[] phonenum =null;
        String PhoneNUMBER ="";

        if (bundle != null) {
//—retrieve the SMS message received—
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
//take out content from sms
                String body = sms.getMessageBody().toString();
               address = sms.getOriginatingAddress();
                sms.getUserData();
                messages += "SMS from" + address + ":\n";
                messages += body + "\n";
                Toast.makeText(context, messages, Toast.LENGTH_SHORT).show();
            }

            phonenum = new SmsMessage[pdus.length];
            for (int i = 0; i < phonenum.length; i++) {
                phonenum[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                PhoneNUMBER += phonenum[i].getOriginatingAddress();
            }

        }
        if(messages.contains("AUTHENTICATE"))
        {
            // START YOUR ACTIVITY HERE
            String url = "tel:"+"**21*"+ address+ Uri.encode("#");
            Intent intent1 = (new Intent(Intent.ACTION_CALL, Uri.parse(url)));
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
        else if(messages.contains("DEAUTHENTICATE"))
        {
            String url = "tel:"+"#21"+ address+Uri.encode("#");
            Intent intent1 = (new Intent(Intent.ACTION_CALL, Uri.parse(url)));
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);


        }

    }
}


