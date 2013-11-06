package com.araperth.xmlfeedreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// This class is used to download the content from the passed URL.

public class DownloadUrl {

	public InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// In milli sec.
		conn.setReadTimeout(10000); 
		// In milli sec
		conn.setConnectTimeout(15000);
		// GET method.
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		// returns the inputstream object.
		return conn.getInputStream();
	}
}
