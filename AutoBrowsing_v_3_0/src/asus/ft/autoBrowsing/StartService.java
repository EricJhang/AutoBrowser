package asus.ft.autoBrowsing;



import java.util.Timer;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;


public class StartService extends Service {
	public static Handler StartServiceHandler = new Handler();
	public int _runtime=1;
	public int _duration1=1;
	public int _duration2=1;
	public int RunCount=1;
	public boolean StopFlag=false;
	Message message;
	 Timer timer ;
	Thread Schedule_AutoBroswer;
	public static Context MainContext;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate();
		 StartServiceHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					// Log.i(PathAndFlag.Tag,"�i�JhandleMessage");
					switch (msg.what) {
					case 1:
						 cancelAM();
						break;
					case 2:

					default:
						break;
					}
				}
			};
	
	}
	public int onStartCommand(Intent intent, int flags, int startID)
	{
		
			
		long startTime = SystemClock.elapsedRealtime(); // �t�Υثe�ɶ�
		// �Q��AlarmManager�ӱƵ{
		//intent = new Intent();
		MainContext=this.getApplicationContext();
		long repeatTime = 1;
		if(intent.getExtras()!=null)
		{
		Bundle _bundle_var = intent.getExtras();
		
		_runtime=_bundle_var.getInt("runtime");
		Log.i(PathAndFlag.LogcatTAG,"runtime="+ _runtime);
		 _duration1=_bundle_var.getInt("duration1");
		 Log.i(PathAndFlag.LogcatTAG,"duration1="+ _duration1);
		 _duration2=_bundle_var.getInt("duration2");
		 Log.i(PathAndFlag.LogcatTAG,"duration2="+ _duration2);
		 if(!BrowsingServices.toStop)
		 {
			//�z�Ltimer �ӱƵ{
			 timer = new Timer(true);
			
				int tmp = 0;
				// �Y duration1==duration2, �h�w�ɰ���
				if (_duration1 == _duration2) {
					tmp = _duration1 * 60;
				} else {
					int tmp_min = (int) (Math.random() * 100
							% (_duration2 - _duration1) + _duration1);
					tmp = (int) (Math.random() * 10 + 60 * tmp_min);
				}
				// �ϥΪ̿�J�����
				repeatTime = repeatTime + tmp * 1000;

				 timer.schedule(AutoRun,tmp*1000 ,tmp*1000);
	
		 }
		}
		
		return START_STICKY;

	}
	private TimerTask AutoRun = new TimerTask(){
		
		@Override
	
		public void run() {

		// TODO Auto-generated method stub
        //��RunCount�������榸�ơA�Y�W�L_runtime�hTimer����
		if (RunCount<=_runtime){
			
			Intent i = new Intent(MainContext, BrowsingServices.class);
			Bundle variable = new Bundle();
			variable.putString("STR_CALLER", "");
			variable.putInt("RunCount", RunCount);
			i.putExtras(variable);
			//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MainContext.startService(i);
			RunCount++;

		}
		else
		{
			FileIO.WriteAPI_Result("========Test Stop========");
			//����timer�A�Y�����������e���o��Exception
			 timer.cancel();
		     AutoRun.cancel();
		     System.exit(0);
		}
		}
		 
	
		};
	@Override
	public void onDestroy() {	
		super.onDestroy();
	
		message = new Message();
		message.what = 1;
		 Log.i(PathAndFlag.LogcatTAG,"onDestroy ");
		//����timer
		StartServiceHandler.sendMessage(message);
		
	}
	@Override
    public boolean stopService(Intent name) {
        // TODO Auto-generated method stub
		//����timer�A
        timer.cancel();
        AutoRun.cancel();
        FileIO.WriteAPI_Result("========Test Stop========");
        return super.stopService(name);

    }
	public void cancelAM() {

		timer.cancel();
		AutoRun.cancel();
		 Log.i(PathAndFlag.LogcatTAG,"Start cancelAM ");
		stopService(new Intent(this,
				BrowsingServices.class));
		

		}
}



