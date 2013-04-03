package com.byfdevelopment.popcine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PopCineService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// ScheduledThreadPoolExecutor pool = new
		// ScheduledThreadPoolExecutor(1);
		// pool.scheduleAtFixedRate(new ScratchingTask(), 0,1,TimeUnit.HOURS);
		// new ScratchingTask(getApplicationContext(), null).execute();

		Log.w("PopCineService", "Service exectuado");
		//return super.onStartCommand(intent, flags, startId);
		return Service.START_STICKY;
	}
}
