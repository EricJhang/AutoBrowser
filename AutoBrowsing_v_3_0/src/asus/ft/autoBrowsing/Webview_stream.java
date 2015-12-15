package asus.ft.autoBrowsing;

import java.io.IOException;














import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Webview_stream extends Activity{
	public static Context Webview_context;
	private WebView webView_1=null;
    Context Webview_stream_context;
	Bundle _bundle_webview;
	public boolean GETHTML5_COMPLETE=false;
	public String realPlayUrl="";
	int[] count=new int[256];
	Thread VideoPlay;
	public static Webview_stream instance = null; 
	String ua = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36";
	//Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36
	//String ua1= "Mozilla/5.0 (iPhone; CPU iPhone OS 7_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D5145e Safari/9537.53";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_2);
		instance=this;
	     _bundle_webview =this.getIntent().getExtras();
	     DisplayMetrics monitorsize =new DisplayMetrics();
         getWindowManager().getDefaultDisplay().getMetrics(monitorsize);
	     webView_1 =(WebView) findViewById(R.id.webView_1);
	  
		WebSettings setting = webView_1.getSettings();
		 getWindow().setFlags(0x1000000, 0x1000000);
		setting.setJavaScriptEnabled(true);
		setting.setDomStorageEnabled(true);
		setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setDatabaseEnabled(true);
		setting.setDefaultTextEncodingName("UTF-8");
		setting.setUseWideViewPort(true);
		setting.setAllowFileAccess(true);
		setting.setAppCacheEnabled(true);
		setting.setLoadWithOverviewMode(true);
		
	    setting.setUseWideViewPort(true);
		setting.setCacheMode(WebSettings.LOAD_DEFAULT);
		setting.setPluginsEnabled(true);
		setting.setBuiltInZoomControls(true);
		setting.setSupportZoom(true);
		//webView_1.addJavascriptInterface(new DemoJavaScriptInterface(),"HTMLOUT");
	 class InJavaScriptLocalObj {
		
	        public void showSource(String GetId) {  
	            /*if (html5url != null && !GETHTML5_COMPLETE) {  
	                GETHTML5_COMPLETE = true;  
	                realPlayUrl = html5url;  
	                
	            } */ 
	        	
	          //  Log.i("conowen", "html5url=" + html5url);  
	            Log.i(PathAndFlag.LogcatTAG, " GetId=" +  GetId); 
	          
	        } 
	    } 
		setting.setPluginState(WebSettings.PluginState.ON);
		
	//	webView_1.addJavascriptInterface(new InJavaScriptLocalObj(), "js_method");
		//webView_1.setWebViewClient(mWebViewClient);
		setting.setUserAgentString(ua);
		
		webView_1.setWebChromeClient(new WebChromeClient());
		webView_1.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onPageFinished(WebView view, String url) { 
				super.onPageFinished(view, url);
				//view.loadUrl("javascript:(alert(navigator.userAgent))()"); 
				
					//SystemClock.sleep(10000);
					
						/*try{
							 Runtime.getRuntime().exec(new String[]{"input", "tap", "293 241"});
							 Log.i("PathAndFlag.LogcatTAG", " input, tap, 293 241" ); 
						 }
				      catch(IOException e){
				   	   Log.i("PathAndFlag.LogcatTAG", " IOException=" +  e.getMessage()); 
					          
				      }*/
					
				 //view.loadUrl("javascript:(function() { document.getElementsByTagName('player_html5').play})()"); 
			//	 view.loadUrl("javascript:(window.js_method.showSource( document.getElementById('movie_player')))"); 
				 //view.loadUrl("javascript:window.js_method.showSource(document.getElementsByTagName('video')[0].src);");
			 }
			 @Override
			 public boolean shouldOverrideUrlLoading(WebView view, String url) {
				
				  view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
				 // view.loadUrl("javascript:(function(){document.getElementsByTagName('isAOUTD).innerHTML = \"true\"})();"); 
				  view.loadUrl(url);
			
				  
			   return true;
			  }
		});
		
		String url= _bundle_webview.getString("loadUrl");
		String s = "<html><head><meta charset=\"utf-8\" /><title>swf</title></head><body bgcolor=\"#000000\">"
				   + "<embed src=\" "+url + "\"  "
				   + "  width=\"100%\" height=\"100%\" align=\"middle\" allowScriptAccess=\"always\""
				   + " allowFullScreen=\"true\" wmode=\"transparent\" "
				   + "type=\"application/x-shockwave-flash\"> </embed></body></html>";
		
		String s1 ="<html><body> <iframe height=100% width=100% src=\""+url+ "\" frameborder=0 allowfullscreen=\"true\"  ></iframe></body></html>";
		String S2="<video width=device-width height=device-height controls=\"controls\" poster=\"video.jpg\">" 
         + " <iframe height=600 width=800 src=\""+url+ "\" frameborder=0 allowfullscreen></iframe> ></video>"; 
		String s3 ="<embed src=\"http://player.youku.com/player.php/Type/Folder/Fid/23800841/Ob/1/sid/XMTMwOTYyNzgyOA==/v.swf\" quality=\"high\" width=\"480\" height=\"400\" align=\"middle\" allowScriptAccess=\"always\" allowFullScreen=\"true\" mode=\"transparent\" type=\"application/x-shockwave-flash\"></embed>";

		Log.i(PathAndFlag.LogcatTAG, "Open Webview URL " + url);
		
	
		webView_1.loadData(s1, "text/html; charset=UTF-8", null);
		VideoPlay = new Thread(new VideoPlayClick());
		VideoPlay.start();
		
		
		// MediaController mc = new MediaController(this);  
		 //videoView1.setMediaController(mc);  
		// videoView1.setVideoURI(Uri.parse(s));
		// videoView1.start(); 
		
	//webView_1.loadUrl(s1);
	
	}
	/*final class DemoJavaScriptInterface {
		   public void showHTML(String html) {
		         new AlertDialog.Builder(AppCt).setTitle("HTML codes")
		         .setMessage(html)
		         .setPositiveButton(android.R.string.ok, null)
		         .setCancelable(false).create().show();
		   }
		}*/
	/* WebViewClient mWebViewClient = new WebViewClient() {
		  @Override
		  public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
			  view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		     
			  //view.loadUrl(url);
		   return true;
		  }
	 };*/
	class VideoPlayClick implements Runnable {

		Message message;
		@Override
		public void run() {
			// TODO Auto-generated method stub
	        try
	        {
	        
			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			boolean stop = false;
			Log.i("PathAndFlag.LogcatTAG", " Video now isMusicActive= " +stop);
			int Count=0;
			while(Count <= 10)
			{	
			try
			{
				 Runtime.getRuntime().exec(new String[]{"input", "tap", "293 241"});
				 Log.i(PathAndFlag.LogcatTAG, " input, tap, 293 241" ); 
				 Log.i(PathAndFlag.LogcatTAG, " webView_1.isShown()= " +webView_1.isShown());
			}
			catch(Exception e)
			{
				Log.i(PathAndFlag.LogcatTAG, "Input tap  Exception=" +  e.getMessage()); 
		          
			}
			Thread.sleep(3000);
			Count=Count+1;
			}
			/*boolean Flag=false;
			while(!Flag)
			{
				if (stop) 
				{
				System.out.println("true");
				Log.i(PathAndFlag.LogcatTAG, " Video play is true" );
				Log.i(PathAndFlag.LogcatTAG, " webView_1.isShown()= " +webView_1.isShown());
				Flag=true;
				break;
				} 
				else 
				{
				System.out.println("false");
				Log.i(PathAndFlag.LogcatTAG, " Video is not  play" );
				try
				{
					 Runtime.getRuntime().exec(new String[]{"input", "tap", "293 241"});
					 Log.i(PathAndFlag.LogcatTAG, " input, tap, 293 241" ); 
					 Log.i(PathAndFlag.LogcatTAG, " webView_1.isShown()= " +webView_1.isShown());
				}
				catch(Exception e)
				{
					Log.i(PathAndFlag.LogcatTAG, "Input tap  Exception=" +  e.getMessage()); 
			          
				}
				}
				Thread.sleep(5000);
				stop = am.isMusicActive();
			}*/
		
	        }
	        catch(Exception ee)
	        {
	        	Log.i(PathAndFlag.LogcatTAG, " Threa Exception=" +  ee.getMessage()); 
	        }
	        
		}
		
	
	}
	@Override
	public void onPause()
    {
        super.onPause();
      
        Log.i(PathAndFlag.LogcatTAG,"onPause()");
       
    }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
		//this.finish();
		Log.i(PathAndFlag.LogcatTAG,"onDestroy()");
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
	
		super.onStop();
	
		//this.finish();
		Log.i(PathAndFlag.LogcatTAG,"onStop()");
	}
}
