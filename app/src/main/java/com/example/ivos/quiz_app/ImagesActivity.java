package com.example.ivos.quiz_app;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    public int imageScore = 0;

    public boolean[] openQuestion = {true, true, true, true};

    public void onBackPressed() {
        Intent backIntent = new Intent(ImagesActivity.this, MainActivity.class);
        backIntent.putExtra("imageQuestionsScore", String.valueOf(imageScore));
        backIntent.putExtra("imageQuestionsCount", String.valueOf(openQuestion.length));
        backIntent.putExtra("sendFrom", "Image");
        startActivity(backIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);

        // Create a list of image questions (second constructor)
        final ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is the city with second largest population in Czech Republic?",
                "Brno", "Plzeň (Pilsen)", "Praha",R.drawable.flag, true, 1));
        questions.add(new Question("Who was Czech Republic’s first president?",
                "Alexander Dubceck", "Vaclav Havel", "Ludvik Svoboda",R.drawable.flag, true,  2));
        questions.add(new Question("Which country is to the west of Czech Republic?",
                "Italy", "Germany", "France",R.drawable.flag, true,  2));
        questions.add(new Question("How tall is Sněžka, the highest peak in the Czech Republic?",
                "1402 m a.s.l.", "1502 m a.s.l.", "1602 m a.s.l.",R.drawable.flag, true,  3));


        // Create an {@link QuestionAdapter}, whose data source is a list of {@link Question}s. The
        // adapter knows how to create list items for each item in the list.
        QuestionAdapter adapter = new QuestionAdapter(this, questions, R.color.category_images);

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
                Toast.makeText(ImagesActivity.this, "Click", Toast.LENGTH_SHORT).show();

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
                    checkButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.trueAnswer));
                    container_list_item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.trueAnswerBackground));
                    setTitle("Text questions score = " + ++imageScore + " / " + openQuestion.length );
                } else {
                    // false answer
                    checkButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.falseAnswer));
                    container_list_item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.falseAnswerBackground));
                }

                for(int i = 0; i < answerRadioGroup.getChildCount(); i++){
                    (answerRadioGroup.getChildAt(i)).setEnabled(false);
                }


                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current question
                mMediaPlayer = MediaPlayer.create(ImagesActivity.this, R.raw.family_grandfather);

                // Start the audio file
                mMediaPlayer.start();
            }
        });
    }
}
