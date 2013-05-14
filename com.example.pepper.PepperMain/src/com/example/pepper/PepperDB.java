package com.example.pepper;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.*;
import android.database.sqlite.*;
import android.database.*;
import android.util.Log;

public class PepperDB
{
	public static final String KEY_ROWID = "_id";
	public static final String KEY_APPNAME = "app_name";
	public static final String KEY_DISPLAYNAME = "app_label";
	public static final String KEY_DAY = "app_day";
	public static final String KEY_HOUR = "app_hour";
	public static final String KEY_MINUTE = "app_minute";
	private static final String DATABASE_NAME = "Pepperdb";
	private static final String DATABASE_TABLE = "launcher";
	//private static final String VIEW_NAME = "bgview";
	private static final int DATABASE_VERSION = 1;
	private static final boolean VERBOSE = true;
	private static final String TAG = null;

	private DbHelper pepperDbHelper;
	private SQLiteDatabase pepperDatabase;
	private Context pepperDbContext;



    public long createEntry(String appname, String displayname,  int day, int hour, int minute)
	{
		ContentValues cv = new ContentValues();// TODO: Implement this method
		cv.put(KEY_APPNAME,appname);
		cv.put(KEY_DISPLAYNAME, displayname);
		cv.put(KEY_DAY,day);
		cv.put(KEY_HOUR,hour);
		cv.put(KEY_MINUTE, minute);
		if (VERBOSE) Log.v(TAG, "insert into pepperDatabase happened here");

		return pepperDatabase.insert(DATABASE_TABLE,null,cv);

	}
    
    public long updateEntry(long rowID, String appname, String displayname,  int day, int hour, int minute)
	{
		ContentValues cv = new ContentValues();// TODO: Implement this method
		cv.put(KEY_APPNAME,appname);
		cv.put(KEY_DISPLAYNAME, displayname);
		cv.put(KEY_DAY,day);
		cv.put(KEY_HOUR,hour);
		cv.put(KEY_MINUTE, minute);
		if (VERBOSE) Log.v(TAG, "insert into pepperDatabase happened here");

		return pepperDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + rowID, null);

	}
    
    public long deleteEntry(long rowID)
    {
    	return pepperDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null);
    }
	

	private static class DbHelper extends SQLiteOpenHelper{

		//public DbHelper(Context context, String name, CursorFactory factory, int version) 
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + 
					   KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					   KEY_APPNAME  + " TEXT NOT NULL, " +
					   KEY_DISPLAYNAME + " TEXT NOT NULL, " +
					   KEY_DAY + " INTEGER, " +
					   KEY_HOUR + " INTEGER, " +
					   KEY_MINUTE +" INTEGER);"
					   );
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
			onCreate(db);

		}
	}



	

	public PepperDB(Context baseContext) {
		// TODO Auto-generated constructor stub
		pepperDbContext = baseContext;
	}



	public PepperDB open() throws SQLException
	{
		pepperDbHelper = new DbHelper(pepperDbContext);
		pepperDatabase = pepperDbHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		pepperDbHelper.close();
	}
	/* 
	 * ATTENTION! all retrieve functions require you to open the database first;
	 * opening conventions are simple: create an instance of the PepperDB
	 * 
	 * example:
	 * 
	 * PepperDB content = new PepperDB(getActivity().getBaseContext()); <--- if you're inside a fragment
	 * PepperDB content = new PepperDB(ActivityName.this);   <---- if you're calling this from an activity
	 * content.open();
	 * String dump = content.getAllData();
	 */
	public long getNextEntryToLaunch() //change back to long
	{
		String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};

		Cursor c = pepperDatabase.query(DATABASE_TABLE, columns,KEY_DAY+"=" +Calendar.getInstance().get(Calendar.DAY_OF_WEEK)+ " OR "
				+KEY_DAY+"=0", null, null, null, KEY_HOUR);
		int iRow = c.getColumnIndex(KEY_ROWID);
		//int iDay = c.getColumnIndex(KEY_DAY);
		int iHour = c.getColumnIndex(KEY_HOUR);
		int iMin = c.getColumnIndex(KEY_MINUTE);
		//int iApp = c.getColumnIndex(KEY_APPNAME);
		//int iName = c.getColumnIndex(KEY_DISPLAYNAME);
		int comphour = -1;
		int compmin = -1;
		long returnID = -1;
		//String returnString = "";
		while(c.moveToNext())
		{ 
			if(c.getInt(iHour) < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)&& returnID ==-1)
			{
				if (VERBOSE) Log.v(TAG, c.getInt(iHour)+" was smaller than " +Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+ " looping back");
			continue;
			}
			else //the hour is either equal or greater to the current hour, or there's nothing on the list. 
			{		
				if (VERBOSE) Log.v(TAG, "hour is greater to or equal to the current hour");
				//case where the launch time in minutes is less than the current time
				if(c.getInt(iHour)== Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && c.getInt(iMin) < Calendar.getInstance().get(Calendar.MINUTE)){
					if (VERBOSE) Log.v(TAG, "minute "+c.getInt(iMin)+" was smaller than " +Calendar.getInstance().get(Calendar.MINUTE)+ " looping back");
					continue;
				}
				else
				{//this entry is guaranteed to have it's hour and minute larger than, or equal to this very minute
					
					if(returnID == -1)
					{comphour = c.getInt(iHour);
					returnID = c.getLong(iRow);
					compmin = c.getInt(iMin);
					if (VERBOSE) Log.v(TAG, "initializing "+c.getInt(iRow)+ " RowID to be @ time "+c.getInt(iHour)+":" +c.getInt(iMin));
					//now that we have a comparing value to measure, we want to make sure that this entry is closest to now.					
					continue;
					}
					else
					{
					//use temporary variables to compare the next 
					if(c.getInt(iHour)> comphour)
						{
						//the next entry is at least an hour later than this entry, and the stored entry is the closest
						if (VERBOSE) Log.v(TAG, "Returning "+returnID+ " RowID");
						
						return returnID;
						}
					else//if not then the iHour must be equal to the comphour, and minutes need to be compared
						{
						//we only care if the minutes of the entry are closer to now than the stored entry, while still being greater than now.
							if(c.getInt(iMin)>Calendar.getInstance().get(Calendar.MINUTE)&& c.getInt(iMin)< compmin)
							{
							//we got a new winner!
							comphour = c.getInt(iHour);
							returnID = c.getLong(iRow);
							compmin = c.getInt(iMin);
							if (VERBOSE) Log.v(TAG, "Found "+c.getInt(iRow)+ " RowID to be @ time "+c.getInt(iHour)+":" +c.getInt(iMin));
							}
							else
							{
							//might want to check the next entry to see if it's closer...
							continue;
							}
						}
					}
				}
			}
	
		}
		if (VERBOSE) Log.v(TAG, "Database EOF: Returning "+returnID+ " RowID");
			return returnID;
	
	}
	
	public String getAllData()
	{
		//this bad motha rolls up all the data in the table  
		if (VERBOSE) Log.v(TAG, " ++ all data call queued ++ " );

		String returnString = "";
		String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};
		
		//PepperDB content  = open();
		//if (VERBOSE) Log.v(TAG, " pepperDatabase Open variable is :  " + content.pepperDatabase.isOpen());
		//Cursor c = content.pepperDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		Cursor c = pepperDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);		
		
		
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iApp = c.getColumnIndex(KEY_APPNAME);
		int iName = c.getColumnIndex(KEY_DISPLAYNAME);
		int iDay = c.getColumnIndex(KEY_DAY);
		int iHour = c.getColumnIndex(KEY_HOUR);
		int iMin = c.getColumnIndex(KEY_MINUTE);
		while(c.moveToNext())
			{ returnString = returnString  + c.getString(iRow) + " " + c.getString(iApp) + " " + c.getString(iName) + " " + c.getString(iDay) + " " + c.getString(iHour) + " " + c.getString(iMin) + "\n";
			}
		c.close();
		close();
		
		if (VERBOSE) Log.v(TAG, "++ dump attempt successful ++");
		if (VERBOSE) Log.v(TAG, "contents of dump:  " + "\n" + returnString );
		
		return returnString;		
	}
	public ArrayList<Long> getRowIDArray()
	{
		ArrayList<Long> RowIDs = new ArrayList<Long>();
	String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};
	Cursor c = pepperDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);		
	int iRow = c.getColumnIndex(KEY_ROWID);
	
	
		while(c.moveToNext())
		{ RowIDs.add( c.getLong(iRow));
		}
		c.close();
		return RowIDs;
	}
	
	public String getAppName(long rowID)
	{
		String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};
		
		//PepperDB content  = open();
		//if (VERBOSE) Log.v(TAG, " pepperDatabase Open variable is :  " + content.pepperDatabase.isOpen());
		//Cursor c = content.pepperDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		Cursor c;
		String retrievedAppName;
		if (pepperDatabase != null)
		{
		c = pepperDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + rowID, null, null, null, null);		
		if(c.moveToFirst())
		{
			 retrievedAppName = c.getString(c.getColumnIndex(KEY_APPNAME)); 
			 c.close();
			 return retrievedAppName;
		}
		else
		{
			c.close();
			return "Nothing to display.";}
		}
		else
		{
			if (VERBOSE) Log.v(TAG, " pepperDatabase Open failed ");
			return "Database error:  Database not found";
		}

	}
	
	
	public String getAppLabel(long rowID)
	{
		String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};
		
		Cursor c;
		String retrievedAppName;
		if (pepperDatabase != null)
		{
		c = pepperDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + rowID, null, null, null, null);		
		if(c.moveToFirst())
		{
			 retrievedAppName = c.getString(c.getColumnIndex(KEY_DISPLAYNAME)); 
			 c.close();
			 return retrievedAppName;
		}
		else
		{
			c.close();
			return "Nothing to display.";}
		}
		else
		{
			if (VERBOSE) Log.v(TAG, " pepperDatabase Open failed ");
			return "Database error:  Database not found";
		}
	}
	public int getAppDay(long rowID)
	{
		String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};
		
		//PepperDB content  = open();
		//if (VERBOSE) Log.v(TAG, " pepperDatabase Open variable is :  " + content.pepperDatabase.isOpen());
		//Cursor c = content.pepperDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		Cursor c;
		int retrievedDay;
		if (pepperDatabase != null)
		{
		c = pepperDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + rowID, null, null, null, null);		
		if(c.moveToFirst())
		{
			
			 retrievedDay = c.getInt(c.getColumnIndex(KEY_DAY)); 
			 c.close();
			 return retrievedDay;
		}
		else
		{
			c.close();
			return -1;}
		}
		else
		{
			if (VERBOSE) Log.v(TAG, " pepperDatabase Open failed ");
			return -34404;
		}
	}
	public String getDayText(int dayNumber)
	{
		switch(dayNumber)
		{
		
		//case = All Days
			case 0:
				return "Daily";
		//case = Sunday
			case 1:
				return "Sunday";
		//case = Monday
			case 2:
				return "Monday"; 
		//case = Tuesday
			case 3:
				return "Tuesday";
		//case = Wednesday
			case 4:
				return "Wednesday";
		//case = Thursday
			case 5:
				return "Thursday";
		//case = Friday
			case 6:
				return "Friday";
		//case = Saturday
			case 7:
				return "Saturday";
		//if anything else, return an error.
			default:
				return "Error!";
		}
	}
	public String formatTime(int hour, int minute)
	{
		//formats the gabledegook time into a human readable one (String resource)
		String returnFormat = "";
		if (hour <10)
			returnFormat = returnFormat + "0";
		
		returnFormat = returnFormat + hour + ":";
		if (minute <10)
			returnFormat = returnFormat + "0";
		
		returnFormat = returnFormat + minute;
		
		return returnFormat;
	}
	
	public int getAppHour(long rowID)
	{
		String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};
		
		//PepperDB content  = open();
		//if (VERBOSE) Log.v(TAG, " pepperDatabase Open variable is :  " + content.pepperDatabase.isOpen());
		//Cursor c = content.pepperDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		Cursor c;
		int retrievedHour;
		if (pepperDatabase != null)
		{
		c = pepperDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + rowID, null, null, null, null);		
		if(c.moveToFirst())
		{
			
			 retrievedHour = c.getInt(c.getColumnIndex(KEY_HOUR)); 
			 c.close();
			 return retrievedHour;
		}
		else
		{
			c.close();
			return -1;}
		}
		else
		{
			if (VERBOSE) Log.v(TAG, " pepperDatabase Open failed ");
			return -34404;
		}
	}
	public int getAppMinute(long rowID)
	{
		String[] columns = new String[]{KEY_ROWID, KEY_APPNAME, KEY_DISPLAYNAME, KEY_DAY, KEY_HOUR,KEY_MINUTE};
		
		//PepperDB content  = open();
		//if (VERBOSE) Log.v(TAG, " pepperDatabase Open variable is :  " + content.pepperDatabase.isOpen());
		//Cursor c = content.pepperDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		Cursor c;
		int retrievedMinute;
		if (pepperDatabase != null)
		{
		c = pepperDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + rowID, null, null, null, null);		
		if(c.moveToFirst())
		{
			
			 retrievedMinute = c.getInt(c.getColumnIndex(KEY_MINUTE)); 
			 c.close();
			 return retrievedMinute;
		}
		else
		{
			c.close();
			return -1;}
		}
		else
		{
			if (VERBOSE) Log.v(TAG, " pepperDatabase Open failed ");
			return -34404;
		}
	}

	


	

}
