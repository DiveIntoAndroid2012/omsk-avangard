package ru.omsu.avangard.omsk.ui.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.omsu.avangard.omsk.data.Match;
import ru.omsu.avangard.omsk.data.Team;
import ru.omsu.avangard.omsk.services.APICallBuilder;
import ru.omsu.avangard.omsk.services.APIService.APIResultReceiver;
import ru.omsu.avangard.omsk.services.APIService.Result;
import ru.omsu.avangard.omsk.services.protocol.ProtocolUtils;
import ru.omsu.avangard.omsk.services.protocol.Requests;
import ru.omsu.avangard.omsk.services.protocol.Responses.GetGamesResponse;
import ru.omsu.avangard.omsk.services.protocol.Responses.GetGamesResponse.GameModel;
import ru.omsu.avangard.omsk.ui.adapters.MatchesListAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;

public class MatchesActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initListView();
		loadMatches();
	}

	protected void initListView() {
		getListView().setDividerHeight(0);
	}

	protected void loadMatches() {
		final Intent apiCall = APICallBuilder.makeIntent(
				this,
				Requests.Games.getCurrentTourmentGames(),
				GetGamesResponse.class,
				new APIResultReceiver() {

					@Override
					public void onResult(Result result) {
						if (result.isOk()) {
							final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
							final Date now = new Date();
							final GetGamesResponse response = (GetGamesResponse) result.getResponse();
							int counter = 0;
							final List<Match> matches = new ArrayList<Match>();
							for (GameModel game : response.gamesModel.games) {
								if (game.firstTeam.id == 3983 || game.secondTeam.id == 3983) {
									final Date matchDate = safeParseDate(game.date, sdf);
									matches.add(
											new Match(
													new Team(game.firstTeam.name, ProtocolUtils.getLogoUrl(game.firstTeam.logo)),
													new Team(game.secondTeam.name, ProtocolUtils.getLogoUrl(game.secondTeam.logo)),
													matchDate,
													game.firstTeam.scores + ":" + game.secondTeam.scores)
											);
									if (matchDate.before(now)) {
										++counter;
									}
								}
							}
							final ListAdapter adapter = new MatchesListAdapter(MatchesActivity.this, matches);
							setListAdapter(adapter);
							getListView().setVisibility(View.INVISIBLE);
							final Integer freezedCounter = counter;
							getListView().postDelayed(new Runnable() {
								
								@Override
								public void run() {
									final int listViewHeight = getListView().getHeight();
									final int itemHeight = getListView().getChildAt(0).getHeight();
									final float itemsPerScreen = (float) listViewHeight / itemHeight;
									getListView().setSelection(Math.max(0, freezedCounter.intValue() - (int) (itemsPerScreen * 0.5)));		
									getListView().setVisibility(View.VISIBLE);
								}
							}, 300);
						}
					}
				});
		startService(apiCall);
	}

	protected Date safeParseDate(String date, SimpleDateFormat sdf) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}

}
