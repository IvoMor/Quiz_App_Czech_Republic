package com.example.ivos.quiz_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //initialization
    private TextView userNameTextView = null;
    public String userName = "Guest";

    public int imageQuestionsScore = 0;
    public boolean imageCategoryClosed = false;

    public int textQuestionsScore = 0;
    public boolean textCategoryClosed = false;

    public int soundQuestionsScore = 0;
    public boolean soundCategoryClosed = false;

    public int userTotalScore() {
        return imageQuestionsScore + textQuestionsScore + soundQuestionsScore;
    }

    public int userHighScore = 0;

    SharedPreferences sharedPref = null;

    private long backPressedTime = 0;

    static final String UN = "userName";
    static final String IQS = "imageQuestionsScore";
    static final String IA = "imageCategoryClosed";
    static final String TQS = "textQuestionsScore";
    static final String TA = "textCategoryClosed";
    static final String SQS = "soundQuestionsScore";
    static final String SA = "soundCategoryClosed";
    static final String UHS = "userHighScore";


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(UN, userName);
        savedInstanceState.putInt(IQS, imageQuestionsScore);
        savedInstanceState.putBoolean(IA, imageCategoryClosed);
        savedInstanceState.putInt(TQS, textQuestionsScore);
        savedInstanceState.putBoolean(TA, textCategoryClosed);
        savedInstanceState.putInt(SQS, soundQuestionsScore);
        savedInstanceState.putBoolean(SA, soundCategoryClosed);
        savedInstanceState.putInt(UHS, userHighScore);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        userName = savedInstanceState.getString(UN);
        imageQuestionsScore = savedInstanceState.getInt(IQS);
        imageCategoryClosed = savedInstanceState.getBoolean(IA);
        textQuestionsScore = savedInstanceState.getInt(TQS);
        textCategoryClosed = savedInstanceState.getBoolean(TA);
        soundQuestionsScore = savedInstanceState.getInt(SQS);
        soundCategoryClosed = savedInstanceState.getBoolean(SA);
        userHighScore = savedInstanceState.getInt(UHS);

        // display data again
        refresh();
    }

    //save sharedPrefImor
    public void saveSharedPref() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("imageQuestionsScore", imageQuestionsScore);
        editor.putInt("textQuestionsScore", textQuestionsScore);
        editor.putInt("soundQuestionsScore", soundQuestionsScore);
        editor.putInt("userHighScore", userHighScore);
        editor.putBoolean("imageCategoryClosed", imageCategoryClosed);
        editor.putBoolean("textCategoryClosed", textCategoryClosed);
        editor.putBoolean("soundCategoryClosed", soundCategoryClosed);
        editor.putString("userName", userName);
        editor.apply();
    }

    //load sharedPrefImor
    public void loadSharedPref() {
        imageQuestionsScore = sharedPref.getInt("imageQuestionsScore", 0);
        textQuestionsScore = sharedPref.getInt("textQuestionsScore", 0);
        soundQuestionsScore = sharedPref.getInt("soundQuestionsScore", 0);
        userHighScore = sharedPref.getInt("userHighScore", 0);
        imageCategoryClosed = sharedPref.getBoolean("imageCategoryClosed", false);
        textCategoryClosed = sharedPref.getBoolean("textCategoryClosed", false);
        soundCategoryClosed = sharedPref.getBoolean("soundCategoryClosed", false);
        userName = sharedPref.getString("userName", getString(R.string.default_user_name));
    }

    //refresh all activity changing values
    public void refresh() {
        TextView imageScoreTV = (TextView) findViewById(R.id.image_score_counter);
        imageScoreTV.setText(String.valueOf(imageQuestionsScore));

        TextView textScoreTV = (TextView) findViewById(R.id.text_score_counter);
        textScoreTV.setText(String.valueOf(textQuestionsScore));

        TextView soundScoreTV = (TextView) findViewById(R.id.sound_score_counter);
        soundScoreTV.setText(String.valueOf(soundQuestionsScore));

        TextView totalScoreTV = (TextView) findViewById(R.id.total_score_counter);
        totalScoreTV.setText(String.valueOf(userTotalScore()));

        TextView highScoreTV = (TextView) findViewById(R.id.high_score_counter);
        highScoreTV.setText(String.valueOf(userHighScore));

        if (!userName.equals("Guest")) {
            TextView userNameTextView = (TextView) findViewById(R.id.user_name);
            userNameTextView.setText(userName);
            TextView totalScoreTextView = (TextView) findViewById(R.id.total_score);
            String userNameTextMessage = userName + getString(R.string.totalScoreText2);
            totalScoreTextView.setText(userNameTextMessage);
        }
    }

    public void clearQuiz() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        loadSharedPref();
        refresh();
    }

    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, R.string.Press_back_again_to_exit,Toast.LENGTH_SHORT).show();
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }

    public void checkHighScore_ReNewQuiz() {
        if (userHighScore < userTotalScore()) {
            userHighScore = userTotalScore();
            //new high score message
            Toast.makeText(MainActivity.this, "Congrats " + userName + ". It's a new high score." , Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Not bad " + userName + ". You've got " + String.valueOf(userTotalScore()) + " points.", Toast.LENGTH_LONG).show();
        }
        //renew quiz but high score and user stay
        Toast.makeText(MainActivity.this, "So " + userName + " next try?" , Toast.LENGTH_LONG).show();
        int saveUserHighScore = userHighScore;
        String saveUserName = userName;
        clearQuiz();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userHighScore", saveUserHighScore);
        editor.putString("userName",saveUserName);
        editor.apply();
        userHighScore = saveUserHighScore;
        userName = saveUserName;
    }

    //when come back from intent
    @Override
    protected void onResume() {
        super.onResume();
        loadSharedPref();
        //if quiz is finished = all categories are answered > check the highscore and renew quiz
        if (endQuizCheck()) checkHighScore_ReNewQuiz();
        refresh();
    }
    //if all categories are answered - quiz end comes = returns true
    private boolean endQuizCheck() {
        return imageCategoryClosed & textCategoryClosed & soundCategoryClosed;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        //set SharedPreferences nad if it is first time then set default values
        sharedPref = getSharedPreferences("com.example.ivos.quiz_app.pref1", MODE_PRIVATE);
        if (savedInstanceState == null)  clearQuiz();

        // change the hint Text in EditText View OnFocus and back
        userNameTextView = (TextView) findViewById(R.id.user_name);
        if (!userName.equals("Guest")) userNameTextView.setText(userName);
        userNameTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    userNameTextView.setHint(getString(R.string.name_request));
                } else {
                    userNameTextView.setHint("Guest");
                }
            }
        });
        // user's total score line handling after userName change
        userNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                TextView totalScoreTextView = (TextView) findViewById(R.id.total_score);
                userName = s.toString();
                String userNameTextMessage = userName + getString(R.string.totalScoreText2);
                totalScoreTextView.setText(userNameTextMessage);
                userNameTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_dark_color));
                userNameTextView.setCursorVisible(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                userNameTextView.setCursorVisible(true);
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
        // Find the View that shows the images category
        TextView images = (TextView) findViewById(R.id.image_question_textbuton);

        // Set a click listener on that View
        images.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the images category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link ImagesActivity}
                if (!imageCategoryClosed) {
                    //save variables before start intent by Shared Preferences
                    saveSharedPref();
                    Intent imagesIntent = new Intent(MainActivity.this, ImagesActivity.class);
                    // Start the new activity
                    startActivity(imagesIntent);
                }
            }
        });
        // Find the View that shows the colors category
        TextView texts = (TextView) findViewById(R.id.text_question_textbuton);

        // Set a click listener on that View
        texts.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link TextsActivity}
                if (!textCategoryClosed) {
                    //save variables before start intent by Shared Preferences
                    saveSharedPref();
                    Intent textsIntent = new Intent(MainActivity.this, TextsActivity.class);
                    // Start the new activity*/
                    startActivity(textsIntent);
                }
            }
        });
        // Find the View that shows the sounds category
        TextView sound = (TextView) findViewById(R.id.sound_question_textbuton);
        // Set a click listener on that View
        sound.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link SoundsActivity}
                if (!soundCategoryClosed) {
                    //save variables before start intent by Shared Preferences
                    saveSharedPref();
                    Intent soundsIntent = new Intent(MainActivity.this, SoundsActivity.class);
                    // Start the new activity
                    startActivity(soundsIntent);
                }
            }
        });
    }
}
