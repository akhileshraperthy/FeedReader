package com.araperth.xmlfeedreader;

import com.araperth.xmlfeedreader.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
// This View Displays the detailed information when 
// the user clicks on the particular interest. 
public class DetailedListView extends Activity {
	WebView web;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.listdetailview);
		
		web = (WebView) findViewById(R.id.webview);
		web.setWebViewClient(new WebViewClient() {
			
			// on page load.
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			// on page load finish.

			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			// This method called when there is error while displaying.

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(DetailedListView.this,
						"The detail description doesn't available.", Toast.LENGTH_LONG).show();
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		// Retrieving url from the rss list view and display in the webview..
		web.loadUrl(getIntent().getStringExtra("url"));
		web.getSettings().setLoadWithOverviewMode(true);
		web.getSettings().setUseWideViewPort(true);
	}
}