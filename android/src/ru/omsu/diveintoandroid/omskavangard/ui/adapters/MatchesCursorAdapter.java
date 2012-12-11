package ru.omsu.diveintoandroid.omskavangard.ui.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.omsu.diveintoandroid.omskavangard.R;
import ru.omsu.diveintoandroid.omskavangard.provider.DataContract;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MatchesCursorAdapter extends CursorAdapter {

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
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private Map<String, Integer> mColumnIndexMap = new HashMap<String, Integer>();
	
	public MatchesCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = LayoutInflater.from(context);
		mMatchesDateFormat = new SimpleDateFormat("dd MMMMM", Locale.getDefault());
		mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
		mDisplayImageOptions = new DisplayImageOptions.Builder()
				.cacheInMemory()
				.cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build();
		initColumnIndexMap(c);
	}

	@Override
	public Cursor swapCursor(Cursor newCursor) {
		initColumnIndexMap(newCursor);
		return super.swapCursor(newCursor);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View view = mInflater.inflate(R.layout.list_item_matches, null);
		ViewHolder holder = new ViewHolder();
		holder.teamLine1 = makeTeamLineHolder(view, R.id.matches_item_team_line1);
		holder.teamLine2 = makeTeamLineHolder(view, R.id.matches_item_team_line2);
		holder.date = (TextView) view.findViewById(R.id.matches_item_date);
		view.setTag(holder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder holder = (ViewHolder)view.getTag();
		mImageLoader.displayImage(cursor.getString(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_FIRST_TEAM_LOGO_URL)), holder.teamLine1.teamLogo, mDisplayImageOptions);
		mImageLoader.displayImage(cursor.getString(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_SECOND_TEAM_LOGO_URL)), holder.teamLine2.teamLogo, mDisplayImageOptions);
		holder.teamLine1.teamName.setText(cursor.getString(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_FIRST_TEAM_NAME)));
		holder.teamLine2.teamName.setText(cursor.getString(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_SECOND_TEAM_NAME)));
		boolean matchHasResult = cursor.getInt(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_STATUS)) != DataContract.Constants.MATCH_STATUS_NOT_STARTED;
		if (matchHasResult) {
			holder.teamLine1.teamGoals.setVisibility(View.VISIBLE);
			holder.teamLine1.teamGoals.setText("" + cursor.getInt(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_FIRST_TEAM_SCORES)));
			holder.teamLine2.teamGoals.setVisibility(View.VISIBLE);
			holder.teamLine2.teamGoals.setText("" + cursor.getInt(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_SECOND_TEAM_SCORES)));
		} else {
			holder.teamLine1.teamGoals.setVisibility(View.INVISIBLE);
			holder.teamLine2.teamGoals.setVisibility(View.INVISIBLE);
		}
		final long matchDate = cursor.getLong(mColumnIndexMap.get(DataContract.MatchesDetailed.MATCH_DATE));
		holder.date.setText(mMatchesDateFormat.format(new Date(matchDate)));
	}

	private void initColumnIndexMap(Cursor cursor){
		if(cursor == null){
			return;
		}
		for(int i = 0; i < cursor.getColumnCount(); ++i){
			mColumnIndexMap.put(cursor.getColumnName(i), i);
		}
	}
	
	private TeamLineHolder makeTeamLineHolder(View view, int teamLineId) {
		final View teamLineView = view.findViewById(teamLineId);
		final TeamLineHolder result = new TeamLineHolder();
		result.teamGoals = (TextView) teamLineView.findViewById(R.id.team_line_goals);
		result.teamLogo = (ImageView) teamLineView.findViewById(R.id.team_line_logo);
		result.teamName = (TextView) teamLineView.findViewById(R.id.team_line_name);
		return result;
	}

}