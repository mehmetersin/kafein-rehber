package com.mesoft.kafeinrehber;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ArrayAdapter<Contact> dataAdapter = null;

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public ListView listView = null;
	public DataProviderTask2 dp = null;

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_bookmark:
			// Single menu item is selected do something
			// Ex: launching new activity/screen or show alert message
			Toast.makeText(MainActivity.this, "Bookmark is Selected",
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.menu_preferences:
			Intent intent = new Intent(this, PrefActivity.class);
			startActivity(intent);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences settings = getSharedPreferences("UserInfo", 0);
		String number = settings.getString("Number", "").toString();
		String status = settings.getString("Status", "").toString();

		if (!status.equals("true")) {
			Intent intent = new Intent(this, PrefActivity.class);
			startActivity(intent);
			return;
		}

		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView1);

		displayListView();

		startingUp();

	}

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

							if (dp == null) {

								if (readFromCache()) {

								} else {

									EditText myFilter = (EditText) findViewById(R.id.msisdn);
									myFilter.setHint("Ara");

									dp = new DataProviderTask2();
									dp.init();
									writeFile();

								}

								dataAdapter.clear();
								dataAdapter.addAll(DataProviderTask2
										.getAllContacts());
								dataAdapter.notifyDataSetChanged();

							}

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

	private boolean readFromCache() {

		String UTF8 = "utf8";
		int BUFFER_SIZE = 8192;
		String result = "";

		try {

			File file = new File(DataProviderTask2.FILE_NAME);
			Date lastModDate = new Date(file.lastModified());
			Date n = new Date();
			n.setHours(n.getHours() - 23);
			if (lastModDate.before(n)) {
				return false;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(DataProviderTask2.FILE_NAME), UTF8),
					BUFFER_SIZE);

			BufferedReader reader = new BufferedReader(br);
			String str;
			while ((str = reader.readLine()) != null) {
				result = result + str;
			}

			if (result == null || result.equals("") || result == "") {
				return false;
			} else {

				DataProviderTask2.setData(result);

				if (DataProviderTask2.getAllContacts().size() <= 1) {
					return false;
				}

				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void writeFile() {
		FileOutputStream outputStream;

		String UTF8 = "utf8";
		int BUFFER_SIZE = 8192;
		try {

			File myFile = new File(Environment.getExternalStorageDirectory(),
					DataProviderTask2.FILE_NAME);
			if (!myFile.exists()) {
				myFile.mkdirs();
				myFile.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(DataProviderTask2.FILE_NAME), UTF8),
					BUFFER_SIZE);
			bw.write(DataProviderTask2.getAllContactsAsString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void displayListView() {

		// Array list of countries

		// create an ArrayAdaptar from the String Array
		dataAdapter = new ArrayAdapter<Contact>(this, R.layout.country_list,
				DataProviderTask2.getAllContacts());

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		// enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				String c = (String) ((TextView) view).getText();
				String number = c.substring(c.indexOf("(") + 1, c.length() - 1);

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + number));
				startActivity(callIntent);

				Toast.makeText(getApplicationContext(), number,
						Toast.LENGTH_SHORT).show();
			}
		});

		EditText myFilter = (EditText) findViewById(R.id.msisdn);
		myFilter.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				dataAdapter.getFilter().filter(s.toString());
			}
		});
	}
}
