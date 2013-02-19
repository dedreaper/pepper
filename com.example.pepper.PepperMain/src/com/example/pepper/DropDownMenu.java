package com.example.pepper;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ListActivity;
import android.content.Intent;
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
	String[] canonicalList = {"com.facebook.katana", "com.pandora.android", "com.android.settings", "com.aide.ui", "com.cooliris.media", };

	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActivityManager m = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);

			for (int i=0; i< 5; i++)
	    {
		RecentTaskInfo task = m.getRecentTasks(5,0).get(i);
	    canonicalList[i] = task.baseIntent.getComponent().getPackageName();
	    if (VERBOSE) Log.v(TAG, "CanonicalList[" + i + "]=" + canonicalList[i]);
	    }
	    setListAdapter(new ArrayAdapter<String>(DropDownMenu.this, android.R.layout.simple_list_item_1, canonicalList));
	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if (VERBOSE) Log.v(TAG, canonicalList[0]);

			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(canonicalList[position]);
			startActivity(LaunchIntent);
	}
	
}
