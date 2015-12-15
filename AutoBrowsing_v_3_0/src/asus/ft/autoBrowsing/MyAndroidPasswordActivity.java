package asus.ft.autoBrowsing;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MyAndroidPasswordActivity extends Activity {
	
	private EditText password;
	private Button btnSubmit;
	private Button btnExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_android_password);
		
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_android_password, menu);
		return true;
	}
	
	public void addListenerOnButton() {

		password = (EditText) findViewById(R.id.txtPassword);
		
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnExit = (Button) findViewById(R.id.btnExit);
		
		//Enter Password
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(password.getText().toString().equals("asusqt5ft")){
					Toast.makeText(getApplicationContext(),"PASSWORD Correct�I�I", Toast.LENGTH_LONG).show();
			        /* new�@��Intent����A�ë��w�n�Ұʪ�class */
			        Intent intent = new Intent();
			    	  intent.setClass(MyAndroidPasswordActivity.this, AutoBrowsingActivity.class);
			    	  
			    	  /* �I�s�@�ӷs��Activity */
			    	  startActivity(intent);
			    	  /* �����쥻��Activity */
			    	  MyAndroidPasswordActivity.this.finish();					
				}else{
					//Toast.makeText(MyAndroidAppActivity.this, password.getText(),Toast.LENGTH_SHORT).show();
					Toast.makeText(MyAndroidPasswordActivity.this,"Wrong�IPlease enter the password again�I", Toast.LENGTH_LONG).show();
					//Toast.makeText(getApplicationContext(), "Password Error,Please Try Again!!", Toast.LENGTH_LONG).show();

				}


			}
			

		});
		
		//Exit
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    	  MyAndroidPasswordActivity.this.finish();
			}
			
		});
		
	}

}
