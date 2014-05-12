package com.agilesumo.timedbreath;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends Activity {
	

	//=======Constants========

	public final static String EXTRA_INHALE = "com.agilesumo.timedbreath.INHALE";
	public final static String EXTRA_EXHALE = "com.agilesumo.timedbreath.EXHALE";
	public final static String EXTRA_DURATION = "com.agilesumo.timedbreath.DURATION";
	public final static String EXTRA_IN_HOLD ="com.agilesumo.timedbreath.IN_HOLD";
	public final static String EXTRA_OUT_HOLD ="com.agilesumo.timedbreath.OUT_HOLD";
	public final static String EXTRA_MUSIC = "com.agilesumo.timedbreath.MUSIC";
	public final static String EXTRA_BACKGROUND = "com.agilesumo.timedbreath.BACKGROUND";
	public final static String EXTRA_AUDIO = "com.agilesumo.timedbreath.AUDIO";
	
	// =======================
	
	// =====Instance variables=====
	
	private Spinner inhaleSpinner;
	private Spinner exhaleSpinner;
	private Spinner inHoldSpinner;
	private Spinner outHoldSpinner;
	private Spinner durationSpinner;
	private Spinner audioSpinner;
	private Spinner backgroundSpinner;
	private SharedPreferences settings;
	
	// ===========================



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		
		inhaleSpinner = (Spinner)findViewById(R.id.inhale_spinner);
		exhaleSpinner = (Spinner)findViewById(R.id.exhale_spinner);
		inHoldSpinner = (Spinner)findViewById(R.id.in_hold_spinner);
		outHoldSpinner = (Spinner)findViewById(R.id.out_hold_spinner);
		durationSpinner = (Spinner)findViewById(R.id.duration_spinner);
		audioSpinner = (Spinner)findViewById(R.id.audio_spinner);
		backgroundSpinner = (Spinner)findViewById(R.id.background_spinner);
		audioSpinner = (Spinner)findViewById(R.id.audio_spinner);
		
	    String inhaleSpinnerPos = settings.getString("inhaleDuration", "0");
	    String exhaleSpinnerPos = settings.getString("exhaleDuration", "0");
	    String inHoldSpinnerPos = settings.getString("inHoldDuration", "0");
	    String outHoldSpinnerPos = settings.getString("outHoldDuration", "0");
	    String durationSpinnerPos = settings.getString("totalDuration", "0");
	    String backgroundPos = settings.getString("background", "0");
	    String audioPos = settings.getString("audio", "0");
	    
	    inhaleSpinner.setSelection(Integer.parseInt(inhaleSpinnerPos));
	    exhaleSpinner.setSelection(Integer.parseInt(exhaleSpinnerPos));    
	    inHoldSpinner.setSelection(Integer.parseInt(inHoldSpinnerPos));    
	    outHoldSpinner.setSelection(Integer.parseInt(outHoldSpinnerPos));    
	    durationSpinner.setSelection(Integer.parseInt(durationSpinnerPos));    
	    backgroundSpinner.setSelection(Integer.parseInt(backgroundPos));
	    audioSpinner.setSelection(Integer.parseInt(audioPos));
	    	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_close:
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	  @Override
	  public void onStart() {
	    super.onStart();
	  }

	  
	 
	
	
	protected void onStop() {
		super.onStop();
		// We need an Editor object to make preference changes.
	    // All objects are from android.context.Context
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("inhaleDuration", ""+inhaleSpinner.getSelectedItemPosition() );
	    editor.putString("exhaleDuration", ""+exhaleSpinner.getSelectedItemPosition() );
	    editor.putString("inHoldDuration", ""+inHoldSpinner.getSelectedItemPosition() );
	    editor.putString("outHoldDuration", ""+outHoldSpinner.getSelectedItemPosition() );
	    editor.putString("totalDuration", ""+durationSpinner.getSelectedItemPosition() );
	    editor.putString("background", ""+backgroundSpinner.getSelectedItemPosition());
	    editor.putString("audio", ""+audioSpinner.getSelectedItemPosition());
        // Commit the edits!
        editor.commit();
	}
	/** Called when the user clicks the start session button */

	public void outputBreathing(View view) {
		try {
			Intent intent = new Intent(this, BreathingOutputActivity.class);

			
			String inhaleDuration = inhaleSpinner.getSelectedItem().toString();
			String exhaleDuration = exhaleSpinner.getSelectedItem().toString();
			String inHoldDuration = inHoldSpinner.getSelectedItem().toString();
			String outHoldDuration = outHoldSpinner.getSelectedItem().toString();
			String background = backgroundSpinner.getSelectedItem().toString();
			
			String totalDuration = durationSpinner.getSelectedItem().toString();
			String audio = audioSpinner.getSelectedItem().toString();
			
		    intent.putExtra(EXTRA_INHALE, inhaleDuration);
		    intent.putExtra(EXTRA_EXHALE, exhaleDuration);
		    intent.putExtra(EXTRA_IN_HOLD, inHoldDuration);
		    intent.putExtra(EXTRA_OUT_HOLD, outHoldDuration);
		    intent.putExtra(EXTRA_DURATION, totalDuration);
		    intent.putExtra(EXTRA_BACKGROUND, background);
		    intent.putExtra(EXTRA_AUDIO, audio);
		
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
