package com.agilesumo.timedbreath;



import java.util.Calendar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView; 


public class BreathingOutputActivity extends Activity {
	private enum Status {
    	INHALE,EXHALE, IN_HOLD, OUT_HOLD
    }
	private final int INTERVAL = 100;
	private int inHoldDuration;
	private int outHoldDuration;
	private int inhaleBarMax;
	private int exhaleBarMax;
	private ProgressBar progressBar;
    private MoreAccurateTimer timer;
    private TextView promptText;
    private TextView timerText;
    private Status status;
    private Calendar calendar;
    private int onTickCounter = 0;
    private int inHoldCounter = 0;
    private int outHoldCounter = 0;
    private MediaPlayer mPlayer;
    private int i = 0;
    private boolean audioOn;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		else {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
		
		
		Intent intent = getIntent();
		
		String inhaleStr = intent.getStringExtra(MainActivity.EXTRA_INHALE);
		String exhaleStr = intent.getStringExtra(MainActivity.EXTRA_EXHALE);
		String inHoldStr = intent.getStringExtra(MainActivity.EXTRA_IN_HOLD);
		String outHoldStr = intent.getStringExtra(MainActivity.EXTRA_OUT_HOLD);
		String durationStr = intent.getStringExtra(MainActivity.EXTRA_DURATION);
		String backgroundStr = intent.getStringExtra(MainActivity.EXTRA_BACKGROUND);
		String audioStr = intent.getStringExtra(MainActivity.EXTRA_AUDIO);
		audioOn = true;
		
		
		if (audioStr.equals("None")){
			audioOn = false;
		}
		else if (audioStr.equals("Music")){
			mPlayer = MediaPlayer.create(BreathingOutputActivity.this, R.raw.tribalwisdom);
			mPlayer.setLooping(true);
		}
		else if (audioStr.equals("Stream")){
			mPlayer = MediaPlayer.create(BreathingOutputActivity.this, R.raw.stream);
			mPlayer.setLooping(true);
		}
		else if (audioStr.equals("Waterfall")){
			mPlayer = MediaPlayer.create(BreathingOutputActivity.this, R.raw.waterfall);
			mPlayer.setLooping(true);
		}
		
		int backgroundId = R.drawable.clouds_980_735;
		if(backgroundStr.equals("Ocean")){
			backgroundId = R.drawable.waves_980_735;
		}
		else if(backgroundStr.equals("Dandelions")){
			backgroundId = R.drawable.dandilions_980_665;
		}
		else if(backgroundStr.equals("Stars")){
			backgroundId = R.drawable.stars_980_857;
		}
		
		
		int inhaleDuration = Integer.parseInt(inhaleStr.substring(0,1));
		int exhaleDuration = Integer.parseInt(exhaleStr.substring(0,1));
		inHoldDuration = Integer.parseInt(inHoldStr.substring(0,1));
		outHoldDuration = Integer.parseInt(outHoldStr.substring(0,1));
		int totalDuration;
		if(durationStr.length() > 6){
			totalDuration = Integer.parseInt(durationStr.substring(0,2));

		}
		else {
			totalDuration = Integer.parseInt(durationStr.substring(0,1));
		}
		
		inhaleBarMax = inhaleDuration * 10;		
		exhaleBarMax = exhaleDuration * 10;
		
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, totalDuration);
        calendar.set(Calendar.SECOND, 0);
        	
		setContentView(R.layout.activity_breathing_output);
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.main_layout);
		
		timerText = (TextView)findViewById(R.id.countDown_timer);	        
	    timerText.setText("" + calendar.get(Calendar.MINUTE) + ":" + "00");
		
		promptText = (TextView)findViewById(R.id.prompt);
        promptText.setText("Inhale");
		
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(false);
        progressBar.setMax(inhaleBarMax);
        progressBar.setProgress(i);
        
		mainLayout.setBackgroundResource(backgroundId);
		// The text color need to be changed to increase visibility for stars background 
	    timerText.setTextColor(Color.WHITE);
		promptText.setTextColor(Color.WHITE);
		

        
        timer = new Timer (totalDuration*60*1000, INTERVAL); // timer ticks 10 times per second
        status = Status.INHALE;
        
        if(audioOn){
        	mPlayer.start();
        }
        
        timer.start();
        
	}
	
	  @Override
	  public void onStart() {
	    super.onStart();
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	  }
	
	protected void onResume(){
		super.onResume();
		if(audioOn){
	        mPlayer.start();
		}
		
	}
	
	protected void onPause(){
		super.onPause();
		if(audioOn){
	        mPlayer.pause();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.breathing_output, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_back:
	        	finish();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	//*************More Accurate Timer extends Timer class****************
	
	public class Timer extends MoreAccurateTimer {

        public void startCountdownTimer() {  
        }
        
       public Timer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
          timerText.setText("0:00");
          if(audioOn){
              mPlayer.stop();
          }
          finish();   
        }

        @Override
        public void onTick(long millisUntilFinished) {
        	onTickCounter++;
        	if (onTickCounter % 10 == 0) {
        		calendar.add(Calendar.SECOND, -1);
        		String secondsLeft = "" + calendar.get(Calendar.SECOND);
        		//formating output make sure that when seconds Left is a single digit
        		//that a preceding "0" added to the output
        		if (secondsLeft.length() == 1 ) {
        			secondsLeft = "0" + secondsLeft;	
        		}
        		timerText.setText(""+calendar.get(Calendar.MINUTE)+":"+secondsLeft);

        	}
        	if(status.equals(Status.INHALE) ) {
	        	i++;
	            progressBar.setProgress(i);
	            
	            if ( i == inhaleBarMax ) {
	            	if ( inHoldDuration > 0 ) {
	            		status = Status.IN_HOLD;
	            		promptText.setText("In Hold");
	            	}
	            	else {
	            		status = Status.EXHALE;
		            	promptText.setText("Exhale");
	            	}
	            	progressBar.setMax(exhaleBarMax);
	            	progressBar.setProgress(exhaleBarMax);
	            	i = exhaleBarMax;
	            }
	        }
        	else if ( status == Status.IN_HOLD ){
        		inHoldCounter++;
        		if ( (inHoldDuration * 10) == inHoldCounter ) {
        			status = Status.EXHALE;
        			promptText.setText("Exhale");
        			inHoldCounter = 0;
        		}
        	}
        	else if ( status == Status.EXHALE) {
        		i--;
                progressBar.setProgress(i);
	            	            
	            if (i == 0 && outHoldDuration == 0) {
	            	status = Status.INHALE;
	            	promptText.setText("Inhale");
		            progressBar.setMax(inhaleBarMax);
	            	progressBar.setProgress(0);
	            }
	            if (i == 0 && outHoldDuration > 0 ) {
	            	status = Status.OUT_HOLD;
	            	promptText.setText("Out Hold");
		            progressBar.setMax(inhaleBarMax);
	            	progressBar.setProgress(0);
	            	
	            }
	            

        	}
        	
        	else if(status == Status.OUT_HOLD){
        		outHoldCounter++;
        		if ( (outHoldDuration * 10) == outHoldCounter ) {
        			status = Status.INHALE;
        			promptText.setText("Inhale");
        			outHoldCounter = 0;
        		}
        		
        	}
        }
    }
	
}	
	 