package asus.ft.autoBrowsing;

import java.util.Timer;
import java.util.TimerTask;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

public class AutoBrowser_Thread extends Thread{
	public int _runtime=1;
	public int _duration1=1;
	public int _duration2=1;
	public int RunCount=1;
	Context _Context;
	Intent _intent;
	int repeatTime=1;
	public AutoBrowser_Thread(Context Context,Intent intent,int runtime,int duration1,int duration2)
	{
		this._runtime=runtime;
		this._duration1=duration1;
		this._duration2=duration2;
		this._intent=intent;
		this._Context= Context;
	}
	public void run() {
		try {
			 Timer timer = new Timer(true);
			 long startTime = SystemClock.elapsedRealtime();	
				int tmp = 0;
				// 若 duration1==duration2, 則定時執行
				if (_duration1 == _duration2) {
					tmp = _duration1 * 60;
				} else {
					int tmp_min = (int) (Math.random() * 100
							% (_duration2 - _duration1) + _duration1);
					tmp = (int) (Math.random() * 10 + 60 * tmp_min);
				}
				// 使用者輸入的秒數
				repeatTime = repeatTime + tmp * 1000;

				 timer.schedule(AutoRun,tmp*1000,tmp*1000);
				 
		} catch (Exception e) {
			Log.i("info","Run() error" + e.getMessage());
			e.getMessage();
		}

	}
	
	private TimerTask AutoRun = new TimerTask(){
		
		@Override
	
		public void run() {

		// TODO Auto-generated method stub
        
		if (RunCount<=_runtime){
			Intent i = new Intent(_Context, BrowsingServices.class);
			Bundle bb = new Bundle();
			bb.putString("STR_CALLER", "");
			i.putExtras(bb);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_Context.startService(i);
			RunCount++;

		}

		}

		 
	
		};
}
