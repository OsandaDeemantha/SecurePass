package com.example.securepass;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MyAccessibilityService extends AccessibilityService {

    private DatabaseHelper db;
    private List<String> urls;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(AccessibilityEvent.eventTypeToString(event.getEventType()).contains("WINDOW")){
            AccessibilityNodeInfo nodeInfo = event.getSource();
            dfs(nodeInfo);
        }
    }

    public void dfs(final AccessibilityNodeInfo info){
        if(info == null)
            return;

        if(info.getText() != null && info.getText().toString().contains("http") && info.getText().length() > 0 && info.getClassName().toString().contains("EditText")) {
            db = new DatabaseHelper(this);
            urls = db.getAllURLs();

            if (urls.contains(info.getText().toString())) {
                final Intent dialogIntent = new Intent(this, SiteActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                new CountDownTimer(7000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        startActivity(dialogIntent);
                    }
                }.start();
            }

        }
        for(int i=0;i<info.getChildCount();i++){
            AccessibilityNodeInfo child = info.getChild(i);
            dfs(child);
            if(child != null){
                child.recycle();
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}

