package com.eduardrock.chilenismos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreditsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		
		setTitle("Créditos");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
