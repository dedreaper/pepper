package com.example.pepper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class EntryPoint extends FragmentActivity {

    Button firstButton;
    Button secondButton;        
    Button thirdButton;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pepper_main);
        addDynamicFragment();

        firstButton = (Button) findViewById(R.id.firstButton);
        secondButton = (Button) findViewById(R.id.secondButton);        
        thirdButton = (Button) findViewById(R.id.thirdButton);
        firstButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				firstButton.setBackgroundResource(R.drawable.pepperbuttonpressed);
				secondButton.setBackgroundResource(R.drawable.pepperbutton);
				thirdButton.setBackgroundResource(R.drawable.pepperbutton);
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack so the user can navigate back
				transaction.replace(R.id.fragment1, RecentTab.newInstance());
				transaction.addToBackStack(null);
				// Commit the transaction
				transaction.commit();
			}
		});
        secondButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				firstButton.setBackgroundResource(R.drawable.pepperbutton);
				secondButton.setBackgroundResource(R.drawable.pepperbuttonpressed);
				thirdButton.setBackgroundResource(R.drawable.pepperbutton);
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack so the user can navigate back
				transaction.replace(R.id.fragment1, ScheduledTab.newInstance());
				transaction.addToBackStack(null);
				// Commit the transaction
				transaction.commit();
			}
		});
        thirdButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				firstButton.setBackgroundResource(R.drawable.pepperbutton);
				secondButton.setBackgroundResource(R.drawable.pepperbutton);
				thirdButton.setBackgroundResource(R.drawable.pepperbuttonpressed);

				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack so the user can navigate back
				transaction.replace(R.id.fragment1, OptionsTab.newInstance());
				transaction.addToBackStack(null);
				// Commit the transaction
				transaction.commit();

			}
		});

        
	}

    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		

        
	}

	private void addDynamicFragment() {
        // TODO Auto-generated method stub
               // creating instance of the HelloWorldFragment.
    	android.support.v4.app.Fragment fg = RecentTab.newInstance();
        // adding fragment to relative layout by using layout id
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack so the user can navigate back
		transaction.replace(R.id.fragment1, RecentTab.newInstance());
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
    }
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
	}

}
