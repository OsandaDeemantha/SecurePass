package com.example.securepass;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView mParaLabel;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.BIND_ACCESSIBILITY_SERVICE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.BIND_ACCESSIBILITY_SERVICE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        Intent intent = new Intent(getApplicationContext(),MyAccessibilityService.class);
        stopService(intent);
        startService(intent);

        mParaLabel = (TextView) findViewById(R.id.paraLabel);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){   //Check 1 ; Android version => Marshmallow
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){   //Check 2 ; Fingerprint Scanner
                mParaLabel.setText("Fingerprint Scanner not detected in this device");

            }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){   //Check 3 ; Permission to use Fingerprint scanner
                mParaLabel.setText("Permission not granted to use fingerprint scanner");

            }else if(!keyguardManager.isKeyguardSecure()){    //Check 4 ; Lock screen is secured with at least 1 type of lock
                mParaLabel.setText("Add lock to your phone to use fingerprint authentication");

            }else if(!fingerprintManager.hasEnrolledFingerprints()){   //Check 5 ; At least 1 fingerprint is registered
                mParaLabel.setText("Fingerprint not set");

            }else {
                mParaLabel.setText("Place your Finger on scanner to access SecurePass");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);

            }
        }

    }

}