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
import ru.omsu.avangard.omsk.services.protocol.Requests;
import ru.omsu.avangard.omsk.services.protocol.Responses.GetGamesResponse;
import ru.omsu.avangard.omsk.services.protocol.Responses.GetGamesResponse.GameModel;
import ru.omsu.avangard.omsk.ui.adapters.MatchesListAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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

	protected void loadMatches(){
		final Intent apiCall = APICallBuilder.makeIntent(
				this,
				Requests.Games.getCurrentTourmentGames(), 
				GetGamesResponse.class,
				new APIResultReceiver() {
					
					@Override
					public void onResult(Result result) {
						if(result.isOk()){
							final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
							final GetGamesResponse response = (GetGamesResponse)result.getResponse();
							final List<Match> matches = new ArrayList<Match>();
							for(GameModel game: response.gamesModel.games){
								if(game.firstTeam.id == 3983 || game.secondTeam.id == 3983){
									matches.add(
											new Match(
													new Team(game.firstTeam.name, ""), 
													new Team(game.secondTeam.name, ""), 
													safeParseDate(game.date, sdf),
													game.firstTeam.scores + ":" + game.secondTeam.scores)
											);
								}
							}
							final ListAdapter adapter = new MatchesListAdapter(MatchesActivity.this, matches);
							setListAdapter(adapter);
						}
					}
				});
		startService(apiCall);
	}
	
	protected Date safeParseDate(String date, SimpleDateFormat sdf){
		try{
			return sdf.parse(date);
		} catch(ParseException e){
			return new Date();
		}
	}

}
