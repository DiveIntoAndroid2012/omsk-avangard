package ru.omsu.diveintoandroid.omskavangard.provider;

import ru.omsu.diveintoandroid.omskavangard.provider.DataContract.Matches;
import ru.omsu.diveintoandroid.omskavangard.provider.DataContract.Teams;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HockeyDatabase extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "omskavangard.db";
	private static final int DATABASE_VERSION = 1;
	
	interface Tables {
        String TEAMS = "teams";
        String MATCHES = "matches";
    }
	
	public HockeyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + Tables.TEAMS + " ("
                + Teams._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Teams.TEAM_ID + " TEXT NOT NULL,"
                + Teams.TEAM_NAME + " TEXT NOT NULL,"
                + Teams.TEAM_LOGO_URL + " TEXT,"
                + "UNIQUE (" + Teams.TEAM_ID + ") ON CONFLICT REPLACE)");
		
		db.execSQL("CREATE TABLE " + Tables.MATCHES + " ("
                + Matches._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Matches.MATCH_ID + " TEXT NOT NULL, "
                + Matches.MATCH_DATE + " INTEGER, "
                + Matches.MATCH_FIRST_TEAM_ID + " TEXT NOT NULL, "
                + Matches.MATCH_FIRST_TEAM_SCORES + " INTEGER, "
                + Matches.MATCH_JUDGE + " TEXT NOT NULL, "
                + Matches.MATCH_JUDGE_L + " TEXT NOT NULL, "
                + Matches.MATCH_JUDGE_R + " TEXT NOT NULL, "
                + Matches.MATCH_NUMBER + " INTEGER, "
                + Matches.MATCH_PROTOCOL + " INTEGER, "
                + Matches.MATCH_STATUS + " INTEGER, "
                + Matches.MATCH_SCORES + " TEXT NOT NULL, "
                + Matches.MATCH_SECOND_TEAM_ID + " TEXT NOT NULL, "
                + Matches.MATCH_SECOND_TEAM_SCORES + " INTEGER, "
                + Matches.MATCH_TOURNAMENT_ID + " TEXT NOT NULL, "
                + "UNIQUE (" + Matches.MATCH_ID + ") ON CONFLICT REPLACE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
