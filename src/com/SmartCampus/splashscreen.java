package com.SmartCampus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class splashscreen extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 5000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        Log.d("Loginactivity.mailList.size()",""+Loginactivity.mailList.size());
        if(Loginactivity.mailList.size()>0){
        	Intent in= new Intent(getApplicationContext(),SmartPoster.class);
			startActivity(in);
			finish();
        }
        else{
        final splashscreen screen = this; 
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    Intent intent = new Intent();
                    intent.setClass(screen,Loginactivity.class);
                    startActivity(intent);
                                  
                }
            }
        };
        splashTread.start();
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}