package com.trecstudios.pillbox;

import java.util.Date;

import com.trecstudios.pillbox.logger.Log;
import com.trecstudios.pillbox.logger.LogFragment;
import com.trecstudios.pillbox.logger.LogWrapper;
import com.trecstudios.pillbox.logger.MessageOnlyLogFilter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

public class MainActivity extends SampleActivityBase {

	private boolean mLogShown;
	
	private PillStore pillStore = new PillStore(this);
	private RecyclerViewFragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			fragment = new RecyclerViewFragment();
			
			fragment.initPillStore(pillStore);
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
		
		
//		NOTIFCAITON ONE
	
		

		//back from new pill
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			String ndc = extras.getString("NDC_CODE");
			
			this.addPill(ndc);
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add_pill) {
		    Intent intent = new Intent(this, CameraTestActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addPill(String ndc){
	
		
		Pill x= new Pill(ndc);
		x.setTiming(new PillTiming.ByHour(6));
		x.setDosage(1);
		
		x.setInitialTarget(new Date());
		
		pillStore.addPill(x);
		
	
		
		
		Intent alarmIntent = new Intent(this, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 15000, pendingIntent);
		
	}
}
