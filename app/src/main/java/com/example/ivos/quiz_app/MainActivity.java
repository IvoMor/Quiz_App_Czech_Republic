package com.example.ivos.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int totalScore = 0;
    public int imageQuestionsScore = 1;
    public int imageQuestionsCount = 0;
    public boolean imageAnswered = false;

    public int textQuestionsScore = 2;
    public int textQuestionsCount = 0;
    public boolean textAnswered = false;

    public int soundQuestionsScore = 3;
    public int soundQuestionsCount = 0;
    public boolean soundAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        //back send scores
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Toast.makeText(MainActivity.this, extras.getString("sendFrom"), Toast.LENGTH_SHORT).show();
            switch (extras.getString("sendFrom"))  {
                case "Image":
                    imageQuestionsScore = Integer.valueOf(extras.getString("imageQuestionsScore"));
                    imageQuestionsCount = Integer.valueOf(extras.getString("imageQuestionsCount"));
                    TextView imageScore = (TextView) findViewById(R.id.image_score_counter);
                    imageScore.setText(String.valueOf(imageQuestionsScore));
                    imageAnswered = true;
                    break;
                case "Text":
                    textQuestionsScore = Integer.valueOf(extras.getString("textQuestionsScore"));
                    textQuestionsCount = Integer.valueOf(extras.getString("textQuestionsCount"));
                    //Toast.makeText(MainActivity.this, String.valueOf(textQuestionsScore), Toast.LENGTH_SHORT).show();
                    TextView textScore = (TextView) findViewById(R.id.text_score_counter);
                    textScore.setText(String.valueOf(textQuestionsScore));
                    textAnswered = true;
                    break;
                case "Sound":
                    soundQuestionsScore = Integer.valueOf(extras.getString("soundQuestionsScore"));
                    soundQuestionsCount = Integer.valueOf(extras.getString("soundQuestionsCount"));
                    TextView soundScore = (TextView) findViewById(R.id.sound_score_counter);
                    soundScore.setText(String.valueOf(soundQuestionsScore));
                    soundAnswered = true;
                    break;
            }
            totalScore = imageQuestionsScore + textQuestionsScore + soundQuestionsScore;
            TextView totalScoreView = (TextView) findViewById(R.id.total_score_counter);
            totalScoreView.setText(String.valueOf(totalScore));
        }

        // Find the View that shows the images category
        TextView images = (TextView) findViewById(R.id.image_question_textbuton);

        // Set a click listener on that View
        images.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the images category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link ImagesActivity}
                if (!imageAnswered) {
                    Intent imagesIntent = new Intent(MainActivity.this, ImagesActivity.class);
                    imagesIntent.putExtra("textQuestionsScore", String.valueOf(textQuestionsScore));
                    // Start the new activity
                    startActivity(imagesIntent);
                }
            }
        });


        // Find the View that shows the colors category
        TextView texts = (TextView) findViewById(R.id.text_question_textbuton);

        // Set a click listener on that View
        texts.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link TextsActivity}
                if (!textAnswered) {
                    Intent textsIntent = new Intent(MainActivity.this, TextsActivity.class);
                    textsIntent.putExtra("textQuestionsScore", String.valueOf(textQuestionsScore));
                    // Start the new activity
                    startActivity(textsIntent);
                }
            }
        });

        // Find the View that shows the sounds category
        TextView sound = (TextView) findViewById(R.id.sound_question_textbuton);

        // Set a click listener on that View
        sound.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link SoundsActivity}
                if (!soundAnswered) {
                    Intent soundsIntent = new Intent(MainActivity.this, SoundsActivity.class);
                    soundsIntent.putExtra("soundQuestionsScore", String.valueOf(soundQuestionsScore));
                    // Start the new activity
                    startActivity(soundsIntent);
                }
            }
        });
    }
}
