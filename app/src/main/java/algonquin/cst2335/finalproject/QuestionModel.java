package algonquin.cst2335.finalproject;

import java.util.ArrayList;

class QuestionModel {
    String category;
    String correct_answer;
    String user_selected_answer="";
    int user_selected_option=-1;
    String difficulty;
    ArrayList<String> incorrect_answers;
    String question;
    String type;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_selected_answer() {
        return user_selected_answer;
    }

    public void setUser_selected_answer(String user_selected_answer) {
        this.user_selected_answer = user_selected_answer;
    }
}