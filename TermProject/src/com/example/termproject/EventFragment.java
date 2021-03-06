package com.example.termproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.dbutil.DatabaseUtil;
import com.example.dbutil.OnlineDBUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class EventFragment extends Fragment {
	String eventType;
	ArrayList<Event> events; 
	ArrayList<Event> eventsLoaded;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.eventType = getArguments().getString("eventType");
		events = queryForEvents();
	}

	@Override
	public void onResume() {
		super.onResume();
		events = queryForEvents();
		updateView(events);
	}

	public ArrayList<Event> queryForEvents(){
		ArrayList<String> select = new ArrayList<String>();
		select.add("Name__c");
		select.add("Details__c");
		select.add("Date__c");
		select.add("Time__c");
		select.add("Location__c");
		select.add("Type__c");
		select.add("Rating__c");
		DatabaseUtil du = new DatabaseUtil(select, eventType + "_Event__c",
				"Rating__c", "desc");
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			events = new OnlineDBUtil.GetEvents().execute(du.buildQuery())
						.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return events;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.options, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh:
			events = queryForEvents();
			updateView(events);
			break;
		case R.id.create:
			Intent i = new Intent(getActivity(), CreateActivity.class);
			i.putExtra("eventType", eventType);
			startActivity(i); 
			break;
		case R.id.recent:
			Collections.sort(events,new TimeComparator());
			updateView(events);
			break;
		case R.id.popular:
			Collections.sort(events,new RatingsComparator());
			updateView(events);
			break;
		case R.id.academic:
			updateView(filterEvents("Academic"));
			break;
		case R.id.free:
			updateView(filterEvents("Free"));
			break;
		case R.id.music:
			updateView(filterEvents("Music"));
			break;
		case R.id.sport:
			updateView(filterEvents("Sport"));
			break;
		case R.id.other:
			updateView(filterEvents("Other"));
			break;
		
		}
		return true;
	}
	
	public ArrayList<Event> filterEvents(String type) {
		ArrayList<Event> filtered = new ArrayList<Event>();
		for(Event e : events){
			if (e.getType().equals(type))
				filtered.add(e);
		}
		return filtered;
	}
	
	public class TimeComparator implements Comparator<Event> {
	    @Override
	    public int compare(Event event1, Event event2) {
	        return event1.getDate().compareTo(event2.getDate());
	    }
	}
	
	public class RatingsComparator implements Comparator<Event> {
	    @Override
	    public int compare(Event event1, Event event2) {
	        return (event1.getRating() > event2.getRating()) ? -1 : 1;
	    }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_event, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		updateView(events);
		ListView eventList = (ListView) getView().findViewById(R.id.EventList);
		eventList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i = new Intent(getActivity(),ViewEventActivity.class);
				i.putExtra("event", eventsLoaded.get(position));
				startActivity(i);
			}
		});
	}
	
	public void updateView(ArrayList<Event> events) {
		if (events.size() == 0) {
			Toast.makeText(getActivity(), "No " + eventType + " events found.",
					Toast.LENGTH_LONG).show();
		}
		eventsLoaded = events;
		ListView eventList = (ListView) getView().findViewById(R.id.EventList);
		List<Map<String, String>> eventDisplay = new ArrayList<Map<String, String>>();
		for (Event e : events) {
			Map<String, String> display = new HashMap<String, String>();
			display.put("title", e.getName());
			display.put("type", e.getType());
			display.put("details", e.getDetails());
			int x = e.getRating();
			String rating = ((x>0)?"+":"") + x;
			display.put("rating", rating);
			eventDisplay.add(display);
		}

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), eventDisplay,
				R.layout.list_view, new String[] { "title","type","details","rating" },
				new int[] { R.id.text1, R.id.text2,R.id.text3,R.id.text4 });
		eventList.setAdapter(adapter);
	}
}
