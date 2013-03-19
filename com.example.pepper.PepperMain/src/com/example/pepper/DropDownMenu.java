package com.example.pepper;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


@TargetApi(Build.VERSION_CODES.FROYO)
public class DropDownMenu extends ListActivity{
    private static final boolean VERBOSE = true;
    private static final String TAG = null;
	ArrayList<String> canonicalList = new ArrayList<String>();
	ArrayList<String> labelList = new ArrayList<String>();
	private static final int SELF_FILTER_BUFFER_SIZE = 2;
	int SizeOption = 5;
	
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManager m = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		int index = 0;
		int maxListSize = m.getRecentTasks((SizeOption+SELF_FILTER_BUFFER_SIZE),0).size();
			for (int i=0; i< maxListSize; i++)
	    {
				//teh usesr hath spoken! get him his tasks!
				//below line grabs tasks up to 
			RecentTaskInfo task = m.getRecentTasks((SizeOption+SELF_FILTER_BUFFER_SIZE),0).get(i);
			//mysterious code! ... actually it'd be kind of dumb to list this program, 
			//or the launcher of this program in the list, so we're taking that out here
			if(task.baseIntent.getComponent().getPackageName().contains("launcher")
				|| task.baseIntent.getComponent().getPackageName().contains("pepper"))
			{
			//do nothing and...
			continue;
			}
			//lets try something here... lets see if we can get logcat to display the actual names of the processes 
			//(this would make my like a whole hell of a lot easier)
			
			
			
			
		String retrievedPackageName = task.baseIntent.getComponent().getPackageName();

	    canonicalList.add(retrievedPackageName);
	    index++;
			if (VERBOSE) Log.v(TAG, retrievedPackageName + "added to canonicalList at index " + canonicalList.indexOf(retrievedPackageName));
	    }	    

	    setListAdapter(new ArrayAdapter<String>(DropDownMenu.this, android.R.layout.simple_list_item_1, canonicalList));
	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if (VERBOSE) Log.v(TAG, canonicalList.get(0));

		Object o = this.getListAdapter().getItem(position);
	      String programName = o.toString();
	      //Intent returnIntent = new Intent();
	      
	      //trying this stuff out
	      Intent returnIntent = getPackageManager().getLaunchIntentForPackage(programName);
			String appname = null;
			try {
				appname  = (String) getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(programName, 0));
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (VERBOSE && appname !=null) Log.v(TAG, "retrieved app name for " + appname );
			returnIntent.putExtra("SelectedProgramName", appname);
				     
		//this stuff is working		   
	      returnIntent.putExtra("SelectedProgram",programName);

	      setResult(RESULT_OK,returnIntent);        
	      finish();
		
		
			//Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(canonicalList[position]);
			//startActivity(LaunchIntent);
			
			//change this to a callback that passes to the TextView element in the RecentTab fragment the name of the program.
	}
	
}
