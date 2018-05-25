package com.eduardrock.chilenismos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends Activity {
	private SearchView mSearchView;
	private ListView mListView;
	ArrayList<HashMap<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			File dbFile = getDatabasePath("diccionario.db");
			if (!dbFile.exists()) {
				this.copy("diccionario.db", dbFile.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setTitle("Chilenismos");

		SQLiteDatabase db = openOrCreateDatabase("diccionario.db", MODE_PRIVATE, null);
		Cursor cur = db.rawQuery("SELECT id AS _id, name FROM words", null);
		list = new ArrayList<HashMap<String,String>>();		
		while (cur.moveToNext()) {
			HashMap<String, String> row = new HashMap<String, String>();
			row.put("id", cur.getString(0));
			row.put("name", cur.getString(1));
			list.add(row);
		}
		

		mSearchView = (SearchView) findViewById(R.id.search_view);
		mListView = (ListView) findViewById(R.id.list_view);
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.row, new String[]{ "id", "name" }, new int[]{ R.id.cell_id, R.id.cell_name });
//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, cur, new String[]{ "name" }, new int[]{ R.id.cell_name });
		mListView.setAdapter(adapter);
		mListView.setTextFilterEnabled(true);
		
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				filter(query);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				filter(newText);
				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView cell_id = (TextView) view.findViewById(R.id.cell_id);				
				Intent i = new Intent(MainActivity.this, ViewDefinitionActivity.class);
				Bundle data = new Bundle();
				data.putInt("id", Integer.parseInt(cell_id.getText().toString()));
				i.putExtras(data);
				startActivity(i);
			}
		});

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

	public void filter(String text) {
		if (text.isEmpty()) {
			mListView.clearTextFilter();
		} else {
			mListView.setFilterText(text);
		}
	}

	void copy(String file, String folder) throws IOException {
		File CheckDirectory;
		CheckDirectory = new File(folder);

		String parentPath = CheckDirectory.getParent();

		File filedir = new File(parentPath);
		if (!filedir.exists()) {
			if (!filedir.mkdirs()) {
				return;
			}
		}

		InputStream in = this.getApplicationContext().getAssets().open(file);
		File newfile = new File(folder);
		OutputStream out = new FileOutputStream(newfile);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0)
			out.write(buf, 0, len);
		in.close();
		out.close();
	}

}