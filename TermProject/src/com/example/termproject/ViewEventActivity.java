package com.example.termproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewEventActivity extends Activity implements OnClickListener{
	
	@Override
	public void onCreate(Bundle arg) {
        super.onCreate(arg);
        setContentView(R.layout.activity_view);
 
        Event event = getIntent().getExtras().getParcelable("event");
        
        TextView txtName= (TextView) findViewById(R.id.name_e);
        TextView txtDate= (TextView) findViewById(R.id.date_e);
        TextView txtTime= (TextView) findViewById(R.id.time_e);
        TextView txtLocation= (TextView) findViewById(R.id.location_e);
        TextView txtDetails= (TextView) findViewById(R.id.details_e);
        TextView txtType= (TextView) findViewById(R.id.type_e);
        TextView txtRating = (TextView) findViewById(R.id.ratingCounter);
        
        txtName.setText(event.getName());
        txtDate.setText(event.getDate());
        txtTime.setText(event.getTime());
        txtLocation.setText(event.getLocation());
        txtDetails.setText(event.getDetails());
        txtType.setText(event.getType());
        String num = event.getRating() + "";
        txtRating.setText(num);
        //Button button = findview...
        //button.setOnClickListener(this);
        
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		//case R.id.buttonName://do code
			//break;
		case R.id.backButton:
			finish();
			break;
		}
		
	}
	

}
        
        
        
	


