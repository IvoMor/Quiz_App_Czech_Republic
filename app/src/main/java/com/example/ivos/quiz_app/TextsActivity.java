package com.example.ivos.quiz_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class TextsActivity extends AppCompatActivity {

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);

        // Create a list of questions
        final ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is the city with second largest population in Czech Republic?",
                "Brno", "Plzeň (Pilsen)", "Praha", 1));
        questions.add(new Question("Who was Czech Republic’s first president?",
                "Alexander Dubceck", "Vaclav Havel", "Ludvik Svoboda", 2));
        questions.add(new Question("Which country is to the west of Czech Republic?",
                "Italy", "Germany", "France", 2));
        questions.add(new Question("How tall is Sněžka, the highest peak in the Czech Republic?",
                "1402 m a.s.l.", "1502 m a.s.l.", "1602 m a.s.l.", 3));


        // Create an {@link QuestionAdapter}, whose data source is a list of {@link Question}s. The
        // adapter knows how to create list items for each item in the list.
        QuestionAdapter adapter = new QuestionAdapter(this, questions, R.color.category_texts);

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
                // Get the {@link Question} object at the given position the user clicked on
                Question question = questions.get(position);

                // debugging tools
                Toast.makeText( TextsActivity.this, "Click", Toast.LENGTH_SHORT  ).show();


                // get the radioGroup
                RadioGroup answerRadioGroup =(RadioGroup) view.findViewById(R.id.answer_radio_group);
                // save id of checked button
                int checkButtonID = answerRadioGroup.getCheckedRadioButtonId();

                RadioButton checkButton = (RadioButton) view.findViewById(checkButtonID);

                int checkButtonPosition = 0;
                // which is checked 1 or 2 or 3
                switch (checkButtonID) {
                    case R.id.radio_button_answer_a : checkButtonPosition = 1;
                        break;
                    case R.id.radio_button_answer_b : checkButtonPosition = 2;
                        break;
                    case R.id.radio_button_answer_c : checkButtonPosition = 3;
                        break;
                }

                //if it is the right answer then colored text to green else to red
                if (checkButtonPosition == question.getqRightAnswerPosition()) {
                    checkButton.setTextColor(getResources().getColor(R.color.trueAnswer));
                }  else {
                    // if it is checked then false answer
                    if (checkButtonPosition >0) checkButton.setTextColor(getResources().getColor(R.color.falseAnswer));
                }

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current question - teh
               // mMediaPlayer = MediaPlayer.create(TextsActivity.this, question.getAudioResourceId());         TODO some noise

                // Start the audio file
                //mMediaPlayer.start();
            }
        });
    }
}