package ru.omsu.avangard.omsk;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MatchesActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    final String[] matches = {"Авангард - Динамо", "Трактор - Авангард", "Ак барс - Авангард"};
	    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches);
	    setListAdapter(adapter);
	}

}
