package org.nathanbraun.minemoba.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nathanbraun.minemoba.MineMoba;

public class GameManager {
	
	private static final int PLAYERS_PER_TEAM = 1; // 1 for testing
	private static final int TEAMS_PER_GAME = 2;
	private static final long QUEUE_CHECK_TIME = 20; // 20 ticks = 1 second
	
	private static final HashSet<Player> ACTIVE_PLAYERS = new HashSet<>();
	private static final LinkedList<Player> QUEUED_PLAYERS = new LinkedList<>();
	private static final HashSet<Game> ACTIVE_GAMES = new HashSet<>();
	private static final HashSet<Game> FINISHED_GAMES = new HashSet<>();
	private static final HashMap<Player, HashSet<Player>> QUEUED_WITH_FRIENDS = new HashMap<>(); // TODO: fully implement in queueCheckStatus
	
	public GameManager() {
		startQueueManager();
	}
	
	/*
	 * Starts a thread that constantly checks the queued players to determine
	 * if it is possible to start a new game.
	 */
	private void startQueueManager() {
		long delay = 0;
		long period = QUEUE_CHECK_TIME;
		BukkitRunnable thread = new BukkitRunnable() {
			@Override
			public void run() {
				checkQueueStatus();
			}
		};
		thread.runTaskTimer(MineMoba.plugin, delay, period);
	}
	
	public void checkQueueStatus() {
		int requiredPlayers = PLAYERS_PER_TEAM * TEAMS_PER_GAME;
		if (QUEUED_PLAYERS.size() < requiredPlayers) {
			return;
		}
		
		ArrayList<Player> players = new ArrayList<>();
		for (int i = 0; i < requiredPlayers; i++) {
			players.add(QUEUED_PLAYERS.removeFirst());
		}
		
		Collections.shuffle(players);
		ArrayList<Team> teams = new ArrayList<>();
		int playerIndex = 0;
		
		for (int i = 0; i < TEAMS_PER_GAME; i++) {
			Team team = new Team();
			for (int j = 0; j < PLAYERS_PER_TEAM; j++) {
				team.addPlayer(players.get(playerIndex));
				playerIndex++;
			}
			teams.add(team);
		}
		
		Game game = new Game(teams, players);
		game.start();
		ACTIVE_GAMES.add(game);
	}
	
	/**
	 * Queues the player and returns true if it was successful.
	 */
	public static boolean addToQueue(Player player) {
		if (player != null) {
			QUEUED_PLAYERS.add(player);
			return true;
		}
		return false;
	}
	
	public static boolean addToQueue(Player player, HashSet<Player> friends) {
		if (addToQueue(player)) {
			QUEUED_WITH_FRIENDS.put(player, friends);
			return true;
		}
		return false;
	}
	
	public static void removeFromQueue(Player player) {
		if (player != null) {
			QUEUED_PLAYERS.remove(player);
			QUEUED_WITH_FRIENDS.remove(player);
		}
	}
	
	public static boolean isInGame(Player player) {
		return player != null ? ACTIVE_PLAYERS.contains(player) : false;
	}
	
	public static boolean isInQueue(Player player) {
		return player != null ? QUEUED_PLAYERS.contains(player) : false;
	}
	
	public static Set<Game> getActiveGames() {
		return ACTIVE_GAMES;
	}
	
	public static Set<Game> getFinishedGames() {
		return FINISHED_GAMES;
	}
}
