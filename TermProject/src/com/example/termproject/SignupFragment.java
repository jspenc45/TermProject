package com.example.termproject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.example.dbutil.DatabaseUtil;
import com.example.dbutil.OnlineDBUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupFragment extends Fragment implements OnClickListener {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_signup, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Button) getView().findViewById(R.id.signup_cancel))
				.setOnClickListener(this);
		((Button) getView().findViewById(R.id.signup_signup))
				.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_cancel:
			getActivity().getSupportFragmentManager().popBackStackImmediate();
			break;
		case R.id.signup_signup:

			FieldChecker check = new FieldChecker();
			if (!check.runTests()) {
				Toast.makeText(getActivity(), check.getMessage(),
						Toast.LENGTH_LONG).show();
			} else {
				Account account = new Account(((EditText) getView()
						.findViewById(R.id.username)).getText().toString(),
						((EditText) getView().findViewById(R.id.email))
								.getText().toString(), ((EditText) getView()
								.findViewById(R.id.password)).getText()
								.toString());
				try {
					new OnlineDBUtil.insertRecords().execute(account.toJSON(),"Account__c")
							.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				new AlertDialog.Builder(getActivity())
						.setMessage("Registration Complete!")
						.setTitle("Sign Up")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										getActivity()
												.getSupportFragmentManager()
												.popBackStackImmediate();
									}
								}).create().show();
			}
		}

	}

	public class FieldChecker {
		String statusMessage;
		ArrayList<Account> accounts;

		public FieldChecker() {
			statusMessage = "";
			accounts = new ArrayList<Account>();
		}

		public String getMessage() {
			return statusMessage;
		}

		public boolean runTests() {
			if (!fieldsPopulated())
				return false;
			else if (!passwordsMatch()) {
				statusMessage = "Your passwords do not match. Please Reenter passwords.";
				return false;
			} else if (!usernameAvailable())
				return false;
			else if (!emailAvailable())
				return false;

			return true;
		}

		private boolean fieldsPopulated() {
			if (((EditText) getView().findViewById(R.id.username)).getText()
					.toString().equals("")) {
				statusMessage = "Please fill out Username field.";
				return false;
			} else if (((EditText) getView().findViewById(R.id.email))
					.getText().toString().equals("")) {
				statusMessage = "Please fill out Email Address field.";
				return false;
			} else if (((EditText) getView().findViewById(R.id.password))
					.getText().length() == 0) {
				statusMessage = "Password cannot be blank.";
				return false;
			}
			return true;
		}

		private boolean passwordsMatch() {
			return ((EditText) getView().findViewById(R.id.password))
					.getText()
					.toString()
					.equals(((EditText) getView().findViewById(
							R.id.verify_password)).getText().toString());
		}

		private boolean usernameAvailable() {
			String username = ((EditText) getView().findViewById(R.id.username))
					.getText().toString();
			ArrayList<String> select = new ArrayList<String>();
			select.add("Username__c");
			select.add("email__c");
			DatabaseUtil du = new DatabaseUtil(select, "Account__c");

			try {
				accounts = new OnlineDBUtil.GetRecords().execute(
						du.buildQuery()).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			for (Account account : accounts) {
				if (username.equalsIgnoreCase(account.getName())) {
					statusMessage = "Username is taken. Please try another.";
					return false;
				}
			}
			return true;
		}

		private boolean emailAvailable() {
			String email = ((EditText) getView().findViewById(R.id.email))
					.getText().toString();

			for (Account account : accounts) {
				if (email.equalsIgnoreCase(account.getEmailAddress())) {
					statusMessage = "Email Address is already in use. Plaese use another.";
					return false;
				}
			}
			return true;
		}
	}

}
