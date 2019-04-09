package com.example.securepass;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView mHeadingLabel;
    private ImageView mFingerprintImage;
    private TextView mParaLabel;
    private Button mButton;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadingLabel = (TextView) findViewById(R.id.headingLabel);
        mFingerprintImage = (ImageView) findViewById(R.id.fingerprintImage);
        mParaLabel = (TextView) findViewById(R.id.paraLabel);
        mButton = (Button) findViewById(R.id.accessbutton);

        mButton.setVisibility(View.INVISIBLE);

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

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".HomeActivity");
                        startActivity(intent);

                    }
                });

            }
        }

    }


}
