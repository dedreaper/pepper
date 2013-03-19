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
import android.widget.TextView;
@TargetApi(7)
public class RecentTab extends Fragment {
	private static final boolean VERBOSE = true;
	private static final String TAG = null;
	TextView ProgName;
	Button DayButton;
	DropDownMenu progmenu;
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
	}
    public void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == PROG_PICKER_REQUEST_CODE) {
        	
        	if (VERBOSE) Log.v(TAG, "Request code retrieved = " + requestCode);
            if (resultCode == Activity.RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user. 
            	String retrievedProgramValue = data.getStringExtra("SelectedProgramName");
                ProgName.setText(retrievedProgramValue);
            }
        }
        if (requestCode == DAY_PICKER_REQUEST_CODE) {
        	
        	if (VERBOSE) Log.v(TAG, "Request code retrieved = " + requestCode);
            if (resultCode == Activity.RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user. 
            	String retrievedDay = data.getStringExtra("SelectedDay");
                DayButton.setText(retrievedDay);
            }
        }
    }


	
}