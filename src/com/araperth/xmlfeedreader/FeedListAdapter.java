package com.araperth.xmlfeedreader;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.araperth.xmlfeedreader.XmlFeedParser.Entry;
import com.araperth.xmlfeedreader.R;

// extends Array Adapter
class FeedListAdapter extends ArrayAdapter<Entry> {
	private List<Entry> rssFeedLst;
	Context context;
	Entry entry;

	public FeedListAdapter(Context context, int textViewResourceId,List<Entry> rssFeedLst) {
		super(context, textViewResourceId, rssFeedLst);
		this.rssFeedLst = rssFeedLst;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		RssHolder rssHolder = null;
		if (convertView == null) {
			view = View.inflate(context, R.layout.rsslistview, null);
			rssHolder = new RssHolder();
			rssHolder.rssTitleView = (TextView) view
					.findViewById(R.id.rss_title_view);
			view.setTag(rssHolder);
		} else {
			rssHolder = (RssHolder) view.getTag();
		}
		entry = rssFeedLst.get(position);
		// sets the title to the list view.
		rssHolder.rssTitleView.setText(entry.title);
		return view;
	}

	static class RssHolder {
		public TextView rssTitleView;
	}
}