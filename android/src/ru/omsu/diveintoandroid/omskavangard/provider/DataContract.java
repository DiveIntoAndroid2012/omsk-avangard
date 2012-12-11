package ru.omsu.diveintoandroid.omskavangard.provider;

import ru.omsu.diveintoandroid.omskavangard.services.protocol.ProtocolConstants;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {
	public static final String CONTENT_AUTHORITY = "ru.omsu.diveintoandroid.avangardomsk";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	
	interface TeamColumns{
		String TEAM_ID = "team_id";
		String TEAM_NAME = "team_name";
		String TEAM_LOGO_URL = "team_logo_url";
	}
	
	interface MatchColumns{
		String MATCH_ID = "match_id";
		String MATCH_FIRST_TEAM_ID = "match_first_team_id";
		String MATCH_SECOND_TEAM_ID = "match_second_team_id";
		String MATCH_DATE = "match_date";
		String MATCH_NUMBER = "match_number";
		String MATCH_TOURNAMENT_ID = "match_tournament_id";
		String MATCH_JUDGE = "match_judge";
		String MATCH_JUDGE_L = "match_judge_l";
		String MATCH_JUDGE_R = "match_judge_r";
		String MATCH_FIRST_TEAM_SCORES = "match_first_team_scores";
		String MATCH_SECOND_TEAM_SCORES = "match_second_team_scores";
		String MATCH_STATUS = "match_status";
		String MATCH_PROTOCOL = "match_protocol";
		String MATCH_SCORES = "match_scores";
	}
	
	public static class Teams implements BaseColumns, TeamColumns{
		
		public static final String PATH = "teams";
		
		public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.diveintoandroid.team";
		
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.diveintoandroid.team";
	}
	
	public static class Matches implements BaseColumns, MatchColumns{
		
		public static final String PATH = "matches";
		
		public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.diveintoandroid.matches";
		
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.diveintoandroid.match";
        
	}
	
	//virtual magic table
	public static class MatchesDetailed implements BaseColumns, MatchColumns{
		
		public static final String PATH = "matches_detailed";
		
		public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.diveintoandroid.matches_detailed";
		
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.diveintoandroid.match_detailed";
        
        public static final String MATCH_FIRST_TEAM_ID = "match_first_team_id";
        public static final String MATCH_FIRST_TEAM_NAME = "match_first_team_name";
        public static final String MATCH_FIRST_TEAM_LOGO_URL = "match_first_team_logo_url";
        
        public static final String MATCH_SECOND_TEAM_ID = "match_second_team_id";
        public static final String MATCH_SECOND_TEAM_NAME = "match_second_team_name";
        public static final String MATCH_SECOND_TEAM_LOGO_URL = "match_second_team_logo_url";
        
	}
	
	public static class Constants{
		public static final int MATCH_STATUS_NOT_STARTED = ProtocolConstants.MATCH_STATUS_NOT_STARTED;
		public static final int MATCH_STATUS_STARTED = ProtocolConstants.MATCH_STATUS_STARTED;
		public static final int MATCH_STATUS_FINISHED = ProtocolConstants.MATCH_STATUS_FINISHED;
		
		public static int matchStatus(int valueInResponse){
			return valueInResponse;
		}
	}
}
