package org.nathanbraun.minemoba.game;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Game {
		
	private long startTime;
	private ArrayList<Team> teams;
	private ArrayList<Player> players;
	
	public Game(ArrayList<Team> teams, ArrayList<Player> players) {
		this.teams = teams;
		this.players = players;
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public void finish() {
		
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

}
