package com.example.termproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewEventActivity extends Activity implements OnClickListener{
	boolean plussed = false;
	boolean minussed = false;
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
        
        Button backButton1 = (Button) findViewById(R.id.backButton);
        backButton1.setOnClickListener(this);
        
        Button plusButton1 = (Button) findViewById(R.id.plusButton);
        plusButton1.setOnClickListener(this);
        
        Button minusButton1 = (Button) findViewById(R.id.minusButton);
        minusButton1.setOnClickListener(this);


	}

	@Override
	public void onClick(View arg0) {
		TextView ratingField;
		int num;
		switch(arg0.getId()){
		case R.id.plusButton:
			ratingField = (TextView) findViewById(R.id.ratingCounter);
			Button plusButton1 = (Button) findViewById(R.id.plusButton);
			Button minusButton1 = (Button) findViewById(R.id.minusButton);
			num = Integer.parseInt(ratingField.getText().toString())+1;
			plussed = true;
			if (minussed) {
				minussed = false;
				minusButton1.setEnabled(true);
				num++;
			}
			plusButton1.setEnabled(false);
			ratingField.setText(num+"");
		break;
		case R.id.minusButton:
			ratingField = (TextView) findViewById(R.id.ratingCounter);
			Button plusButton = (Button) findViewById(R.id.plusButton);
			Button minusButton = (Button) findViewById(R.id.minusButton);
			num = Integer.parseInt(ratingField.getText().toString())-1;
			minussed = true;
			if (plussed) {
				plussed = false;
				plusButton.setEnabled(true);
				num--;
			}
			minusButton.setEnabled(false);
			ratingField.setText(num+"");
		break;
		case R.id.backButton:
			finish();
			break;
		}
		
	}
	

}
        
        
        
	


