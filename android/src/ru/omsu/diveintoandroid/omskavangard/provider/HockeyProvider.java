package ru.omsu.diveintoandroid.omskavangard.provider;

import ru.omsu.diveintoandroid.omskavangard.provider.DataContract.MatchColumns;
import ru.omsu.diveintoandroid.omskavangard.provider.DataContract.Matches;
import ru.omsu.diveintoandroid.omskavangard.provider.DataContract.MatchesDetailed;
import ru.omsu.diveintoandroid.omskavangard.provider.DataContract.TeamColumns;
import ru.omsu.diveintoandroid.omskavangard.provider.DataContract.Teams;
import ru.omsu.diveintoandroid.omskavangard.provider.HockeyDatabase.Tables;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class HockeyProvider extends ContentProvider{

	private static UriMatcher sUriMatcher = buildUriMatcher();
	private HockeyDatabase mOpenHelper;
	
	private static final int TEAMS = 100;
	private static final int MATCHES = 200;
	private static final int MATCHES_DETAILED = 250;
	
	private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, Teams.PATH, TEAMS);
        matcher.addURI(authority, Matches.PATH, MATCHES);
        matcher.addURI(authority, MatchesDetailed.PATH, MATCHES_DETAILED);
        return matcher;
    }
	
	@Override
	public boolean onCreate() {
		mOpenHelper = new HockeyDatabase(getContext());
        return true;
	}
	
	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch(match){
		case TEAMS:
			return Teams.CONTENT_TYPE;
		case MATCHES:
			return Teams.CONTENT_TYPE;
		case MATCHES_DETAILED:
			return MatchesDetailed.CONTENT_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        switch(match){
        case TEAMS:
        	db.insertOrThrow(Tables.TEAMS, null, values);
        	getContext().getContentResolver().notifyChange(uri, null, false);
        	return uri.buildUpon().appendPath(values.getAsString(Teams.TEAM_ID)).build();
        case MATCHES:
        	db.insertOrThrow(Tables.MATCHES, null, values);
        	getContext().getContentResolver().notifyChange(uri, null, false);
        	return uri.buildUpon().appendPath(values.getAsString(Matches.MATCH_ID)).build();
        default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        switch(match){
        case TEAMS:
        	return db.query(Tables.TEAMS, projection, selection, selectionArgs, null, null, sortOrder);
        case MATCHES:
        	return db.query(Tables.MATCHES, projection, selection, selectionArgs, null, null, sortOrder);
        case MATCHES_DETAILED:
        	return db.rawQuery(Subquery.MATCHES_DETAILED, null);
        default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException("Unknown uri: " + uri);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException("Unknown uri: " + uri);
	}
	
	private interface Subquery {
		String MATCHES_DETAILED = "SELECT"
				+ " m.*,"
				+ " t1.team_name AS " + MatchesDetailed.MATCH_FIRST_TEAM_NAME + ","
				+ " t1.team_logo_url AS " + MatchesDetailed.MATCH_FIRST_TEAM_LOGO_URL + ","
				+ " t2.team_name AS " + MatchesDetailed.MATCH_SECOND_TEAM_NAME + ","
				+ " t2.team_logo_url AS " + MatchesDetailed.MATCH_SECOND_TEAM_LOGO_URL
				+ " FROM matches AS m"
				+ " INNER JOIN teams AS t1 ON m." + MatchColumns.MATCH_FIRST_TEAM_ID + "=t1." + TeamColumns.TEAM_ID
				+ " INNER JOIN teams AS t2 ON m." + MatchColumns.MATCH_SECOND_TEAM_ID + "=t2." + TeamColumns.TEAM_ID
				+ " ORDER BY " + MatchColumns.MATCH_NUMBER + " ASC";
	}

}
