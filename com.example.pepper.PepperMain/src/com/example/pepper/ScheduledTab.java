package com.example.pepper;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
@TargetApi(7)
public class ScheduledTab extends Fragment {
   
	
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
}