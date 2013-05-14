package com.example.pepper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
@TargetApi(7)
public class ScheduledTab extends Fragment {
	private static final boolean VERBOSE = true;
	private static final String TAG = null;
	Button ProgName;
	Button DayButton;
	Button TimeButton;
	Button DisableButton;
	Button UpdateButton;
	String ProgAddy = new String();
	long DBrefID;
	int day;
	int hour;
	int minute;
	final int PROG_PICKER_REQUEST_CODE = 1;
	final int DAY_PICKER_REQUEST_CODE = 2;
	final int TIME_PICKER_REQUEST_CODE = 3;
	
	
	public static Fragment newInstance() {
        ScheduledTab thisInstance = new ScheduledTab();
        return thisInstance;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.scheduled_tab, container, false);
    }
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ProgName = (Button) getActivity().findViewById(R.id.s_progbutton);
		//on click listener code is breaking the game
		ProgName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (VERBOSE) Log.v(TAG, "++Found click, launching list event++");
				Intent intent = new Intent("com.example.pepper.DBMENU");
				intent.putExtra("SelectedMODE",PROG_PICKER_REQUEST_CODE);
				startActivityForResult(intent,PROG_PICKER_REQUEST_CODE);
			}
		});
		DayButton = (Button) getActivity().findViewById(R.id.s_daybutton);
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
		TimeButton = (Button) getActivity().findViewById(R.id.s_timebutton);
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
		UpdateButton = (Button) getActivity().findViewById(R.id.s_updatebutton);
		//ok, so we set this button up to launch a list of days on click, whatever.
		UpdateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean worked = true;
				if (VERBOSE) Log.v(TAG, "++Found click, launching database row update++");
				try{
				PepperDB entry = new PepperDB(getActivity().getBaseContext());
				entry.open();
				entry.updateEntry(DBrefID, entry.getAppName(DBrefID), entry.getAppLabel(DBrefID), day, hour, minute);
				entry.close();
				}
				catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (VERBOSE) Log.v(TAG, "++Database update failed++");
				worked = false;
				}
				if (VERBOSE && worked == true)
				{
					Intent service = new Intent(getActivity().getBaseContext(), LauncherService.class);
					getActivity().stopService(service);
					getActivity().startService(service);
					Toast.makeText(getActivity().getBaseContext(), "Automation Entry Updated", Toast.LENGTH_LONG).show();
					Log.v(TAG, "++Update successful++");
				}
				
			}
		});
		DisableButton = (Button) getActivity().findViewById(R.id.s_disablebutton);
		//ok, so we set this button up to launch a list of days on click, whatever.
		DisableButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean worked = true;
				if (VERBOSE) Log.v(TAG, "++Found click, launching database row deletion++");
				try{
				PepperDB entry = new PepperDB(getActivity().getBaseContext());
				entry.open();
				entry.deleteEntry(DBrefID);
				entry.close();
				}
				catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (VERBOSE) Log.v(TAG, "++Database delete failed++");
				worked = false;
				}
				if (VERBOSE && worked == true) 
				{
					Intent service = new Intent(getActivity().getBaseContext(), LauncherService.class);
					getActivity().stopService(service);
					getActivity().startService(service);
					Toast.makeText(getActivity().getBaseContext(), "Automation Entry Removed", Toast.LENGTH_LONG).show();
					Log.v(TAG, "++Deletion successful++");
				}
				
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
            	DBrefID = data.getLongExtra("SelectedProgram", DBrefID);
                PepperDB selectedEntry = new PepperDB(getActivity().getBaseContext());
                selectedEntry.open();
            	ProgName.setText(selectedEntry.getAppLabel(DBrefID));
            	day = selectedEntry.getAppDay(DBrefID);
                DayButton.setText(selectedEntry.getDayText(day));
                hour = selectedEntry.getAppHour(DBrefID);
                minute = selectedEntry.getAppMinute(DBrefID);
                TimeButton.setText(selectedEntry.formatTime(hour,minute ));
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