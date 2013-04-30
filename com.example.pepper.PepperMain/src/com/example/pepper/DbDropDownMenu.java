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
public class DbDropDownMenu extends ListActivity{
    private static final boolean VERBOSE = true;
    private static final String TAG = null;
	ArrayList<Long> RowIDList = new ArrayList<Long>();
	ArrayList<String> labelList = new ArrayList<String>();
	private static final int SELF_FILTER_BUFFER_SIZE = 2;
	int SizeOption = R.string.menuListSizeOption;
	
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		PepperDB content = new PepperDB(DbDropDownMenu.this);
	    content.open();
	    RowIDList = content.getRowIDArray();
	   
	    
	    //This big honkin' for loop assembles the database row into readable form
	    //it shows the name of the app, the day, and the time that it was set to launch
	   
	    for (int index = 0; index < RowIDList.size(); index++)
			{
	    	
			long rIdx = RowIDList.get(index);
			
			String ListItem = content.getAppLabel(rIdx) + "\t";
			
			switch(content.getAppDay(rIdx))
			{
			
			//case = All Days
				case 0:
				{
				ListItem = ListItem + " Daily \t";
				break;
				}
			//case = Sunday
				case 1:
				{
				ListItem = ListItem + " Sun \t";
				break;
				}
			//case = Monday
				case 2:
				{
				ListItem = ListItem + " Mon \t";
				break;
				}  
			//case = Tuesday
				case 3:
				{
				ListItem = ListItem + " Tue \t";
				break;
				}
			//case = Wednesday
				case 4:
				{
				ListItem = ListItem + " Wed \t";
				break;
				}
			//case = Thursday
				case 5:
				{
				ListItem = ListItem + " Thu \t";
				break;
				}
			//case = Friday
				case 6:
				{
				ListItem = ListItem + " Fri \t";
				break;
				}
			//case = Saturday
				case 7:
				{
				ListItem = ListItem + " Sat \t";
				break;
				}
			}
			
			int appendingHour = content.getAppHour(rIdx);
			if (appendingHour < 10)
				ListItem = ListItem + "0" + appendingHour + ":";
			else
				ListItem = ListItem + appendingHour + ":";
			int appendingMinute = content.getAppMinute(rIdx);
			if (appendingMinute < 10)
				ListItem = ListItem + "0" + appendingMinute;
			else
				ListItem = ListItem + appendingMinute;
			
			
			labelList.add(ListItem);
			if (VERBOSE) Log.v(TAG, ListItem + "added to labelList at index " + index);
		}
			

	    setListAdapter(new ArrayAdapter<String>(DbDropDownMenu.this, android.R.layout.simple_list_item_1, labelList));
		
	    content.close();
	    //setListAdapter(new ArrayAdapter<Long>(DbDropDownMenu.this, android.R.layout.simple_list_item_1, RowIDList));

	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		//if (VERBOSE) Log.v(TAG, canonicalList.get(0));

		//Object o = this.getListAdapter().getItem(position);
	      //String programName = canonicalList.get(position);
	      //String applabel = o.toString();//Intent returnIntent = new Intent();
	      
	      //trying this stuff out
	      Intent returnIntent = new Intent();


			//if (VERBOSE && applabel !=null) Log.v(TAG, "retrieved app name for " + applabel );
			//returnIntent.putExtra("SelectedProgramName", applabel);
				     
		//this stuff is working		   
	      returnIntent.putExtra("SelectedProgram",RowIDList.get(position));

	      setResult(RESULT_OK,returnIntent);        
	      finish();
		
		
			//Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(canonicalList[position]);
			//startActivity(LaunchIntent);
			
			//change this to a callback that passes to the TextView element in the RecentTab fragment the name of the program.
	}
	
}
