package com.example.ivos.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int totalScore = 0;
    private int imageQuestionsScore = 0;
    private int textQuestionsScore = 0;
    private int soundQuestionsScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the images category
        TextView images = (TextView) findViewById(R.id.image_question_textbuton);

        // Set a click listener on that View
        images.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the images category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link ImagesActivity}
                Intent imagesIntent = new Intent(MainActivity.this, ImagesActivity.class);

                // Start the new activity
                startActivity(imagesIntent);
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
                Intent textsIntent = new Intent(MainActivity.this, TextsActivity.class);

                // Start the new activity
                startActivity(textsIntent);
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
                Intent soundsIntent = new Intent(MainActivity.this, SoundsActivity.class);

                // Start the new activity
                startActivity(soundsIntent);
            }
        });
    }
}
