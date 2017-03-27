package com.example.ivos.quiz_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);

        // Create a list of questions
        final ArrayList<Question> questions = new ArrayList<Question>();
//        questions.add(new Question("one", "lutti", R.drawable.number_one, R.raw.number_one));
//        questions.add(new Question("two", "otiiko", R.drawable.number_two, R.raw.number_two));
//        questions.add(new Question("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
//        questions.add(new Question("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
//        questions.add(new Question("five", "massokka", R.drawable.number_five, R.raw.number_five));
//        questions.add(new Question("six", "temmokka", R.drawable.number_six, R.raw.number_six));
//        questions.add(new Question("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
//        questions.add(new Question("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
//        questions.add(new Question("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
//        questions.add(new Question("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link Question} object at the given position the user clicked on
                Question question = questions.get(position);

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current question
                mMediaPlayer = MediaPlayer.create(ImagesActivity.this, question.getAudioResourceId());

                // Start the audio file
                mMediaPlayer.start();
            }
        });
    }
}
