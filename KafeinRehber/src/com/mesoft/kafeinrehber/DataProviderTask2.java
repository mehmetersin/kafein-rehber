package com.mesoft.kafeinrehber;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.SharedPreferences;

public class DataProviderTask2 {

	private static List<Contact> allContacts = new ArrayList<Contact>();

	public SharedPreferences settings;

	public static final String FILE_NAME = "/sdcard/kafeincont_file.txt";

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public MainActivity activity;

	public static final String PREFS_URL = "http://www.yaklaskazan.com/krs?cmd=all";

	public static void setData(String data) {

		try {
			allContacts = new ArrayList<Contact>();

			String[] cs = data.split("\\|");
			for (int i = 0; i < cs.length; i++) {
				String cont = cs[i];
				String[] cont2 = cont.split("_");
				Contact c = new Contact();
				c.setName(cont2[0]);
				c.setNumber(cont2[1]);
				allContacts.add(c);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void init() {

		allContacts = new ArrayList<Contact>();

		if (allContacts == null || allContacts.size() == 0) {
			allContacts = new ArrayList<Contact>();
			execute(PREFS_URL);
		}

		// adapter.notifyDataSetChanged();
	}

	public static List<Contact> getAllContacts() {

		return allContacts;
	}

	public static String getAllContactsAsString() {

		String v = "";
		for (int i = 0; i < allContacts.size(); i++) {
			Contact c = allContacts.get(i);
			v = v + c.getName() + "_" + c.getNumber() + "|";
		}

		return v;
	}

	public static void setAllContacts(List<Contact> allContacts) {
		DataProviderTask2.allContacts = allContacts;
	}

	protected String execute(String... uri) {

		HttpResponse response;
		String responseString = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 6000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 8000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
			response = httpclient.execute(new HttpGet(uri[0]));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();

				setData(responseString);

			} else {
				response.getEntity().getContent().close();
				throw new Exception(statusLine.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Contact c = new Contact();
			c.setName("Bir Hata Oldu");
			c.setNumber("Lütfen Başka Zaman Tekrar Deneyin");
			allContacts.add(c);
		}
		return responseString;
	}

	protected void onPostExecute(MainActivity Params) {

		activity.dataAdapter.clear();
		activity.dataAdapter.addAll(getAllContacts());
		activity.dataAdapter.notifyDataSetChanged();
	}

}