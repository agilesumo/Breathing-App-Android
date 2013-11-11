package com.agilesumo.timedbreath;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	

	//=======Constants========

	public final static String EXTRA_INHALE = "com.agilesumo.timedbreath.INHALE";
	public final static String EXTRA_EXHALE = "com.agilesumo.timebreath.EXHALE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/** Called when the user clicks the start session button */

	public void outputBreathing(View view) {
		try {
			Intent intent = new Intent(this, BreathingOutputActivity.class);
			Spinner inhaleSpinner = (Spinner)findViewById(R.id.inhale_spinner);
			Spinner exhaleSpinner = (Spinner)findViewById(R.id.exhale_spinner);
			String inhaleDuration = inhaleSpinner.getSelectedItem().toString();
			String exhaleDuration = exhaleSpinner.getSelectedItem().toString();
			
		    intent.putExtra(EXTRA_INHALE, inhaleDuration);
		    intent.putExtra(EXTRA_EXHALE, exhaleDuration);
		
			startActivity(intent);
			
		}
		catch (Exception e) {
		    // handle any errors
		    Log.e("ErrorMainActivity", "Error in activity", e);  // log the error
		    // Also let the user know something went wrong
		    Toast.makeText(
		        getApplicationContext(),
		        e.getClass().getName() + " " + e.getMessage(),
		        Toast.LENGTH_LONG).show();
		}
	}    
	
}
