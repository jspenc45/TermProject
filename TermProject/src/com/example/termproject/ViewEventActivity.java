package com.example.termproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewEventActivity extends Activity {
	
	@Override
	public void onCreate(Bundle arg) {
        super.onCreate(arg);
        setContentView(R.layout.event_layout);
 
        Event event = new Event(null, null, null, null, null, null, 0);
        
        TextView txtName= (TextView) findViewById(R.id.name_e);
        TextView txtDate= (TextView) findViewById(R.id.date_e);
        TextView txtTime= (TextView) findViewById(R.id.time_e);
        TextView txtLocation= (TextView) findViewById(R.id.location_e);
        TextView txtDetails= (TextView) findViewById(R.id.details_e);
        TextView txtType= (TextView) findViewById(R.id.type_e);
        
        txtName.setText(event.getName());
        txtDate.setText(event.getDate());
        txtTime.setText(event.getTime());
        txtLocation.setText(event.getLocation());
        txtDetails.setText(event.getDetails());
        txtType.setText(event.getType());
        
        
	}
	

}
        
        
        
	


