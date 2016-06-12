package pruebaServer;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionsByCategoryPackage implements Serializable, Package {
	
	private static final long serialVersionUID = -5236502747742644840L;
	private static final Integer PACKAGEID = 9;
	private String category;
	private ArrayList<Question> questions;
	
	public QuestionsByCategoryPackage(String category) {
		this.category = category;
	}

	public Integer getPackageID() {
		return PACKAGEID;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions){
		this.questions = questions;
	}

	public String getCategory() {
		return category;
	}
	
}
