package com.example.pepper;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class Splash extends Activity  {
    private static final boolean VERBOSE = true;
    private static final String TAG = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		MediaPlayer introbang = MediaPlayer.create(Splash.this, R.raw.firebreath);
		introbang.start();
		Thread introTimer = new Thread()
		{
			public void run()
			{
				try{
					sleep(5000);
				}catch(InterruptedException e)
					{
					e.printStackTrace();
					}finally
						{
						Intent intent = new Intent("com.example.pepper.MENU");
						if (VERBOSE) Log.v(TAG, "+++ Starting launcher +++");
						startActivity(intent);
				        
						}
			}
		};
		introTimer.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
