package com.example.ivos.quiz_app;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TextsActivity extends AppCompatActivity {
     // Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;
    // textQuestionScore counter
    public int textQuestionScore = 0;
    // open question checker (not answered)
    public boolean[] openQuestion = {true, true, true, true};
    // closed question counter
    public int closedQuestionCounter = 0;
    //initialization SharedPreferences
    SharedPreferences sharedPref = null;

    private long backPressedTime = 0;
    //back button handling - sending score value
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, R.string.Press_back_again_to_exit,Toast.LENGTH_SHORT).show();
            return;
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
        //save textScore to send back to parent
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("textQuestionsScore", textQuestionScore);
        editor.putBoolean("textCategoryClosed", true);
        editor.apply();
        super.onBackPressed();
    }

    @Override
    //back button on action bar handling by redirect to onBackPressed()
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);
        // Initialize SharedPreferences
        sharedPref = getSharedPreferences("com.example.ivos.quiz_app.pref1", MODE_PRIVATE);
        // Create a list of questions
        final ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the city with second largest population in Czech Republic?",
                "Brno", "Plzeň (Pilsen)", "Praha", 1));
        questions.add(new Question("Who was Czech Republic’s first president?",
                "Alexander Dubceck", "Vaclav Havel", "Ludvik Svoboda", 2));
        questions.add(new Question("Which country is to the west of Czech Republic?",
                "Italy", "Germany", "France", 2));
        questions.add(new Question("How tall is Sněžka, the highest peak in the Czech Republic?",
                "1402 m a.s.l.", "1502 m a.s.l.", "1602 m a.s.l.", 3));
         /* Create an {@link QuestionAdapter}, whose data source is a list of {@link Question}s. The
          * adapter knows how to create list items for each item in the list.
          */
        QuestionAdapter adapter = new QuestionAdapter(this, questions);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // question_list.xml.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);
        // Make the {@link ListView} use the {@link QuestionAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Question} in the list.
        listView.setAdapter(adapter);
        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //if question is closed do nothing
                if (!openQuestion[position]) return;
                // Get the {@link Question} object at the given position the user clicked on
                Question question = questions.get(position);
                // get the radioGroup
                RadioGroup answerRadioGroup = (RadioGroup) view.findViewById(R.id.answer_radio_group);
                // save id of checked button
                int checkButtonID = answerRadioGroup.getCheckedRadioButtonId();
                //if no button check yet do nothing
                if (checkButtonID == -1) return;

                RadioButton checkButton = (RadioButton) view.findViewById(checkButtonID);
                int checkButtonPosition = 0;
                // which is checked 1 or 2 or 3
                switch (checkButtonID) {
                    case R.id.radio_button_answer_a:
                        checkButtonPosition = 1;
                        break;
                    case R.id.radio_button_answer_b:
                        checkButtonPosition = 2;
                        break;
                    case R.id.radio_button_answer_c:
                        checkButtonPosition = 3;
                        break;
                }
                LinearLayout container_list_item = (LinearLayout) view.findViewById(R.id.container);
                //if it is the right answer then colored text and background to blue else to red
                if (checkButtonPosition == question.getqRightAnswerPosition()) {
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with trueAnswer sound
                    mMediaPlayer = MediaPlayer.create(TextsActivity.this, R.raw.correct);
                    // Start the audio file
                    mMediaPlayer.start();
                    //change textcolor of bad answer and background color of container to blue
                    checkButton.setTextColor(ContextCompat.getColor(
                            getApplicationContext(), R.color.trueAnswer));
                    container_list_item.setBackgroundColor(ContextCompat.getColor(
                            getApplicationContext(), R.color.trueAnswerBackground));
                    setTitle("Text questions score = " + ++textQuestionScore + " / " + openQuestion.length);
                } else {
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with false answer
                    mMediaPlayer = MediaPlayer.create(TextsActivity.this, R.raw.wrong);
                    // Start the audio file
                    mMediaPlayer.start();
                    //change text color of bad answer and background color of container to red
                    checkButton.setTextColor(ContextCompat.getColor(
                            getApplicationContext(), R.color.falseAnswer));
                    container_list_item.setBackgroundColor(ContextCompat.getColor(
                            getApplicationContext(), R.color.falseAnswerBackground));
                }
                //disable radio button
                for (int i = 0; i < answerRadioGroup.getChildCount(); i++) {
                    (answerRadioGroup.getChildAt(i)).setEnabled(false);
                }
                //close this question
                openQuestion[position] = false;
                closedQuestionCounter++;
                //disable hints
                TextView submitButton = (TextView) view.findViewById(R.id.submit_button);
                submitButton.setVisibility(View.GONE);
                //disable horizontal line
                TextView horizontalLine2 = (TextView) view.findViewById(R.id.horizontal_line2_textview);
                horizontalLine2.setVisibility(View.GONE);
                if (closedQuestionCounter == openQuestion.length) {
                    Toast.makeText(TextsActivity.this,"Last question answered. Go back.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}