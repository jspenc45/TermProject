package com.example.termproject;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable{
	
	//event information
	public String name;
	public String location;
	public String details;
	public String time;
	public String date;
	public String type;
	public int rating;
	
	public Event(String name, String location, String details, String time,
			String date, String type, int rating) {
		super();
		this.name = name;
		this.location = location;
		this.details = details;
		this.time = time;
		this.date = date;
		this.type = type;
		this.rating = rating;
	}
	
	public Event(JSONObject jsonObject) throws JSONException {
		if (jsonObject.has("Name__c"))
			this.name = jsonObject.getString("Name__c");
		if (jsonObject.has("Location__c"))
			this.location = jsonObject.getString("Location__c");
		if (jsonObject.has("Details__c"))
			this.details = jsonObject.getString("Details__c");
		if (jsonObject.has("Time__c"))
			this.time = jsonObject.getString("Time__c");
		if (jsonObject.has("Date__c"))
			this.date = jsonObject.getString("Date__c");
		if (jsonObject.has("Type__c"));
			this.type = jsonObject.getString("Type__c");
		if (jsonObject.has("Rating__c")) {
			this.rating = jsonObject.getInt("Rating__c");
		}
	}
	public Event(Parcel in) {
		this.name = in.readString();
		this.location = in.readString();
		this.details = in.readString();
		this.time = in.readString();
		this.date = in.readString();
		this.type = in.readString();
		this.rating = in.readInt();
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("Name__c", name);
		json.put("Location__c", location);
		json.put("Details__c", details);
		json.put("Time__c", name);
		json.put("Date__c", date);
		json.put("Type__c", type);
		json.put("Rating__c", rating);
		return json;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeString(location);
		out.writeString(details);
		out.writeString(time);
		out.writeString(date);
		out.writeString(type);
		out.writeInt(rating);
	}
	
	public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
		public Event createFromParcel(Parcel in) {
			return new Event(in);
		}
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};
	
}
