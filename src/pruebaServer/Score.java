package pruebaServer;

import java.io.Serializable;

public class Score implements Serializable {

	private static final long serialVersionUID = 3307071588676186223L;
	private Integer gamesPlayed;
	private Integer gamesWon;
	private Integer gamesLost;
	private Integer correctAnswers;
	private Integer wrongAnswers;

	public Score(Integer gamesPlayed,
			Integer gamesWon, Integer gamesLost,
			Integer correctAnswers, Integer wrongAnswers) {
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
		this.correctAnswers = correctAnswers;
		this.wrongAnswers = wrongAnswers;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public Integer getGamesLost() {
		return gamesLost;
	}

	public Integer getCorrectAnswers() {
		return correctAnswers;
	}

	public Integer getWrongAnswers() {
		return wrongAnswers;
	}

	public void increaseGamesPlayed() {
		this.gamesPlayed++;
	}

	public void increaseGamesWon() {
		this.gamesWon++;
	}

	public void increaseGamesLost() {
		this.gamesLost++;
	}

	public void increaseCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers += correctAnswers;
	}

	public void increaseWrongAnswers(Integer wrongAnswers) {
		this.wrongAnswers += wrongAnswers;
	}
	
	
	
}
