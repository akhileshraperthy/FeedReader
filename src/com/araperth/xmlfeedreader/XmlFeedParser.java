package com.araperth.xmlfeedreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XmlFeedParser {
	
	// namespace 
	private static final String ns = null; 
	
	// parse method - creates XmlPullParser object and calls readFeed method.a
	public List parse(InputStream in) throws XmlPullParserException,
			IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		}
		// Close the stream after using it
		finally {
			in.close();
		}
	}

	
	// readFeed looks for the 'entry' tag and pass the controler to readEntry method
	// the others are just skipped
	private List readFeed(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		List entries = new ArrayList();
		parser.require(XmlPullParser.START_TAG, ns, "feed");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// entry tag
			if (name.equals("entry")) {
				// calls readEntry method.
				entries.add(readEntry(parser));
			} else {
				// skips the tags that are unnecessary 
				skip(parser);
			}
		}
		// returns the list of entries
		return entries;
	}

	public static class Entry {
		public final String title;
		public final String link;
		public final String summary;

		private Entry(String title, String summary, String link) {
			this.title = title;
			this.summary = summary;
			this.link = link;
		}
	}

	// readEntry looks for the 'title', 'content', and 'link' tags and handle controller
	// to their respective read methods
	// the tags that are not necessasry are just skipped.
	private Entry readEntry(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "entry");
		String title = null;
		String summary = null;
		String link = null;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			String name = parser.getName();
			// reads the title inside entry
			if (name.equals("title")) {
				title = readTitle(parser);
				// reads content
			} else if (name.equals("content")) {
				summary = readSummary(parser);
				// reads link
			} else if (name.equals("link")) {
				link = readLink(parser);
			} else {
				// skips 
				skip(parser);
			}
		}
		return new Entry(title, summary, link);
	}

	// reads title tags inside entry
	private String readTitle(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return title;
	}

	// reads link tags in the feed and look for the 'rel' attribute and 
	// Particularly which is alternate value in a 'rel' attribute.
	// Gets the URL and returns as a link
	private String readLink(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		System.out.println("read link");
		String link = "";
	    parser.require(XmlPullParser.START_TAG, ns, "link");
	    String tag = parser.getName();
	    String relType = parser.getAttributeValue(null, "rel");  
	    if (tag.equals("link")) {
	        if (relType.equals("alternate")){
	            link = parser.getAttributeValue(null, "href");     
	            parser.nextTag();
	        } 
	        else
	        {
	        	parser.nextTag();
	        }
	    }
	    parser.require(XmlPullParser.END_TAG, ns, "link");
	    return link;
	}

	// reads the content tag
	private String readSummary(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "content");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "content");
		return summary;
	}

	// readText - reads the text value of the required tags and pass the controller
	// parser  to next tag.
	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	// skips the tags that are no use to this contetx.
	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}