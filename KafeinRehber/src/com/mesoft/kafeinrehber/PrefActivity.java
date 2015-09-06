package com.mesoft.kafeinrehber;

import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PrefActivity extends Activity {

	private BroadcastReceiver mReceiver;

	public static String verifDestNumner;
	public static String verifCode;
	public static boolean verified = false;

	private void startingUp() {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		Thread timer = new Thread() { // new thread
			public void run() {
				try {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							DataProviderTask2 dp = new DataProviderTask2();
							dp.init();

						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			};
		};
		timer.start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		startingUp();

		setContentView(R.layout.pref_screen);

		final Button button = (Button) findViewById(R.id.verifyButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String destNo = ((EditText) findViewById(R.id.verifyNumber))
						.getText().toString();
				sendVerifyNumber(destNo);

				((EditText) findViewById(R.id.verifyNumber))
						.setText("Waiting ...");

				Runnable runnable = new Runnable() {
					public void run() {

						try {
							Thread.sleep(60000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (verified) {

							SharedPreferences settings = getSharedPreferences(
									"UserInfo", 0);
							SharedPreferences.Editor editor = settings.edit();
							editor.putString("Number", verifDestNumner);
							editor.putString("Status", "true");
							editor.commit();

							// ((EditText) findViewById(R.id.verifyNumber))
							// .setText("Successfully Verified");

//							Intent intent = new Intent(PrefActivity.this,
//									MainActivity.class);
//							startActivity(intent);

						} else {

//							Intent intent = new Intent(PrefActivity.this,
//									MainActivity.class);
//							startActivity(intent);

							// ((EditText) findViewById(R.id.verifyNumber))
							// .setText("Kafein Rehber Verification Failed !");
						}

					}
				};
				Thread mythread = new Thread(runnable);
				mythread.start();

			}
		});

	}

	protected void onResume() {

		super.onResume();

	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// unregister our receiver
		this.unregisterReceiver(this.mReceiver);
	}

	public void sendVerifyNumber(String destinationNumber) {
		try {

			Random rnd = new Random();
			int randno = rnd.nextInt(10000);

			verifDestNumner = destinationNumber;

			verifCode = String.valueOf(randno);

			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(destinationNumber, null, verifCode,
					null, null);
			Toast.makeText(getApplicationContext(), "Verification SMS Sent!",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
	}

}
