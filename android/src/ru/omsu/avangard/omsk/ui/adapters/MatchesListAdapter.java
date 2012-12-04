package ru.omsu.avangard.omsk.ui.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.omsu.avangard.omsk.R;
import ru.omsu.avangard.omsk.R.id;
import ru.omsu.avangard.omsk.R.layout;
import ru.omsu.avangard.omsk.data.Match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchesListAdapter extends ArrayAdapter<Match>{

	private static class TeamLineHolder {
		ImageView teamLogo;
		TextView teamName;
		TextView teamGoals;
	}
	
	private static class ViewHolder {
		TeamLineHolder teamLine1;
		TeamLineHolder teamLine2;
		TextView date;
	}
	
	private LayoutInflater mInflater;
	private final SimpleDateFormat mMatchesDateFormat;
	
	public MatchesListAdapter(Context context, List<Match> objects) {
		super(context, -1, objects);
		mInflater = LayoutInflater.from(context);
		mMatchesDateFormat = new SimpleDateFormat("dd MMMMM", Locale.getDefault());
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_matches, null);
			holder = new ViewHolder();
			holder.teamLine1 = makeTeamLineHolder(convertView, R.id.matches_item_team_line1);
			holder.teamLine2 = makeTeamLineHolder(convertView, R.id.matches_item_team_line2);
			holder.date = (TextView)convertView.findViewById(R.id.matches_item_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Match match = getItem(position);
		holder.teamLine1.teamName.setText(match.getTeam1().getName());
		holder.teamLine2.teamName.setText(match.getTeam2().getName());
		if(match.hasResult() && match.getStartTime().before(new Date())){
			holder.teamLine1.teamGoals.setVisibility(View.VISIBLE);
			holder.teamLine1.teamGoals.setText("" + match.getTeam1Goals());
			holder.teamLine2.teamGoals.setVisibility(View.VISIBLE);
			holder.teamLine2.teamGoals.setText("" + match.getTeam2Goals());
		} else{
			holder.teamLine1.teamGoals.setVisibility(View.INVISIBLE);
			holder.teamLine2.teamGoals.setVisibility(View.INVISIBLE);
		}
		holder.date.setText(mMatchesDateFormat.format(match.getStartTime()));
		return convertView;
	}
	
	private TeamLineHolder makeTeamLineHolder(View view, int teamLineId){
		final View teamLineView = view.findViewById(teamLineId);
		final TeamLineHolder result = new TeamLineHolder();
		result.teamGoals = (TextView) teamLineView.findViewById(R.id.team_line_goals);
		result.teamLogo = (ImageView) teamLineView.findViewById(R.id.team_line_logo);
		result.teamName = (TextView) teamLineView.findViewById(R.id.team_line_name);
		return result;
	}
	
	
}
