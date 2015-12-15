package asus.ft.autoBrowsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;









import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Browser;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BrowsingServices extends Service {

	boolean isStop = true;

	// for log
	public File file;
	public File file_web;
	public String logFileName = "/sdcard/Auto_Browser.txt";
	public FileWriter LogFileWriter;
	public FileOutputStream fos;
	public int passCount = 1;
	public static int count = 1;
	MediaPlayer mediaPlayer;
	PowerManager pm;
	PowerManager.WakeLock wl;
	NotificationManager myNotiManager;
	public static boolean toStop = false;
	String path_txtFile_youtube = "/sdcard/youtubeList.txt";
	String path_txtFile_1 = "/sdcard/weburl_list.txt";
	String path_txtFile_Youku= "/sdcard/youkuList.txt";
	int tmp;
	String webaddress = "";
	String[] tw_web = new String[300];

	/*
	 * String[] tw_web = { "http://www.live.com", "http://www.yahoo.com",
	 * "http://www.cnn.com/", "http://www.bbc.co.uk/", "http://www.msn.com/",
	 * "http://www.youku.com/", "http://www.163.com/", "http://www.sohu.com/",
	 * "http://www.tudou.com/", "http://www.dailymotion.com/",
	 * "http://www.ku6.com/", "http://taobao.com/", "http://tw.bid.yahoo.com/",
	 * "http://buy.yahoo.com.tw/", "http://shopping.pchome.com.tw/",
	 * "http://www.amazon.com/", "http://www.amazon.co.jp/",
	 * "http://www.ebay.com/", "http://www.wikipedia.org/",
	 * "http://www.microsoft.com/", "http://www.adobe.com/",
	 * "http://windows.microsoft.com/", "http://www.hao123.com/",
	 * "http://www.4399.com/", "http://www.apple.com/", "http://briian.com/",
	 * "http://www.techbang.com.tw/", "http://www.books.com.tw/",
	 * "http://www.wretch.cc/", "http://www.pixnet.net/", "http://www.yam.com/",
	 * "http://xuite.net/", "http://udn.com/", "http://www.mobile01.com/",
	 * "http://ruten.com.tw/" };
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate();

	}

	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(intent.getExtras()!=null)
		{
		  Bundle bundle = intent.getExtras(); 
		}
		
		Log.i("info", "onStart");
		myNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		pm = (PowerManager) getSystemService(BrowsingServices.POWER_SERVICE);
		// wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "asus.ft");
		wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP, this.getClass()
				.getCanonicalName());
		// toStop = false;
		if (AutoBrowsingActivity.youtubeFlag) {
			file_web = new File(path_txtFile_youtube);
		} 
		else if(AutoBrowsingActivity.YouKuFlag)
		{
			file_web = new File(path_txtFile_Youku);
		}
		else {
			file_web = new File(path_txtFile_1);
		}
		Log.i(PathAndFlag.LogcatTAG, "Youtube Play= "
				+ AutoBrowsingActivity.youtubeFlag);
		Log.i(PathAndFlag.LogcatTAG, "YouKu Play= "
				+ AutoBrowsingActivity.YouKuFlag);
		StringBuilder text = new StringBuilder();
		try {

			BufferedReader br = new BufferedReader(new FileReader(file_web));
			String line;

			int i = 0;
			Log.d("line= ", br.readLine());
			while ((line = br.readLine()) != null) {
				String[] PackageName = line.split(",");

				tw_web[i] = PackageName[0];
				text.append(line);
				text.append("\n");
				i++;
			}
			br.close();
		} catch (IOException e) {

		}

		// toStop = checkInternetConnection();
		   
		if (!toStop) {
			wl.acquire();
			setNotiType("瀏覽第" + count + "次");
			String Result="瀏覽第" + count + "次: Pass";
			tmp = (int) ((Math.random() * 21));
			webaddress = tw_web[tmp];
			Bundle bundle_WebView= new Bundle();
			try
			{
				
			Log.i("info", "Open Web uri " + webaddress);
			Intent intent_b = new Intent(Intent.ACTION_VIEW,
					Uri.parse(webaddress.toString()));
			//intent_b.setDataAndType(Uri.parse(webaddress.toString()), "vnd.com.youku.phone");
			//intent_b.putExtra(Browser.EXTRA_CREATE_NEW_TAB,false);
			//intent_b.setDataAndType(Uri.parse(webaddress.toString()), "application");
			//intent_b.setDataAndType(Uri.parse(webaddress.toString()), "text/v.swf");
			intent_b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent_b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent_b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			bundle_WebView.putString("loadUrl", webaddress.toString());
			intent_b.putExtras(bundle_WebView);
			if (AutoBrowsingActivity.youtubeFlag) {
				//因youtube 網址為vnd.youtube://videoid 會直接由youtube開啟 
				// intent_b.setClassName("com.google.android.youtube","com.google.android.apps.youtube.app.WatchWhileActivity");
			}
			else if(AutoBrowsingActivity.YouKuFlag)
			{
				
				/*if(count >1)
				{
					Webview_stream.instance.finish();
				
					Log.i(PathAndFlag.LogcatTAG, "  Webview_stream.instance is =" +  Webview_stream.instance.toString()); 
				}*/
				intent_b.setClass(this, Webview_stream.class);
				//intent_b.setPackage("com.youku.phone");
			}
			else {
				/*intent_b.setClassName("com.android.chrome",
						"com.google.android.apps.chrome.Main");*/
				
				intent_b.setPackage("com.asus.browser");
			}
			

			if (AutoBrowsingActivity.MainServiceContext != null) {
				AutoBrowsingActivity.MainServiceContext.startActivity(intent_b);

			} else {
				startActivity(intent_b);
			}
			addLog("Open website : " + webaddress);
			Log.i("info", webaddress);
			count++;
			passCount++;

			SystemClock.sleep(8000);

			wl.release();
			
			FileIO.WriteAPI_Result(Result);
			}
			catch(Exception e)
			{
				String ResultFail="瀏覽第" + count + "次: Fail. Exception: "+e;
				FileIO.WriteAPI_Result(ResultFail);
				
			}
		} else {

			count++;
		}

		// this.onDestroy();

	}

	public void onDestroy() {
		myNotiManager.cancelAll();
		super.onDestroy();
	}

	// for log
	// create log folder
	public boolean createLogFolder(String folderName) {
		try {
			File logFolder = new File(folderName);
			if (!logFolder.exists()) {
				if (!logFolder.mkdirs())
					;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(getApplicationContext(), e.toString(), 1).show();
			return false;
		}
		return true;
	}

	public void initialLog() {
		// append mode
		try {
			if (LogFileWriter != null) {
				LogFileWriter.close();
				LogFileWriter = null;
			}
			LogFileWriter = new FileWriter(logFileName, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addLog(String content) {
		String date = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
				.format(new Date());
		WriteFile(date + "\t" + content + "\r\n");
	}

	// write log 1.initial FileWriter
	public synchronized void WriteFile(String content) {
		try {
			if (LogFileWriter == null)
				initialLog();
			LogFileWriter.write(content);
			LogFileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setNotiType(String text) {

		PendingIntent contentIntent = PendingIntent.getService(this, 0,
				new Intent(this, BrowsingServices.class), 0);

		Notification myNoti = new Notification();
		myNoti.icon = R.drawable.ic_launcher;
		myNoti.tickerText = text;
		Log.i("info", contentIntent.getTargetPackage().toString());
		Log.i("info", text);
		myNoti.setLatestEventInfo(BrowsingServices.this, "Auto Browsing", text,
				contentIntent);
		myNotiManager.notify(0, myNoti);
	}

	public boolean checkInternetConnection() {
		boolean r = false;
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		// System.out.println("**************" + ni.getState().toString());

		if (ni != null && ni.isConnected()) {
			// System.out.println("ni.isConnected() = " + ni.isConnected());
			// return ni.isConnected();
			r = false;
		} else {
			// System.out.println("ni.isConnected() = "+ni.isConnected());
			// return false;
			r = true;
		}
		Log.d("Connection info", ni.toString());
		Log.d("isConnected?", String.valueOf(ni.isConnected()));
		return r;
	}
}
