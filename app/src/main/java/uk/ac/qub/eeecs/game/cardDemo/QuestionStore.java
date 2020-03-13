package uk.ac.qub.eeecs.game.cardDemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;

public class QuestionStore {

    private FileIO mFileIO;

    private Game mGame;

    //Question ID
    private String questionID;

    //Question
    private String question;

    //Answer to Question
    private String answer;

    //questionStore Constructor
    public QuestionStore(Game game) {

        mGame = game;
        mFileIO = mGame.getFileIO();
        loadQuestionList("txt/assets/QuestionList.JSON");

    }

    /**
     * The JSON file assumes the following format:
     * <p>
     * "questionID": String
     * "question": String
     * "answer": String
     */

    public void loadQuestionList(String assetsToLoadJSONFile) {
        // Attempt to load in the JSON asset details
        String loadedJSON;
        try {
            loadedJSON = mFileIO.loadJSON(assetsToLoadJSONFile);
        } catch (IOException e) {
            throw new RuntimeException(
                    "QuestionList.constructor: Cannot load JSON [" + assetsToLoadJSONFile + "]");
        }

        // Attempt to extract the JSON information
        try {
            JSONObject settings = new JSONObject(loadedJSON);
            JSONArray assets = settings.getJSONArray("assets");

            // Load in each asset
            for (int idx = 0; idx < assets.length(); idx++) {
                questionID = assets.getJSONObject(idx).getString("questionID");
                question = assets.getJSONObject(idx).getString("question");
                answer = assets.getJSONObject(idx).getString("answer");

            }

        } catch (JSONException | IllegalArgumentException e) {
            throw new RuntimeException(
                    "QuestionStore.constructor: JSON parsing error [" + e.getMessage() + "]");
        }
    }

}