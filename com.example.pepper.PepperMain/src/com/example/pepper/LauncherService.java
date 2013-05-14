package com.example.pepper;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LauncherService extends Service {

			private static final boolean VERBOSE = true;
    		private static final String TAG = null;
			long DBrefID;
			private Thread thread;
			private Handler handler = new Handler();

		  @Override
		  public int onStartCommand(Intent intent, int flags, int startId) {
		    //TODO do something useful
			  if (VERBOSE) Log.v(TAG, "launching toast to show service is running");
			  Toast.makeText(getBaseContext(), "Background Daemon active", Toast.LENGTH_LONG).show();
			    
			  thread = new Thread() {
			        public void run() {
			            while (true) {
			                PepperDB db = new PepperDB(getBaseContext());
			            	try { //queue up the next task to launch
			      			  
			    			  db.open();
			    			  long compID = -1;
			    			  compID =  db.getNextEntryToLaunch();
			    			  if (compID == DBrefID)
			    			  {
			    				  DBrefID = -2; //-2 is error code for same entry found
			    				  //we don't want this thread loop launching one item over and over
			    			  }
			    			  else
			    			  {
			    				  DBrefID = compID;
			    			  }
			    			  
			    			  if (DBrefID > -1)//-1 is the return value of getNextEntryToLaunch if nothing is found
			    			  {
			    			  	int calchour = db.getAppHour(DBrefID) - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			    			  		if(calchour < 0)
			    			  		{
			    			  		Log.d(TAG, "calchour found negative" + calchour);
			    			  		}
			    			  	int calcmin = calchour *60;
			    			  	calcmin = calcmin + (db.getAppMinute(DBrefID)- Calendar.getInstance().get(Calendar.MINUTE));
			    			  		if(calcmin < 0 && calchour == 0)
			    			  		{
			    			  			//calcmin = -1* calcmin;
			    			  		Log.d(TAG, "calcmin found negative" + calcmin);
			    			  		}
			                    
			                    
			                    //some complicated math that retrieves the 
			                    //next launch time and sets a sleep thread to sleep until launch time
			                    if (VERBOSE) Log.v(TAG, "thread to sleep for "+(calcmin*60000) +" Milliseconds");
			                    
			                   
			                    
			                    if (DBrefID >-1)
			                    { Log.d(TAG, "local Thread sleeping");
			                    	Thread.sleep( (calcmin*60000) );
			                    	 Log.d(TAG, "ExecuteNextTask()");
			                    	 ExecuteNextTask(db.getAppName(DBrefID));
			                    	 Thread.sleep(3600000);
			                    }
			    			  else if (DBrefID == -2 )
			    			  {
			    				  Log.d(TAG, "local Thread found same item: now sleeping");
			    				  Thread.sleep(3600000);
			    			  }else if (DBrefID == -1 )
			    			  {
			    				  Log.d(TAG, "local Thread found nothing to automate");
			    				  Thread.sleep(3600000);
			    			  }
			    			  else
			    			  {
			    				  Log.e(TAG, "ERROR!  unexpected results @ launcher service thread");
			    			  }
			                
			                   
			                    

			                }
			    			  } catch (InterruptedException e) {
			                    Log.e(TAG, "local Thread error", e);
			                    
			                }
			    			  db.close();
			            }
			        }
			    };
			    thread.start();
			  return Service.START_NOT_STICKY;
			  
		  }
		  public int ExecuteNextTask(String Appname)
		  {
			  	int successful = 0;
			  	try{
				Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(Appname);
				startActivity(LaunchIntent);
			  	}
			  	catch (Exception e)
			  	{
			  	successful = 1;	
			  	Log.e(TAG, "Error in ExecuteNextTask() function: ", e);
			  	}
			  	return successful;
		  }

		  @Override
		  public IBinder onBind(Intent intent) {
		  //TODO for communication return IBinder implementation
		    return null;
		  }

		  
		  
		  
		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (VERBOSE) Log.v(TAG, "launching toast to show service is stopping");
			Toast.makeText(getBaseContext(), "Background Daemon inactive", Toast.LENGTH_LONG).show();
			handler.removeCallbacks(thread);
			thread.interrupt();
			thread = null;
		}
		 
	
	
	

}
