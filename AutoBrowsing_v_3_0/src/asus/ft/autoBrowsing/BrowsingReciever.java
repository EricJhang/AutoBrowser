package asus.ft.autoBrowsing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BrowsingReciever extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		Log.i(PathAndFlag.LogcatTAG, "go to auto browsing reciever");
		Intent i = new Intent(context, BrowsingServices.class);
		Bundle bb = new Bundle();
		bb.putString("STR_CALLER", "");
		i.putExtras(bb);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);
		
	}

}
