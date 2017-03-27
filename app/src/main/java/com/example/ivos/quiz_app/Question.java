package com.example.ivos.quiz_app;

/**
 * {@link Question} represents a vocabulary word that the user wants to learn.
 * It contains a default translation, a Miwok translation, and an image for that word.
 */
public class Question {

    /** Carry chosen language -  true = Czech and false = English*/
    private boolean setCzechLanguage = true;

    /** Question text */
    private String qQuestionText;

    /** Right answer*/
    private String qAnswerRightText;

    /** First wrong answer*/
    private String qAnswerWrong1Text;

    /** Second wrong answer*/
    private String qAnswerWrong2Text;

    /** Audio resource ID for the word */
    private int qAudioResourceId = NO_AUDIO_PROVIDED;

    /** Constant value that represents no audio question */
    private static final int NO_AUDIO_PROVIDED = -1;

    /** Image resource ID for the question */
    private int qImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image question */
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new Question object - text type.
     *
     * @param questionText is recent question
     * @param answerRightText is right answer for qQuestionText
     * @param answerWrong1Text is wrong answer for qQuestionText
     * @param answerWrong2Text is wrong answer for qQuestionText
     */
    public Question(String questionText, String answerRightText, String answerWrong1Text, String answerWrong2Text ) {
        qQuestionText = questionText;
        qAnswerRightText = answerRightText;
        qAnswerWrong1Text = answerWrong1Text;
        qAnswerWrong2Text = answerWrong2Text;
    }

    /**
     * Create a new Question object - image or sound type.
     *
     * @param questionText is recent question
     * @param answerRightText is right answer for qQuestionText
     * @param answerWrong1Text is wrong answer for qQuestionText
     * @param answerWrong2Text is wrong answer for qQuestionText
     * @param imageOrAudioResourceId id for image or sound
     * @param isImageId for image = true and for audio = false
     */
    public Question(String questionText, String answerRightText, String answerWrong1Text, String answerWrong2Text, int imageOrAudioResourceId, boolean isImageId ) {
        qQuestionText = questionText;
        qAnswerRightText = answerRightText;
        qAnswerWrong1Text = answerWrong1Text;
        qAnswerWrong2Text = answerWrong2Text;
        if (isImageId) qImageResourceId = imageOrAudioResourceId;
        else qAudioResourceId = imageOrAudioResourceId;
    }

    /**
     * Get the question text of the question instance.
     */
    public String getQuestionText() {
        return qQuestionText;
    }
    /**
     * Get the right answer of the question instance.
     */
    public String getAnswerRightText() {
        return qAnswerRightText;
    }

    /**
     * Get the first wrong answer of the question instance.
     */
    public String getAnswerWrong1Text() {
        return qAnswerWrong1Text;
    }
    /**
     * Get the second wrong answer of the question instance.
     */
    public String getAnswerWrong2Text() {
        return qAnswerWrong2Text;
    }

    /**
     * Return the image resource ID of the word.
     */
    public int getImageResourceId() {
        return qImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this question.
     */
    public boolean hasImage() {
        return qImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * Return the audio resource ID of the word.
     */
    public int getAudioResourceId() {
        return qAudioResourceId;
    }

    /**
     * Returns whether or not there is an audio for this question.
     */
    public boolean hasAudio() {
        return qAudioResourceId != NO_IMAGE_PROVIDED;
    }
}