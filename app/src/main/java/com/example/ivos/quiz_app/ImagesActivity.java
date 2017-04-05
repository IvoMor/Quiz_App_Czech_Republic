package com.example.ivos.quiz_app;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    public int imageQuestionScore = 0;

    public boolean[] openQuestion = {true, true, true, true};

    SharedPreferences sharedPref = null;

    public void onBackPressed() {
        //save textScore to send back to parent
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("imageQuestionsScore", imageQuestionScore);
        editor.putBoolean("imageAnswered", true);
        editor.apply();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);

        // Initialize SharedPreferences
        sharedPref = getSharedPreferences("com.example.ivos.quiz_app.pref1", MODE_PRIVATE);

        // Create a list of image questions (second constructor)
        final ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Which one is Czech Republic neighboring country flag?",
                "Norway", "Denmark", "Poland",R.drawable.poland_flag, true, 3));
        questions.add(new Question("Do you know what is Czech favourite drink?",
                "Beer", "Coca-cola", "Mineral water",R.drawable.beer, true,  1));
        questions.add(new Question("Which one is the Czech famous automotive brand?",
                "Renault", "Audi", "Skoda",R.drawable.skoda, true,  3));
        questions.add(new Question("Who is on the picture? Most known Czech king and Holy Roman Emperor.",
                "Frederick I Barbarossa", "Carl IV", "Charles the Great",R.drawable.karel4, true,  2));

        // Create an {@link QuestionAdapter}, whose data source is a list of {@link Question}s. The
        // adapter knows how to create list items for each item in the list.
        QuestionAdapter adapter = new QuestionAdapter(this, questions);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // question_listlist.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link QuestionAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Question} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {                         //TODO doesn't work
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //if question is closed do nothing
                if (!openQuestion[position]) return;

                // Get the {@link Question} object at the given position the user clicked on
                Question question = questions.get(position);

                // debugging tools
                //Toast.makeText(ImagesActivity.this, "Click", Toast.LENGTH_SHORT).show();

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

                RelativeLayout container_list_item = (RelativeLayout) view.findViewById(R.id.container);
                //if it is the right answer then colored text and background to blue else to red
                if (checkButtonPosition == question.getqRightAnswerPosition()) {
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with trueAnswer sound
                    mMediaPlayer = MediaPlayer.create(ImagesActivity.this, R.raw.correct);
                    // Start the audio file
                    mMediaPlayer.start();

                    checkButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.trueAnswer));
                    container_list_item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.trueAnswerBackground));
                    setTitle("Text questions score = " + ++imageQuestionScore + " / " + openQuestion.length );
                } else {
                    // false answer

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with trueAnswer sound
                    mMediaPlayer = MediaPlayer.create(ImagesActivity.this, R.raw.wrong);
                    // Start the audio file
                    mMediaPlayer.start();

                    checkButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.falseAnswer));
                    container_list_item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.falseAnswerBackground));
                }

                //disable radiobutton
                for(int i = 0; i < answerRadioGroup.getChildCount(); i++){
                    (answerRadioGroup.getChildAt(i)).setEnabled(false);
                }
                //close this question
                openQuestion[position] = false;

                TextView submitButton = (TextView) view.findViewById(R.id.submit_button);
                submitButton.setVisibility(View.GONE);
            }
        });
    }
}
