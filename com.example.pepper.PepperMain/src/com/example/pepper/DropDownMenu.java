package com.example.pepper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DropDownMenu extends ListActivity{
    private static final boolean VERBOSE = true;
    private static final String TAG = null;
	String[] programList = {"com.facebook.katana", "com.pandora.android", "com.aide.ui", "com.aide.ui", "com.aide.ui", "com.aide.ui", "com.aide.ui"};
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(DropDownMenu.this, android.R.layout.simple_list_item_1, programList));
	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String positionMark = programList[position];

		try{
			Class ourClass = Class.forName(positionMark);
		
		Intent intent = new Intent(DropDownMenu.this, ourClass);
		startActivity(intent);
		}catch(ClassNotFoundException e)
		{e.printStackTrace();}
	}
	
}
