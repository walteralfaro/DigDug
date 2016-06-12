package pruebaServer;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable, Package {

	private static final long serialVersionUID = 946827401315759467L;
	private static final Integer PACKAGEID = 6;
	private Integer id;
	private String question;
	private String category;
	private String correctAnswer;
	private ArrayList<String> wrongAnswers = new ArrayList<String>();

	public Question(Integer id, String question, String category, String correctAnswer,
			ArrayList<String> wrongAnswers) {
		this.id = id;
		this.question = question;
		this.category = category;
		this.correctAnswer = correctAnswer;
		this.wrongAnswers = wrongAnswers;
	}
	
	public Question(String question){
		this.question = question;
	}

	public Question(Integer id, String question) {
		this.id = id;
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}

	public String getCategory() {
		return category;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public ArrayList<String> getWrongAnswers() {
		return wrongAnswers;
	}
	
	public Integer getID(){
		return id;
	}

	public Integer getPackageID() {
		return PACKAGEID;
	}
	
	public String toString(){
		return question;
	}
}
