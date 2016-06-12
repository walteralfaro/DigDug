package pruebaServer;

import java.io.Serializable;

public class AnswerQuestionPackage implements Serializable, Package {
		
	private static final long serialVersionUID = 2102908903826759542L;
	private static final Integer PACKAGEID = 10;
	private String answer;
	private Boolean isCorrect;
	
	public AnswerQuestionPackage(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public AnswerQuestionPackage(String answer) {
		this.answer = answer;
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}

	public String getAnswer() {
		return answer;
	}
	
	public Boolean isCorrect() {
		return isCorrect;
	}
	
}