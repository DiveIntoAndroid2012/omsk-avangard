package ru.omsu.avangard.omsk;

public class Team {
	private String name;
	private String logo;
	
	public Team(String name, String logo) {
		super();
		this.name = name;
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public String getLogo() {
		return logo;
	}
	
}
