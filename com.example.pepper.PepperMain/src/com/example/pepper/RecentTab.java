package com.example.pepper;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
@TargetApi(7)
public class RecentTab extends Fragment {
	private static final boolean VERBOSE = true;
	private static final String TAG = null;
	TextView ProgName;
	Button DayButton;
	Button TimeButton;
	Button AutomateButton;
	//DropDownMenu progmenu;
	String ProgAddy = new String();
	int day;
	int hour;
	int minute;
	final int PROG_PICKER_REQUEST_CODE = 1;
	final int DAY_PICKER_REQUEST_CODE = 2;
	final int TIME_PICKER_REQUEST_CODE = 3;
	
	int Recent_tab_mode = 1;
	
	public static Fragment newInstance() {
        RecentTab thisInstance = new RecentTab();
        return thisInstance;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recent_tab, container, false);
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ProgName = (TextView) getActivity().findViewById(R.id.textView2);
		//on click listener code is breaking the game
		ProgName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (VERBOSE) Log.v(TAG, "++Found click, launching list event++");
				Intent intent = new Intent("com.example.pepper.MENU");
				intent.putExtra("SelectedMODE",PROG_PICKER_REQUEST_CODE);
				startActivityForResult(intent,PROG_PICKER_REQUEST_CODE);
			}
		});
		DayButton = (Button) getActivity().findViewById(R.id.RTABdayButton);
		//ok, so we set this button up to launch a list of days on click, whatever.
		DayButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (VERBOSE) Log.v(TAG, "++Found click, launching day list event++");
				Intent intent = new Intent("com.example.pepper.DAY");
				intent.putExtra("SelectedMODE",DAY_PICKER_REQUEST_CODE);
				startActivityForResult(intent,DAY_PICKER_REQUEST_CODE);
			}
		});
		TimeButton = (Button) getActivity().findViewById(R.id.RTABtimeButton);
		//ok, so we set this button up to launch a time picker dialog (would making this an activity be more difficult?)
		TimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (VERBOSE) Log.v(TAG, "++Found click, launching time picker event++");
				Intent intent = new Intent("com.example.pepper.TIME");
				intent.putExtra("SelectedMODE",TIME_PICKER_REQUEST_CODE);
				startActivityForResult(intent,TIME_PICKER_REQUEST_CODE);
			}
		});
		AutomateButton = (Button) getActivity().findViewById(R.id.RTABautomateButton);
		//ok, so we set this button up to launch a list of days on click, whatever.
		AutomateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean worked = true;
				if (VERBOSE) Log.v(TAG, "++Found click, launching database entry++");
				try{
				PepperDB entry = new PepperDB(getActivity().getBaseContext());
				entry.open();
				entry.createEntry(ProgAddy, ProgName.getText().toString(), day, hour, minute);
				entry.close();
				}
				catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (VERBOSE) Log.v(TAG, "++Database entry code failed++");
				worked = false;
				}
				if (VERBOSE && worked == true) Log.v(TAG, "++Database entry successful++");
				
			}
		});
	}
    public void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == PROG_PICKER_REQUEST_CODE) {
        	
        	if (VERBOSE) Log.v(TAG, "Request code retrieved = " + requestCode);
            if (resultCode == Activity.RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user. 
            	String retrievedParameter = data.getStringExtra("SelectedProgramName");
                ProgName.setText(retrievedParameter);
                ProgAddy = data.getStringExtra("SelectedProgram");
            }
        }
        if (requestCode == DAY_PICKER_REQUEST_CODE) {
        	
        	if (VERBOSE) Log.v(TAG, "Request code retrieved = " + requestCode);
            if (resultCode == Activity.RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user. 
            	String retrievedDay = data.getStringExtra("SelectedDay");
                DayButton.setText(retrievedDay);
                day = data.getIntExtra("DayRank", 0);
                if (VERBOSE) Log.v(TAG, "Selected Day Rank = " + day);	
            }
        }
        if (requestCode == TIME_PICKER_REQUEST_CODE) {
            	
        	if (VERBOSE) Log.v(TAG, "Request code retrieved = " + requestCode);
        	if (resultCode == Activity.RESULT_OK) {
        	// A contact was picked.  Here we will just display it
        	// to the user. 
        	hour = data.getIntExtra("SelectedHour", hour);
        	minute = data.getIntExtra("SelectedMinute", minute);
        	TimeButton.setText(hour + ":" + minute);
        	}
        }
    }


	
    
}