package com.eformation.eformation;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Benjamin on 22/05/2017.
 */

public class EFormationIntentService extends IntentService {

    //Constructeur service
    public EFormationIntentService()
    {
        super("EformationIntentService");
    }

    //Ce service, quand il est appellé , fait patienter le thread pendant 1 seconde.
    //Présent à titre d'essai
    @Override
    protected void onHandleIntent(Intent intent)
    {
        int waitDuration = intent.getIntExtra("waitDuration",1000);
        try {
            Thread.sleep(waitDuration);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        Intent resultIntent = new Intent("Eformation.SerivceEnded");
        resultIntent.putExtra("replyMessage","Le service a envoyé un broadcast");

        sendBroadcast(resultIntent);
    }
}
