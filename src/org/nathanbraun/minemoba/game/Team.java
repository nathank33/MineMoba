package org.nathanbraun.minemoba.game;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Team {
		
	private ArrayList<Player> players;
	private boolean isLeftTeam;
	
	public Team() {
		this(new ArrayList<Player>());
	}
	
	public Team(ArrayList<Player> players) {
		this.players = players;
	}

	public boolean isLeftTeam() {
		return isLeftTeam;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}

	public void setLeftTeam(boolean isLeftTeam) {
		this.isLeftTeam = isLeftTeam;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
}
