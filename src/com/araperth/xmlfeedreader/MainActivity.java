package com.araperth.xmlfeedreader;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.araperth.xmlfeedreader.XmlFeedParser.Entry;
import com.araperth.xmlfeedreader.R;


public class MainActivity extends Activity implements OnItemClickListener {

	private ListView listView;
	// Given URL
	private static final String URL = "http://blog.solstice-mobile.com/feeds/posts/default";
	private ArrayList<com.araperth.xmlfeedreader.XmlFeedParser.Entry> arrayList;
	private GetXml getXml;
	// List Adapter.
	FeedListAdapter feedListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitymain);
		listView = (ListView) findViewById(R.id.listView);
		arrayList = new ArrayList<com.araperth.xmlfeedreader.XmlFeedParser.Entry>();
	}
	
	// check the network activity.

	private void networkActivity() {

		ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);// Connectivity
		NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			
			try {
				
				getXml = new GetXml();
				// execute the given URL and makes it a background task which 
				// implements async task.
				arrayList = getXml.execute(URL).get();
				
				feedListAdapter = new FeedListAdapter(MainActivity.this,
						R.layout.rsslistview, arrayList);
				// adding array adapter to the listview.
				listView.setAdapter(feedListAdapter);
				// Event Listener for the list item.
				listView.setOnItemClickListener(this);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			
			// to show connectivity errors.
			
			DialogBox alert = new DialogBox();
			alert.showAlertDialog(MainActivity.this, "Network Connectivity", "Please, check internet connection.", false);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// Creates a new intent for every list item in the array adapter 
		//when clicked on list item, which is a webview. 
		Intent intent = new Intent(this, DetailedListView.class);
		intent.putExtra("url", arrayList.get(arg2).link);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		networkActivity();
	}
	
	// Asynchronous task to download Xml data.
	
	class GetXml extends AsyncTask<String, Void, ArrayList<Entry>> {

		public LoadXmlData loadxmlNetWork;
		
		@Override
		protected ArrayList<Entry> doInBackground(String... urls) {
			try {
				loadxmlNetWork =new LoadXmlData();
				return loadxmlNetWork.loadXml(urls[0],MainActivity.this);
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null; 
			 
		}
		
	}
}
