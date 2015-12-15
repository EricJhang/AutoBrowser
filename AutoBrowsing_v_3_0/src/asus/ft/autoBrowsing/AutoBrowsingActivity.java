package asus.ft.autoBrowsing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AutoBrowsingActivity extends Activity {

	/*
	 * UI
	 */
	EditText et_runtime;
	EditText et_duration1;
	EditText et_duration2;
	Button button_start, button_stop;
	private RadioButton radioButton_youtube, radioButton_Browser,radioButton_com_cibn_tv;
	/*
	 * for log
	 */
	public File file_set;
	public String logFileName_set;
	public FileWriter LogFileWriter_set;
	public FileOutputStream fos_set;
	public static Context MainServiceContext;
	public File file;
	public String logFileName;
	public FileWriter LogFileWriter;
	public FileOutputStream fos;
	Thread tMonitor;
	public Handler messageHandler = new Handler();
	Message message;

	public static boolean youtubeFlag = false;
	public static boolean YouKuFlag = false;
	public static int runtime = 1;
	int duration1 = 1;
	int duration2 = 2;
	boolean toStop = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_browsing);
		radioButton_youtube = (RadioButton) findViewById(R.id.radioButton_youtube);
		radioButton_Browser = (RadioButton) findViewById(R.id.radioButton_Browser);
		radioButton_com_cibn_tv= (RadioButton) findViewById(R.id.radioButton_com_cibn_tv);
		et_runtime = (EditText) findViewById(R.id.runtime);
		et_duration1 = (EditText) findViewById(R.id.duration1);
		et_duration2 = (EditText) findViewById(R.id.duration2);
		button_start = (Button) findViewById(R.id.button_start);
		button_stop = (Button) findViewById(R.id.button_stop);

		radioButton_Browser.setChecked(true);
		radioButton_Browser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				youtubeFlag = false;
				YouKuFlag=false;
			}
		});
		radioButton_youtube
		.setOnClickListener(new OnClickListener() {
					@Override
					public void  onClick(View v) {
						// TODO Auto-generated method stub
						youtubeFlag = true;
						YouKuFlag=false;
					}
				});
		radioButton_com_cibn_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void  onClick(View v) {
				// TODO Auto-generated method stub
				youtubeFlag = false;
				YouKuFlag=true;
			}
		});
		button_start.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				FileIO.WriteAPI_Result("========Auto Test Start========");
				Bundle bundle_var = new Bundle();
				BrowsingServices.toStop = false;
				button_start.setEnabled(false);
				radioButton_youtube.setEnabled(false);
				radioButton_Browser.setEnabled(false);
				radioButton_com_cibn_tv.setEnabled(false);
				if (!et_runtime.getText().toString().equals("")) {
					runtime = Integer.parseInt(et_runtime.getText().toString()
							.trim());
				} else {
					runtime = 0;
					Toast.makeText(getApplicationContext(), "執行次數有問題！",
							Toast.LENGTH_LONG).show();
					toStop = true;
				}

				if (!et_duration1.getText().toString().equals("")) {
					duration1 = Integer.parseInt(et_duration1.getText()
							.toString().trim());
				} else {
					duration1 = 0;
					Toast.makeText(getApplicationContext(), "執行間隔有問題！",
							Toast.LENGTH_LONG).show();
					toStop = true;
				}

				if (!et_duration2.getText().toString().equals("")) {
					duration2 = Integer.parseInt(et_duration2.getText()
							.toString().trim());
				} else {
					duration2 = 0;
					Toast.makeText(getApplicationContext(), "執行間隔有問題！",
							Toast.LENGTH_LONG).show();
					toStop = true;
				}
				MainServiceContext = getApplicationContext();
				if (!toStop) {
					if (createLogFolder("/sdcard/AutomaticTesting/AutoBrowsing/")) {
						logFileName = "/sdcard/AutomaticTesting/AutoBrowsing/"
								+ "AutoBrowsing"
								+ new SimpleDateFormat("yyyyMMdd_HHmmss")
										.format(new Date()) + ".txt";
						file = new File(logFileName);
						initialLog();
						addLog("New Test");
						addLog("Run Times: " + runtime);
						addLog("Time between calls: " + duration1 + " ~ "
								+ duration2 + "\n");

						try {
							LogFileWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					// 設定Alarm Manager
					bundle_var.putInt("runtime", runtime);
					bundle_var.putInt("duration1", duration1);
					bundle_var.putInt("duration2", duration2);
 
					Intent intent_StartService = new Intent(
							AutoBrowsingActivity.this, StartService.class);
					intent_StartService.putExtras(bundle_var);
					startService(intent_StartService);
					String runtime_Result="runtime=: "+Integer.toString(runtime);
					String duration1_Result="duration1=: "+Integer.toString(duration1);
					String duration2_Result="duration1=: "+Integer.toString(duration2);
					FileIO.WriteAPI_Result(runtime_Result);
					FileIO.WriteAPI_Result(duration1_Result);
					FileIO.WriteAPI_Result(duration2_Result);
					
					if (createLogFolder_set("/sdcard/AutomaticTesting/AutoBrowsing/")) {
						logFileName_set = "/sdcard/AutomaticTesting/AutoBrowsing/_AutoBrowsing_DATA.txt";
						file_set = new File(logFileName_set);
						initialLog_set();
						addLog_set(logFileName);
						try {
							LogFileWriter_set.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		});
		button_stop.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				BrowsingServices.toStop = true;
				UpdateUI(1);
				Intent intent_StartService = new Intent(
						AutoBrowsingActivity.this, StartService.class);
				 stopService(intent_StartService);
			}
		});
		messageHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				// Log.i(PathAndFlag.Tag,"進入handleMessage");
				switch (msg.what) {
				case 1:
					button_start.setEnabled(true);
					radioButton_youtube.setEnabled(true);
					radioButton_Browser.setEnabled(true);
					radioButton_com_cibn_tv.setEnabled(true);
					break;
				case 2:

				default:
					break;
				}
			}
		};
	}

	public void stopBrowsing(View v) {
		//cancelAM();
	}

	public void cancelAM() {
		stopService(new Intent(AutoBrowsingActivity.this,
				BrowsingServices.class));
		Intent intent = new Intent(AutoBrowsingActivity.this,
				BrowsingReciever.class);
		AlarmManager AM = (AlarmManager) getSystemService(ALARM_SERVICE);
		for (int i = 0; i < runtime; i++) {
			PendingIntent sender = PendingIntent.getBroadcast(
					AutoBrowsingActivity.this, i, intent, 0);
			AM.cancel(sender);
		}
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

	// for log_set
	// create log folder
	public boolean createLogFolder_set(String folderName) {
		try {
			File logFolder_set = new File(folderName);
			if (!logFolder_set.exists()) {
				if (!logFolder_set.mkdirs())
					;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(getApplicationContext(), e.toString(), 1).show();
			return false;
		}
		return true;
	}

	public void initialLog_set() {
		// append mode
		try {
			if (LogFileWriter_set != null) {
				LogFileWriter_set.close();
				LogFileWriter_set = null;
			}
			LogFileWriter_set = new FileWriter(logFileName_set, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addLog_set(String content) {
		WriteFile_set(content + ";");
	}

	// write log 1.initial FileWriter
	public synchronized void WriteFile_set(String content) {
		try {
			if (LogFileWriter_set == null)
				initialLog_set();
			LogFileWriter_set.write(content);
			LogFileWriter_set.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void UpdateUI(int event) {
		message = new Message();
		message.what = event;

		AutoBrowsingActivity.this.messageHandler.sendMessage(message);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		tMonitor = new Thread(new MemoryInfoMonitor());
		tMonitor.start();
		Log.i(PathAndFlag.LogcatTAG, "Resume Start ");
		Log.i(PathAndFlag.LogcatTAG, "runtime= " + runtime);
		Log.i(PathAndFlag.LogcatTAG, "count= " + BrowsingServices.count);

	}

	class MemoryInfoMonitor implements Runnable {

		Message message;

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				Log.i(PathAndFlag.LogcatTAG, "MemoryInfoMonitor Start ");
				if (runtime <= BrowsingServices.count) {
					UpdateUI(1);
					BrowsingServices.count = 1;

				}
				Thread.sleep(1000);
			} catch (Exception ee) {
				Log.e(PathAndFlag.LogcatTAG,
						"Fillup MemoryInfoMonitorthread Error : "
								+ ee.getMessage());
			}
		}

	}
}
