package com.aim.domain.game.enums;

public enum GameMode {
	NORMAL("일반"),
	MOVING("무빙"),
	REACT("반응");
	
	private final String gameModeName;

	GameMode(String gameModeName) {
		this.gameModeName = gameModeName;
	}

	public String getgameModeName() {
		return gameModeName;
	}
}
