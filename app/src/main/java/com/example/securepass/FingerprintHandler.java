package com.example.securepass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context) {

        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();

        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Auth ERROR. "+ errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth FAILED.", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: "+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Authentication succeeded.", true);
    }

    private void update(String s, boolean b) {

        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
        ImageView fingerprintImage = (ImageView) ((Activity)context).findViewById(R.id.fingerprintImage);
        Button accessButton = (Button) ((Activity)context).findViewById(R.id.accessbutton);

        paraLabel.setText(s);

        if(b==false) {
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else{
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            fingerprintImage.setImageResource(R.mipmap.ic_done);

            //accessButton.setVisibility(View.VISIBLE);

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(1000);
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

            //Intent intent = new Intent(context,HomeActivity.class);
            //context.startActivity(intent);
        }
    }
}
