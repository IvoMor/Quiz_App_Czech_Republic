package com.example.ivos.quiz_app;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link QuestionAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Question} objects.
 */
public class QuestionAdapter extends ArrayAdapter<Question> {

    /** Resource ID for the background color for this list of words */
    //private int mColorResourceId;

    /**
     * Create a new {@link QuestionAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param questions is the list of {@link Question}s to be displayed.

     */
    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Question} object located at this position in the list
        Question currentQuestion = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID question_textview.
        TextView questionTextView = (TextView) listItemView.findViewById(R.id.question_textview);
        // Set the text for question
        questionTextView.setText(currentQuestion.getQuestionText());

        // Find the RadioButton in the list_item.xml layout with the ID radio_button_answer_a.
        RadioButton answerAtextView = (RadioButton) listItemView.findViewById(R.id.radio_button_answer_a);
        // Set the text for answer A
        answerAtextView.setText(currentQuestion.getAnswerText1());

        // Find the RadioButton in the list_item.xml layout with the ID radio_button_answer_a.
        RadioButton answerBtextView = (RadioButton) listItemView.findViewById(R.id.radio_button_answer_b);
        // Set the text for answer B
        answerBtextView.setText(currentQuestion.getAnswerText2());

        // Find the RadioButton in the list_item.xml layout with the ID radio_button_answer_a.
        RadioButton answerCtextView = (RadioButton) listItemView.findViewById(R.id.radio_button_answer_c);
        // Set the text for answer C
        answerCtextView.setText(currentQuestion.getAnswerText3());

        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageHint = (ImageView) listItemView.findViewById(R.id.image);
        ImageView playButton = (ImageView) listItemView.findViewById(R.id.image2);

        // Check if an image is provided for this word or not
        if (currentQuestion.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageHint.setImageResource(currentQuestion.getImageResourceId());
            // Make sure the view is visible
            imageHint.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.GONE);
        } else {
            if (currentQuestion.getIsText()) {
                // Otherwise hide the ImageView (set visibility to GONE)
                imageHint.setVisibility(View.GONE);
                playButton.setVisibility(View.GONE);
            } else {
                imageHint.setVisibility(View.GONE);
                playButton.setVisibility(View.VISIBLE);
                TextView hintChangeForSound = (TextView) listItemView.findViewById(R.id.submit_button);
                hintChangeForSound.setText(R.string.submit_answer_button_text_sound);
            }
        }

        View textContainer = listItemView.findViewById(R.id.container);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tan_background));

        // Return the whole list item layout
        // the ListView.
        return listItemView;
    }
}