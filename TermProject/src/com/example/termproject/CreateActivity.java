package com.example.termproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle arg) {
		super.onCreate(arg);
		setContentView(R.layout.fragment_create);

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
			if (/* TODO: something is wrong!!!! */false) {

			} else {
				/*
				 * //TODO:Replace this so you make a new Event and run .toJSON()
				 * on it (I have implemented this) Account account = new
				 * Account(( (EditText)
				 * getView().findViewById(R.id.username)).getText().toString(),
				 * ((EditText)
				 * getView().findViewById(R.id.email)).getText().toString(),
				 * ((EditText)
				 * getView().findViewById(R.id.password)).getText().toString());
				 * try { new
				 * OnlineDBUtil.insertRecords().execute(account.toJSON())
				 * .get(); } catch (InterruptedException e) {
				 * e.printStackTrace(); } catch (ExecutionException e) {
				 * e.printStackTrace(); } catch (JSONException e) {
				 * e.printStackTrace(); } // show confirmation dialog new
				 * AlertDialog.Builder(this)
				 * .setMessage("Event has been created!")
				 * .setTitle("Create Event") .setCancelable(false)
				 * .setPositiveButton("Ok", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { finish(); } }).create().show(); }
				 */
				break;
			}
		}
	}
}
