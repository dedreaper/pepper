package com.example.pepper;


import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
@TargetApi(7)
public class OptionsTab extends Fragment {
	private static final boolean VERBOSE = true;
	private static final String TAG = null;
	TextView SizeMod;
	TextView DBDump ;
	Button UpdateButton;
	EditText NumberEntry;
	
	
	public static Fragment newInstance() {
        OptionsTab thisInstance = new OptionsTab();
        return thisInstance;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.options_tab, container, false);
    }
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		PepperDB content = new PepperDB(getActivity().getBaseContext());
		content.open();
		SizeMod = (TextView) getActivity().findViewById(R.id.textView2);
		//NumberEntry = (EditText) getActivity().findViewById(R.id.o_tab_numberEntry);
		//UpdateButton =(Button) getActivity().findViewById(R.id.o_tab_updateButton);
		
		getActivity();
		getActivity();
		ActivityManager m = (ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE);
		//int index = 0;
		int maxListSize = m.getRecentTasks(R.string.menuListSizeOption,0).size();
		SizeMod.setText("" + (maxListSize - 2));
		
	
		
		
		/*DBDump = (TextView)getActivity().findViewById(R.id.o_dbdump);
		long i = content.getNextEntryToLaunch();
		DBDump.setText("id of the next thing to run = " +"  "+
						i+"  "+ 
						content.getAppName(i)+"  "+
						content.getAppLabel(i)+"  "+
						content.getDayText(content.getAppDay(i))+"  "+
						content.formatTime(content.getAppHour(i), content.getAppMinute(i)) );
		//Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(content.getAppName(i));
		//startActivity(LaunchIntent);*/
		content.close();
	}
}