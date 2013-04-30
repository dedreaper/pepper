package com.example.pepper;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimeSelector extends Activity {

    Button selectButton;
    Button cancelButton;        
    TimePicker tp;
    int hour;
    int minute;
	private static final boolean VERBOSE = true;
	private static final String TAG = null;
    
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);
        tp = (TimePicker) findViewById(R.id.timePicker);
        selectButton = (Button)findViewById(R.id.time_picker_select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// grab the hour and minute info from the time picker
				hour = tp.getCurrentHour();
				if (VERBOSE) Log.v(TAG, "Hour retrieved from TimePicker = " + hour);
				minute = tp.getCurrentMinute();
				if (VERBOSE) Log.v(TAG, "Minute retrieved from TimePicker = " + minute);
				// send that shit back to the calling activity
			    Intent returnIntent = new Intent();  
				returnIntent.putExtra("SelectedHour",hour);
				returnIntent.putExtra("SelectedMinute", minute);
			      setResult(RESULT_OK,returnIntent);        
			      finish();
					
			}
		});
        cancelButton = (Button)findViewById(R.id.time_picker_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// GTFO!
				if (VERBOSE) Log.v(TAG, "Cancel button pressed, moving to previous activity");
				// send word to the calling activity that mission was aborted.
				Intent returnIntent = new Intent();
			    setResult(RESULT_CANCELED,returnIntent);        
			    finish();
					
			}
		});
        
	}

    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		

        
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
	}

}
