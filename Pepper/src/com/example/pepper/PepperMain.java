package com.example.pepper;

import java.util.Iterator;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import java.util.*;
import android.app.*;
import android.widget.*;

@TargetApi(9)
public class PepperMain extends Activity {

	
    private static final String TAG = null;
	private ActivityManager mActivityManager;
	private Activity mContext;

	private String appname;


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pepper_main);
        Uri webpage = Uri.parse("pandora://892219884763199517");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, 0);
        boolean isIntentSafe = activities.size() > 0;
        
        if(isIntentSafe)
        {
        	//UNDERLYING COMMENTED CODE IS FOR LAUNCHING AN APP CHOOSER
        	// Always use string resources for UI text. This says something like "Share this photo with"
        	//String title = getResources().getText(R.string.chooser_title);
        	// Create and start the chooser
        	//Intent chooser = Intent.createChooser(webIntent, title);
        	try
        	{
        	startActivity(webIntent);
        	}
        	catch (Exception e) {
        	startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/<user_name_here>")));
        		   } 
        }
      }
    
	@Override
	public void onStop()
	{
		
		boolean diditwork = true;
		
		try{
			RunningAppProcessInfo rapinfo =new RunningAppProcessInfo();
			rapinfo = getForegroundApp();

		
		appname = rapinfo.processName;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	    PepperDB entry = new PepperDB(PepperMain.this);
		entry.open();
    	entry.createEntry(appname,day, time);
	    entry.close();
		
		}
		catch(Exception e){
			diditwork = false;
		}finally{
			if(diditwork){
				Dialog d = new Dialog(this);
				d.setTitle("database entry stored!");
				TextView TV = new TextView(this);
				TV.setText("success");
				d.setContentView(TV);
				d.show();
			}
			else{
				
				Dialog d = new Dialog(this);
				d.setTitle("database entry not stored!");
				TextView TV = new TextView(this);
				TV.setText("fail");
				d.setContentView(TV);
				d.show();
			}
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pepper_main, menu);
        return true;
    }

   
    private RunningAppProcessInfo getForegroundApp() {
        RunningAppProcessInfo result=null, info=null;

        if(mActivityManager==null)
            mActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List <RunningAppProcessInfo> l = mActivityManager.getRunningAppProcesses();
        Iterator <RunningAppProcessInfo> i = l.iterator();
        while(i.hasNext()){
            info = i.next();
            if(info.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && !isRunningService(info.processName)){
                result=info;
                break;
            }
        }
        return result;
    }

    private ComponentName getActivityForApp(RunningAppProcessInfo target){
        ComponentName result=null;
        ActivityManager.RunningTaskInfo info;

        if(target==null)
            return null;

        if(mActivityManager==null)
            mActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List <ActivityManager.RunningTaskInfo> l = mActivityManager.getRunningTasks(9999);
        Iterator <ActivityManager.RunningTaskInfo> i = l.iterator();

        while(i.hasNext()){
            info=i.next();
            if(info.baseActivity.getPackageName().equals(target.processName)){
                result=info.topActivity;
                break;
            }
        }

        return result;
    }

    private boolean isStillActive(RunningAppProcessInfo process, ComponentName activity)
    {
        // activity can be null in cases, where one app starts another. for example, astro
        // starting rock player when a move file was clicked. we dont have an activity then,
        // but the package exits as soon as back is hit. so we can ignore the activity
        // in this case
        if(process==null)
            return false;

        RunningAppProcessInfo currentFg=getForegroundApp();
        ComponentName currentActivity=getActivityForApp(currentFg);

        if(currentFg!=null && currentFg.processName.equals(process.processName) &&
                (activity==null || currentActivity.compareTo(activity)==0))
            return true;

        Log.i(TAG, "isStillActive returns false - CallerProcess: " + process.processName + " CurrentProcess: "
                + (currentFg==null ? "null" : currentFg.processName) + " CallerActivity:" + (activity==null ? "null" : activity.toString())
                + " CurrentActivity: " + (currentActivity==null ? "null" : currentActivity.toString()));
        return false;
    }

    @TargetApi(9)
	private boolean isRunningService(String processname){
        if(processname==null || processname.isEmpty())
            return false;

        RunningServiceInfo service;

        if(mActivityManager==null)
            mActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List <RunningServiceInfo> l = mActivityManager.getRunningServices(9999);
        Iterator <RunningServiceInfo> i = l.iterator();
        while(i.hasNext()){
            service = i.next();
            if(service.process.equals(processname))
                return true;
        }

        return false;
    }
}

