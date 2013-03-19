package com.example.pepper;

import java.util.Calendar;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/* 
The Day picker runs off of the java util Calendar and the vales returned are as follows
0 is default (in the context of our program meaning all days)
1 = Sunday
2 = Monday
3 = Tuesday
4 = Wednesday
5 = Thursday
6 = Friday
7 = Saturday

Launching this activity creates a list view with 8 options.  
Clicking on an item will return both the name of the day (for display)
as well as the position in the list that it was in (to cross reference the Calendar.DAY_OF_WEEK)
*/

@TargetApi(Build.VERSION_CODES.FROYO)
public class DayPicker extends ListActivity{
    private static final boolean VERBOSE = true;
    private static final String TAG = null;
	String[] canonicalList = {"All Days", "Sunday", "Monday", "Tuesday", "Wednesady", "Thursday", "Friday", "Saturday" };
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setListAdapter(new ArrayAdapter<String>(DayPicker.this, android.R.layout.simple_list_item_1, canonicalList));
	
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	      Intent returnIntent = new Intent();
	      returnIntent.putExtra("SelectedDay",canonicalList[position]);
	      int dayOweek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	      if (VERBOSE) Log.v(TAG, "Value of Calender.DAY_OF_WEEK = " + dayOweek );
	      if (VERBOSE) Log.v(TAG, "Value of Calender.DAY_OF_WEEK = " + Calendar.getInstance().getDisplayName(Calendar.getInstance().get(dayOweek), Calendar.LONG, Locale.US) );
	      if (VERBOSE) Log.v(TAG, "Value of Canonical list at position clicked = " + position );
			
		setResult(RESULT_OK,returnIntent );        
	      finish();
	      }
	
}
