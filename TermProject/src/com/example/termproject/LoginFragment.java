package com.example.termproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.dbutil.DatabaseUtil;
import com.example.dbutil.OnlineDBUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment implements OnClickListener {

	OnButtonClick buttonClick;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		buttonClick = (OnButtonClick) activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Button)getActivity().findViewById(R.id.login_button)).setOnClickListener(this);
		((Button)getActivity().findViewById(R.id.signup_button)).setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}
	
	public interface OnButtonClick {
		public void onLoginButtonClick();
		public void onSignupButtonClick();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.login_button:
			if(loginExists()){
				buttonClick.onLoginButtonClick();
			}
			else {
				Toast.makeText(getActivity(), "Incorrect Username/Password. Please try again.", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.signup_button:
			buttonClick.onSignupButtonClick();
			break;
		}
	}
	
	public boolean loginExists() {
		ArrayList<String> select = new ArrayList<String>();
		select.add("Username__c");
		Map<String,String> where = new HashMap<String,String>();
		String username = ((EditText)getView().findViewById(R.id.username)).getText().toString();
		String password = ((EditText)getView().findViewById(R.id.password)).getText().toString();
		where.put("Username__c", username);
		where.put("password__c", password);
		DatabaseUtil du = new DatabaseUtil(select, "Account__c", where);
		ArrayList<Account> loginResult = null;
		
		try {
			loginResult = new OnlineDBUtil.GetRecords().execute(du.buildQuery()).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if(loginResult.isEmpty())
			return false;
		return true;
	}
	
}
