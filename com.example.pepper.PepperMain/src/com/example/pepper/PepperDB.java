package com.example.pepper;
import java.util.ArrayList;

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
