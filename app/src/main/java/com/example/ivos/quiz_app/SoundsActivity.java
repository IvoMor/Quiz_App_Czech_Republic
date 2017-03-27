package com.example.ivos.quiz_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SoundsActivity extends AppCompatActivity {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);

        // Create a list of questions
        final ArrayList<Question> questions = new ArrayList<Question>();
//        questions.add(new Question("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
//        questions.add(new Question("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,
//                R.raw.color_mustard_yellow));
//        questions.add(new Question("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,
//                R.raw.color_dusty_yellow));

        // Create an {@link QuestionAdapter}, whose data source is a list of {@link Question}s. The
        // adapter knows how to create list items for each item in the list.
        QuestionAdapter adapter = new QuestionAdapter(this, questions, R.color.category_sounds);

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

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current question
                mMediaPlayer = MediaPlayer.create(SoundsActivity.this, question.getAudioResourceId());

                // Start the audio file
                mMediaPlayer.start();
            }
        });
    }
}




