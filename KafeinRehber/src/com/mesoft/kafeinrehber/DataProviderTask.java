package com.mesoft.kafeinrehber;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.SharedPreferences;
import android.os.AsyncTask;

public class DataProviderTask extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

//	private static List<Contact> allContacts = new ArrayList<Contact>();
//
//	public SharedPreferences settings;
//
//	public static final String FILE_NAME = "KafeinContacts";
//
//	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//	public MainActivity activity;
//
//	public static final String PREFS_URL = "http://stackoverflow.com/questions/18491316/updating-listview-adapter-from-asynctask";
//
//	public void setData(String data) {
//		String[] cs = data.split("-");
//		for (int i = 0; i < cs.length; i++) {
//			String cont = cs[i];
//			String[] cont2 = cont.split("_");
//			Contact c = new Contact();
//			c.setName(cont2[0]);
//			c.setNumber(cont2[1]);
//			allContacts.add(c);
//		}
//
//	}
//
//	public void init(MainActivity act) {
//
//		activity = act;
//		allContacts = new ArrayList<Contact>();
//
//		if (allContacts == null || allContacts.size() == 0) {
//			execute(PREFS_URL);
//		}
//
//		// adapter.notifyDataSetChanged();
//	}
//
//	public static List<Contact> getAllContacts() {
//
//		return allContacts;
//	}
//
//	public static String getAllContactsAsString() {
//
//		String v = "";
//		for (int i = 0; i < allContacts.size(); i++) {
//			Contact c = allContacts.get(i);
//			v = v + c.getName() + "_" + c.getNumber() + "-";
//		}
//
//		return v;
//	}
//
//	public static void setAllContacts(List<Contact> allContacts) {
//		DataProviderTask.allContacts = allContacts;
//	}
//
//	@Override
//	protected String doInBackground(String... uri) {
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpResponse response;
//		String responseString = null;
//		try {
//			response = httpclient.execute(new HttpGet(uri[0]));
//			StatusLine statusLine = response.getStatusLine();
//			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				response.getEntity().writeTo(out);
//				out.close();
//				responseString = out.toString();
//
//				allContacts = new ArrayList<Contact>();
//
//				Contact c = new Contact();
//				c.setName("Mehmet Ersin Bitirgen");
//				c.setNumber("905425615114");
//
//				Contact c2 = new Contact();
//				c2.setName("ALi Çelik2");
//				c2.setNumber("90542615114");
//
//				allContacts.add(c);
//				allContacts.add(c2);
//
//				activity.dataAdapter.clear();
//				activity.dataAdapter.addAll(getAllContacts());
//				activity.dataAdapter.notifyDataSetChanged();
//
//			} else {
//				response.getEntity().getContent().close();
//				throw new IOException(statusLine.getReasonPhrase());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return responseString;
//	}
//
//	protected void onPostExecute(MainActivity Params) {
//
//		activity.dataAdapter.clear();
//		activity.dataAdapter.addAll(getAllContacts());
//		activity.dataAdapter.notifyDataSetChanged();
//	}

}