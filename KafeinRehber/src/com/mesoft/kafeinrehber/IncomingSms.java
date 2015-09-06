package com.mesoft.kafeinrehber;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();

	public void onReceive(Context context, Intent intent) {

		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage
							.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;

					String message = currentMessage.getDisplayMessageBody();

					if (senderNum.contains(PrefActivity.verifDestNumner)) {
						if (PrefActivity.verifCode.equals(message)) {

							for (int j = 0; j < DataProviderTask2
									.getAllContacts().size(); j++) {
								Contact c = DataProviderTask2.getAllContacts()
										.get(j);
								String number = c.getNumber();
								number = number.replaceAll(" ", "");
								if (number.contains("5425615114")){
									number = number;
								}
								if (senderNum.contains(number)) {
									PrefActivity.verified = true;
									int duration = Toast.LENGTH_LONG;
									Toast toast = Toast
											.makeText(context,
													"Verification Sucessfull",
													duration);
									toast.show();
								}
							}

						}
					} else {
						int duration = Toast.LENGTH_LONG;
						Toast toast = Toast
								.makeText(context, "senderNum: " + senderNum
										+ ", message: " + message, duration);
						toast.show();
					}

				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			// Log.e("SmsReceiver", "Exception smsReceiver" +e);

		}
	}
}