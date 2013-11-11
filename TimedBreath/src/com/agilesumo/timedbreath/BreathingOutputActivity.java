package com.agilesumo.timedbreath;



import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BreathingOutputActivity extends Activity {
	private enum Status {
    	INHALE,EXHALE
    }
	private final int PROGRESS_BAR_MAX = 48;
	private long inhaleDurationMillisec = 12000;
	private long inhaleIntervalMillisec = 125;
	private long exhaleDurationMillisec;
	private long exhaleIntervalMillisec;
	private ProgressBar progressBar;
	private TextView timeCounter;
    private MoreAccurateTimer inhaleTimer;
    private MoreAccurateTimer exhaleTimer;

    private TextView promptText;
    private Status status;
    
    
    
    
    int i = 0;
    boolean isExhaleDone = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String inhaleStr = intent.getStringExtra(MainActivity.EXTRA_INHALE);
		String exhaleStr = intent.getStringExtra(MainActivity.EXTRA_EXHALE);
		int inhaleDuration = Integer.parseInt(inhaleStr.substring(0,1));
		int exhaleDuration = Integer.parseInt(exhaleStr.substring(0,1));
		
		inhaleDurationMillisec = inhaleDuration * 1000;
		inhaleIntervalMillisec = (inhaleDuration * 1000) / PROGRESS_BAR_MAX;
		
		exhaleDurationMillisec = exhaleDuration * 1000;
		exhaleIntervalMillisec = (exhaleDuration * 1000) / PROGRESS_BAR_MAX;


		
		
		setContentView(R.layout.activity_breathing_output);
		
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(false);
        //progressBar.invalidate();
        progressBar.setProgress(i);
        
		inhaleTimer = new Timer(inhaleDurationMillisec,inhaleIntervalMillisec);
		exhaleTimer = new Timer(exhaleDurationMillisec, exhaleIntervalMillisec);
		
        timeCounter = (TextView)findViewById(R.id.time_counter);
        promptText = (TextView)findViewById(R.id.prompt);
        status = Status.INHALE;
        inhaleTimer.start();
        //promptText.setText("Breath In");
        //promptText.setBackgroundColor(Color.LTGRAY);
        
        
		    

        
	    //check this example out
	    //http://stackoverflow.com/questions/14818177/progressbar-with-a-countdowntimer-android
	    
		
	}
	
	protected void onResume(){
		super.onResume();
		
		
		
	}

	
	protected void onPause(){
		super.onPause();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.breathing_output, menu);
		return true;
	}
	
	
	//*****************************
	
	
	public class Timer extends MoreAccurateTimer {

        public void startCountdownTimer() {  
        }
        
       public Timer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            i++;
            progressBar.setProgress(i);
            status = Status.EXHALE;
            if (!isExhaleDone){
            	exhaleTimer.start();
            	isExhaleDone = true;

            }
            	
            
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //timeCounter.setText("Time remaining: " + (millisUntilFinished / 100));
        	if(status.equals(Status.INHALE)) {
	        	i++;
	            timeCounter.setText("i: " + i + "sec until finish" + millisUntilFinished);
	            System.out.println("milli until finishe ="+millisUntilFinished);
	            System.out.println("i = "+i);
	            progressBar.setProgress(i);
	            
	           
            
        	}
        	else {
        		i--;
	            timeCounter.setText("i: " + i + "sec until finish" + millisUntilFinished);
	            System.out.println("+++++++++++++++++++++++++milli until finishe ="+millisUntilFinished);
	            System.out.println("i = "+i);
	            progressBar.setProgress(i);
        	}
        }
    }
	
	
		 
}	
	 
		
	
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


