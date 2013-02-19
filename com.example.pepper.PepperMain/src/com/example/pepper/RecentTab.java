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
import android.widget.TextView;
@TargetApi(7)
public class RecentTab extends Fragment {
   
	TextView ProgName;
	
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
				Intent intent = new Intent("com.example.pepper.MENU");
				
				startActivity(intent);
			}
		});
	}




	
}