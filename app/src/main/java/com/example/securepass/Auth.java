package com.example.securepass;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Process;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.M)
public class Auth extends FingerprintManager.AuthenticationCallback{
    private Context context;

    public Auth(Context context){
        this.context = context;
    }

    public void auth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("Authentication Error: "+errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication Failed!",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: "+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Authentication Succeeded!",true);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(250);
                    ((Activity)context).finish();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    protected void onDestroy(){
        Process.killProcess(Process.myPid());
    }

    private void update(String s, boolean b) {
        TextView txtMessage = ((Activity)context).findViewById(R.id.fingerprintMessage);
        ImageView image = ((Activity)context).findViewById(R.id.fingerprintImage);
        txtMessage.setText(s);
        if(b){
            txtMessage.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            image.setImageResource(R.mipmap.ic_done);
        }else {
            txtMessage.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        }
    }
}