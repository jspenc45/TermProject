package com.example.termproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements LoginFragment.OnButtonClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		Fragment fragment = new LoginFragment();
		if(savedInstanceState != null){
			fragment = getSupportFragmentManager().getFragment(savedInstanceState, null);
		}
		fragmentTransaction.add(R.id.main_layout, fragment);
		fragmentTransaction.commit();
    }
    
    public void onSignupButtonClick() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		SignupFragment fragment = new SignupFragment();
		fragmentTransaction.replace(R.id.main_layout, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	public void onLoginButtonClick() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		MainFragment fragment = new MainFragment();
		fragmentTransaction.replace(R.id.main_layout, fragment);
		fragmentTransaction.commit();
	}
    
}
