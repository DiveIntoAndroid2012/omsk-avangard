package ru.omsu.avangard.omsk.ui.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.omsu.avangard.omsk.data.Match;
import ru.omsu.avangard.omsk.data.Team;
import ru.omsu.avangard.omsk.ui.adapters.MatchesListAdapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

public class MatchesActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initListView();
	}

	protected void initListView() {
		getListView().setDividerHeight(0);
		final List<Match> matches = stubMakeMatches();
		final ListAdapter adapter = new MatchesListAdapter(this, matches);
		setListAdapter(adapter);
	}

	private List<Match> stubMakeMatches() {
		try {
			final Team avangard = new Team("��������", "");
			final Team dynamo = new Team("������", "");
			final Team akbars = new Team("�� ����", "");
			final Team traktor = new Team("�������", "");
			final Team ugra = new Team("����", "");
			final Team vityaz = new Team("������", "");

			final List<Match> matches = new ArrayList<Match>();
			matches.add(new Match(avangard, dynamo,
					stubMakeMatchDate("10/10/12"), "4:3"));
			matches.add(new Match(akbars, avangard,
					stubMakeMatchDate("13/10/12"), "1:0"));
			matches.add(new Match(avangard, traktor,
					stubMakeMatchDate("15/10/12"), "1:2"));
			matches.add(new Match(avangard, ugra,
					stubMakeMatchDate("17/10/12"), "5:1"));
			matches.add(new Match(vityaz, avangard,
					stubMakeMatchDate("19/10/12"), "2:3"));
			matches.add(new Match(dynamo, avangard,
					stubMakeMatchDate("21/10/12"), "1:0"));
			matches.add(new Match(avangard, dynamo,
					stubMakeMatchDate("25/10/12"), "4:3"));
			matches.add(new Match(akbars, avangard,
					stubMakeMatchDate("31/10/12"), "1:0"));
			matches.add(new Match(avangard, traktor,
					stubMakeMatchDate("5/11/12"), null));
			matches.add(new Match(avangard, ugra,
					stubMakeMatchDate("12/11/12"), null));
			matches.add(new Match(vityaz, avangard,
					stubMakeMatchDate("19/11/12"), null));
			matches.add(new Match(dynamo, avangard,
					stubMakeMatchDate("21/11/12"), null));
			return matches;
		} catch (ParseException e) {
			return null;
		}
	}

	private Date stubMakeMatchDate(String date) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.parse(date);
	}
}
