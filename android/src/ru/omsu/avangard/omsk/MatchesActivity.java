package ru.omsu.avangard.omsk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

public class MatchesActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    initListView();
	}

	protected void initListView(){
		getListView().setDividerHeight(0);
		final List<Match> matches = stubMakeMatches();
	    final ListAdapter adapter = new MatchesListAdapter(this, matches);
	    setListAdapter(adapter);
	}
	
	private List<Match> stubMakeMatches(){
		final Team avangard = new Team("Авангард", "");
		final Team dynamo = new Team("Динамо", "");
		final Team akbars = new Team("Ак Барс", "");
		final Team traktor = new Team("Трактор", "");
		final Team ugra = new Team("Югра", "");
		final Team vityaz = new Team("Витязь", "");
		final Calendar calendar = Calendar.getInstance();
		
		final List<Match> matches = new ArrayList<Match>();
		matches.add(new Match(avangard, dynamo, stubMakeMatchDate("10/10/12"), "4:3"));
		matches.add(new Match(akbars, avangard, stubMakeMatchDate("13/10/12"), "1:0"));
		matches.add(new Match(avangard, traktor, stubMakeMatchDate("15/10/12"), "1:2"));
		matches.add(new Match(avangard, ugra, stubMakeMatchDate("17/10/12"), "5:1"));
		matches.add(new Match(vityaz, avangard, stubMakeMatchDate("19/10/12"), "2:3"));
		matches.add(new Match(dynamo, avangard, stubMakeMatchDate("21/10/12"), "1:0"));
		matches.add(new Match(avangard, dynamo, stubMakeMatchDate("10/10/12"), "4:3"));
		matches.add(new Match(akbars, avangard, stubMakeMatchDate("13/10/12"), "1:0"));
		matches.add(new Match(avangard, traktor, stubMakeMatchDate("15/10/12"), "1:2"));
		matches.add(new Match(avangard, ugra, stubMakeMatchDate("17/10/12"), "5:1"));
		matches.add(new Match(vityaz, avangard, stubMakeMatchDate("19/10/12"), "2:3"));
		matches.add(new Match(dynamo, avangard, stubMakeMatchDate("21/10/12"), "1:0"));
		
		return matches;
	}
	
	private Date stubMakeMatchDate(String date){
		return new Date();
	}
}
