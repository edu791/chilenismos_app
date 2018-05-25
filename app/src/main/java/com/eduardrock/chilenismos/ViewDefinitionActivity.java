package com.eduardrock.chilenismos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewDefinitionActivity extends Activity {
	TextView word_name, word_definition;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_definition);
		
		setTitle("Chilenismos");
		
		Bundle data = new Bundle();
		data = this.getIntent().getExtras();
		
		word_name = (TextView) findViewById(R.id.word_name);
		word_definition = (TextView) findViewById(R.id.word_definition);
		
		SQLiteDatabase db = openOrCreateDatabase("diccionario.db", MODE_PRIVATE, null);
		String sql_query = "SELECT name, description FROM words WHERE id=" + data.getInt("id");
		Cursor cur = db.rawQuery(sql_query, null);
		cur.moveToFirst();
		word_name.setText(cur.getString(0));
		word_definition.setText(cur.getString(1));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.btn_about:
	            Intent i = new Intent(this, CreditsActivity.class);
	            startActivity(i);
	    }
	    return true;
	}

}
