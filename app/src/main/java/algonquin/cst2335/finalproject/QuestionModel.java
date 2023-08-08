package algonquin.cst2335.finalproject;

import java.util.ArrayList;

/**
 * Represents a model class for quiz questions.
 */
class QuestionModel {
    String category;
    String correct_answer;
    String user_selected_answer = "";
    int user_selected_option = -1;
    String difficulty;
    ArrayList<String> incorrect_answers;
    String question;
    String type;

    /**
     * Get the category of the question.
     *
     * @return The category of the question.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the category of the question.
     *
     * @param category The category of the question.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get the correct answer for the question.
     *
     * @return The correct answer for the question.
     */
    public String getCorrect_answer() {
        return correct_answer;
    }

    /**
     * Set the correct answer for the question.
     *
     * @param correct_answer The correct answer for the question.
     */
    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    /**
     * Get the difficulty level of the question.
     *
     * @return The difficulty level of the question.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Set the difficulty level of the question.
     *
     * @param difficulty The difficulty level of the question.
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Get the list of incorrect answers for the question.
     *
     * @return The list of incorrect answers for the question.
     */
    public ArrayList<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    /**
     * Set the list of incorrect answers for the question.
     *
     * @param incorrect_answers The list of incorrect answers for the question.
     */
    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    /**
     * Get the text of the question.
     *
     * @return The text of the question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Set the text of the question.
     *
     * @param question The text of the question.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Get the type of the question.
     *
     * @return The type of the question.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the question.
     *
     * @param type The type of the question.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the user-selected answer for the question.
     *
     * @return The user-selected answer for the question.
     */
    public String getUser_selected_answer() {
        return user_selected_answer;
    }

    /**
     * Set the user-selected answer for the question.
     *
     * @param user_selected_answer The user-selected answer for the question.
     */
    public void setUser_selected_answer(String user_selected_answer) {
        this.user_selected_answer = user_selected_answer;
    }
}
