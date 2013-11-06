package com.araperth.xmlfeedreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

import com.araperth.xmlfeedreader.XmlFeedParser.Entry;

public class LoadXmlData {

	private ArrayList<Entry> arrayLst;

	public ArrayList<Entry> loadXml(String urlString, Context context)
			throws XmlPullParserException, IOException {
		InputStream stream = null;
		// creates the xml parser object
		XmlFeedParser xmlFeedParser = new XmlFeedParser();
		List<Entry> entries = null;
		try {
			// retunrs the input stream for the DownloadUrl class.
			
			stream = new DownloadUrl().downloadUrl(urlString); 
			// parse and returns the required fields 
			// and returns list of entries that are
			// Corresponding to the entry tag in the xml data.
			entries = xmlFeedParser.parse(stream);
			
		} finally {
			if (stream != null) {
				// close the input stream object.
				stream.close();
			}
		}
		// Add each entry to the array list.
		arrayLst = new ArrayList<Entry>();
		for (Entry entry : entries) {
			arrayLst.add(entry);
		}
		return arrayLst;
	}
}
