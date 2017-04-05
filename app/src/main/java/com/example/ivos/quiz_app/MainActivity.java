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
    public String userName = "your_name";

    public int imageQuestionsScore = 0;
    public int imageQuestionsCount = 0;
    public boolean imageAnswered = false;

    public int textQuestionsScore = 0;
    public int textQuestionsCount = 0;
    public boolean textAnswered = false;

    public int soundQuestionsScore = 0;
    public int soundQuestionsCount = 0;
    public boolean soundAnswered = false;

    public int userTotalScore = 0;

    SharedPreferences sharedPref = null;

    private long backPressedTime = 0;

    static final String UN = "userName";
    static final String IQS = "imageQuestionsScore";
    static final String IQC = "imageQuestionsCount";
    static final String IA = "imageAnswered";
    static final String TQS = "textQuestionsScore";
    static final String TQC = "textQuestionsCount";
    static final String TA = "textAnswered";
    static final String SQS = "soundQuestionsScore";
    static final String SQC = "soundQuestionsCount";
    static final String SA = "soundAnswered";


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(UN, userName);
        savedInstanceState.putInt(IQS, imageQuestionsScore);
        savedInstanceState.putInt(IQC, imageQuestionsCount);
        savedInstanceState.putBoolean(IA, imageAnswered);
        savedInstanceState.putInt(TQS, textQuestionsScore);
        savedInstanceState.putInt(TQC, textQuestionsCount);
        savedInstanceState.putBoolean(TA, textAnswered);
        savedInstanceState.putInt(SQS, soundQuestionsScore);
        savedInstanceState.putInt(SQC, soundQuestionsCount);
        savedInstanceState.putBoolean(SA, soundAnswered);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        userName = savedInstanceState.getString(UN);
        imageQuestionsScore = savedInstanceState.getInt(IQS);
        imageQuestionsCount = savedInstanceState.getInt(IQC);
        imageAnswered = savedInstanceState.getBoolean(IA);
        textQuestionsScore = savedInstanceState.getInt(TQS);
        textQuestionsCount = savedInstanceState.getInt(TQC);
        textAnswered = savedInstanceState.getBoolean(TA);
        soundQuestionsScore = savedInstanceState.getInt(SQS);
        soundQuestionsCount = savedInstanceState.getInt(SQC);
        soundAnswered = savedInstanceState.getBoolean(SA);

        // display data again
        refresh();
    }

    //save sharedPrefImor
    public void saveSharedPref() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("imageQuestionsScore", imageQuestionsScore);
        editor.putInt("textQuestionsScore", textQuestionsScore);
        editor.putInt("soundQuestionsScore", soundQuestionsScore);
        editor.putBoolean("imageAnswered", imageAnswered);
        editor.putBoolean("textAnswered", textAnswered);
        editor.putBoolean("soundAnswered", soundAnswered);
        editor.putString("userName", userName);
        editor.apply();
    }

    //load sharedPrefImor
    public void loadSharedPref() {
        imageQuestionsScore = sharedPref.getInt("imageQuestionsScore", 0);
        textQuestionsScore = sharedPref.getInt("textQuestionsScore", 0);
        soundQuestionsScore = sharedPref.getInt("soundQuestionsScore", 0);
        imageAnswered = sharedPref.getBoolean("imageAnswered", false);
        textAnswered = sharedPref.getBoolean("textAnswered", false);
        soundAnswered = sharedPref.getBoolean("soundAnswered", false);
        userName = sharedPref.getString("userName", getString(R.string.default_user_name));
        userTotalScore = imageQuestionsScore + textQuestionsScore + soundQuestionsScore;
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
        totalScoreTV.setText(String.valueOf(userTotalScore));

        if (!userName.equals("your_name")) {
            TextView userNameTextView = (TextView) findViewById(R.id.user_name);
            userNameTextView.setText(userName);
            TextView totalScoreTextView = (TextView) findViewById(R.id.total_score);
            String userNameTextMessage = userName + getString(R.string.totalScoreText2);
            totalScoreTextView.setText(userNameTextMessage);
        }
    }

    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press back again to exit",
                    Toast.LENGTH_SHORT).show();
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSharedPref();
        refresh();
    }

    //help debug tool
    public void msg(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        //set SharedPreferences nad if it is first time then set default values
        sharedPref = getSharedPreferences("com.example.ivos.quiz_app.pref1", MODE_PRIVATE);
        if (savedInstanceState == null) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();
                loadSharedPref();
                refresh();
        }

        // change the hint Text in EditText View OnFocus and back
        userNameTextView = (TextView) findViewById(R.id.user_name);
        if (!userName.equals("your_name")) userNameTextView.setText(userName);

        userNameTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    userNameTextView.setHint("write_your_name_now");
                } else {
                    userNameTextView.setHint("your_name");
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
                if (!imageAnswered) {
                    //save score before start intent by Shared Preferences
                    saveSharedPref();
                    Intent imagesIntent = new Intent(MainActivity.this, ImagesActivity.class);
                    /*imagesIntent.putExtra("textQuestionsScore", String.valueOf(textQuestionsScore));*/
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
                if (!textAnswered) {
                    //save score before start intent by Shared Preferences
                    saveSharedPref();
                    Intent textsIntent = new Intent(MainActivity.this, TextsActivity.class);
                    /*textsIntent.putExtra("textQuestionsScore", String.valueOf(textQuestionsScore));
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
                if (!soundAnswered) {
                    //save score before start intent by Shared Preferences
                    saveSharedPref();
                    Intent soundsIntent = new Intent(MainActivity.this, SoundsActivity.class);
                   /* soundsIntent.putExtra("soundQuestionsScore", String.valueOf(soundQuestionsScore));*/
                    // Start the new activity
                    startActivity(soundsIntent);
                }
            }
        });
    }
}
