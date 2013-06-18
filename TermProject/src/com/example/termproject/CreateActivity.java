package com.example.termproject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.example.dbutil.OnlineDBUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateActivity extends Activity implements OnClickListener {

	String eventType;

	@Override
	protected void onCreate(Bundle arg) {
		super.onCreate(arg);
		setContentView(R.layout.fragment_create);
		eventType = getIntent().getExtras().getString("eventType");
		((Button) findViewById(R.id.e_cancel)).setOnClickListener(this);
		((Button) findViewById(R.id.e_create)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.e_cancel:
			finish();
			break;
		case R.id.e_create:

			EventCheck check2 = new EventCheck();

			if (!check2.checkFields()) {
				Toast.makeText(this, check2.getErrorMessage(),
						Toast.LENGTH_LONG).show();

			} else {
				Event event = new Event(((EditText) findViewById(R.id.e_name))
						.getText().toString(),
						((EditText) findViewById(R.id.e_location)).getText()
								.toString(),
						((EditText) findViewById(R.id.e_details)).getText()
								.toString(),
						((EditText) findViewById(R.id.e_time)).getText()
								.toString(),
						((EditText) findViewById(R.id.e_date)).getText()
								.toString(),
						((Spinner) findViewById(R.id.e_type)).getSelectedItem()
								.toString(), 0);

				try {
					new OnlineDBUtil.insertRecords().execute(event.toJSON(),
							eventType + "_Event__c").get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// show confirmation dialog
				new AlertDialog.Builder(this)
						.setMessage("Event has been created!")
						.setTitle("Create Event")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								}).create().show();
			}

		}

	}

	public class EventCheck {
		String eMessage;
		ArrayList<Event> events;

		public EventCheck() {
			eMessage = "";
			events = new ArrayList<Event>();
		}

		public String getErrorMessage() {
			return eMessage;
		}

		public boolean checkFields() {
			if (!formFilled())
				return false;
			return true;
		}

		// event checker to see if field are empty
		private boolean formFilled() {
			if (((EditText) findViewById(R.id.e_name)).getText().toString()
					.equals("")) {
				eMessage = "Name Field empty";
				return false;
			} else if (((EditText) findViewById(R.id.e_date)).getText()
					.toString().equals("")) {
				eMessage = "Date field empty";
				return false;
			} else if (((EditText) findViewById(R.id.e_time)).getText()
					.toString().equals("")) {
				eMessage = "Time field empty";
				return false;
			} else if (((EditText) findViewById(R.id.e_location)).getText()
					.toString().equals("")) {
				eMessage = "Location field empty";
				return false;
			} else if (((EditText) findViewById(R.id.e_details)).getText()
					.toString().equals("")) {
				eMessage = "Enter some details man!";
				return false;

			}
			return true;
		}

	}
}
