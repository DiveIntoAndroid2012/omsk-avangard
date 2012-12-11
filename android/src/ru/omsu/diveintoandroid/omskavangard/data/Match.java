package ru.omsu.diveintoandroid.omskavangard.data;

import java.util.Date;

public class Match {

	private Team mTeam1;
	private Team mTeam2;
	private Date mTime;
	private int mTeam1Goals = -1;
	private int mTeam2Goals = -1;

	public Match(Team team1, Team team2, Date startTime, String result) {
		this.mTeam1 = team1;
		this.mTeam2 = team2;
		this.mTime = startTime;
		parseResult(result);
	}

	public Team getTeam1() {
		return mTeam1;
	}

	public Team getTeam2() {
		return mTeam2;
	}

	public Date getStartTime() {
		return mTime;
	}

	public void parseResult(String result) {
		if (result != null) {
			String[] goals = result.split(":");
			if (goals.length == 2) {
				mTeam1Goals = Integer.parseInt(goals[0]);
				mTeam2Goals = Integer.parseInt(goals[1]);
			}
		}
	}

	public int getTeam1Goals() {
		return mTeam1Goals;
	}

	public int getTeam2Goals() {
		return mTeam2Goals;
	}

	public boolean hasResult() {
		return mTeam1Goals >= 0 && mTeam2Goals >= 0;
	}
}
