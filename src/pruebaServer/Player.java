package pruebaServer;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {

	private static final long serialVersionUID = -36672672438995887L;
	private Integer id;
	private String name;
	private Integer score;
	private String answer;
	private Boolean disconnectedWhilePlaying;
	
	public Player(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.score = 0;
		this.answer = null; 
		disconnectedWhilePlaying = false;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Boolean disconnectedWhilePlaying() {
		return disconnectedWhilePlaying;
	}
	
	public void setDisconnect() {
		disconnectedWhilePlaying = true;
	}
	
	public void increaseScore() {
		score += 1;
	}

	public int compareTo(Player otherPlayer) {
		if(this.score < otherPlayer.score) return 1;
		if(this.score > otherPlayer.score) return -1;
		return 0;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	
	
}
