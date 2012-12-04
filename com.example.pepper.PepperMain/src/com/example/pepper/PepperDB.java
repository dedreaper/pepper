package com.example.pepper;
import android.content.*;
import android.database.sqlite.*;
import android.database.*;

public class PepperDB
{
	public static final String KEY_ROWID = "_id";
	public static final String KEY_APPNAME = "app_name";
	public static final String KEY_DAY = "app_day";
	public static final String KEY_TIME = "app_time";
	private static final String DATABASE_NAME = "Pepperdb";
	private static final String DATABASE_TABLE = "launcher";
	private static final int DATABASE_VERSION = 1;

	private DbHelper pepperDbHelper;
	private SQLiteDatabase pepperDatabase;
	private Context pepperDbContext;



    public void createEntry(String appname, int day, int time)
	{
		ContentValues cv = new ContentValues();// TODO: Implement this method
		cv.put(KEY_APPNAME,appname);
		cv.put(KEY_DAY,day);
		cv.put(KEY_TIME,time);
		pepperDatabase.insert(DATABASE_TABLE,null,cv);

	}
	

	private static class DbHelper extends SQLiteOpenHelper{

		//public DbHelper(Context context, String name, CursorFactory factory, int version) 
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE" + DATABASE_TABLE + " (" + 
					   KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					   KEY_APPNAME  + " TEXT NOT NULL, " +
					   KEY_DAY+ " INTEGER, " +
					   KEY_TIME +" INTEGER);"
					   );

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
			onCreate(db);

		}
	}

	public PepperDB(Context c)
	{
		pepperDbContext = c;
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

	

}
