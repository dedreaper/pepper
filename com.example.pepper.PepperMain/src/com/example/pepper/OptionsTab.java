package com.example.pepper;


import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
@TargetApi(7)
public class OptionsTab extends Fragment {
   TextView DBDump ;
	
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
		DBDump = (TextView)getActivity().findViewById(R.id.o_dbdump);
		DBDump.setText(content.getAllData());
		
	}
}