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

    /** 1. answer*/
    private String qAnswerText1;

    /** 2. answer*/
    private String qAnswerText2;

    /** 3. answer*/
    private String qAnswerText3;

    private int qRightAnswerPosition;

    /** Audio resource ID for the word */
    private int qAudioResourceId = NO_AUDIO_PROVIDED;

    /** Constant value that represents no audio question */
    private static final int NO_AUDIO_PROVIDED = -1;

    /** Image resource ID for the question */
    private int qImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image question */
    private static final int NO_IMAGE_PROVIDED = -1;

    /** Image resource ID for the question */
    private boolean qIsImageId;

    /** Image resource ID for the question */
    private boolean qIsText = false;

    /**
     * Create a new Question object - text type.
     *
     * @param questionText is recent question
     * @param answerText1 is 1. answer for qQuestionText
     * @param answerText2 is 2. answer for qQuestionText
     * @param answerText3 is 3. answer for qQuestionText
     * @param rightAnswerPosition says witch i true one
     */
    public Question(String questionText, String answerText1, String answerText2, String answerText3, int rightAnswerPosition ) {
        qQuestionText = questionText;
        qAnswerText1 = answerText1;
        qAnswerText2 = answerText2;
        qAnswerText3 = answerText3;
        qRightAnswerPosition = rightAnswerPosition;
        qIsText = true;
    }

    /**
     * Create a new Question object - image or sound type.
     *
     * @param questionText is recent question
     * @param answerText1 is 1. answer for qQuestionText
     * @param answerText2 is 2. answer for qQuestionText
     * @param answerText3 is 3. answer for qQuestionText
     * @param imageOrAudioResourceId id for image or sound
     * @param isImageId for image = true and for audio = false
     * @param rightAnswerPosition says witch i true one
     */
    public Question(String questionText, String answerText1, String answerText2, String answerText3, int imageOrAudioResourceId, boolean isImageId, int rightAnswerPosition ) {
        qQuestionText = questionText;
        qAnswerText1 = answerText1;
        qAnswerText2 = answerText2;
        qAnswerText3 = answerText3;
        if (isImageId) qImageResourceId = imageOrAudioResourceId;
        else qAudioResourceId = imageOrAudioResourceId;
        qIsImageId = isImageId;
        qRightAnswerPosition = rightAnswerPosition;
    }

    /**
     * Get the question text of the question instance.
     */
    public String getQuestionText() {
        return qQuestionText;
    }
    /**
     * Get the 1. answer of the question instance.
     */
    public String getAnswerText1() {
        return qAnswerText1;
    }

    /**
     * Get the 2. answer of the question instance.
     */
    public String getAnswerText2() {
        return qAnswerText2;
    }
    /**
     * Get the 3. answer of the question instance.
     */
    public String getAnswerText3() {
        return qAnswerText3;
    }
    /**
     * Get the position of wright answer of the question instance.
     */
    public int getqRightAnswerPosition() {
        return qRightAnswerPosition;
    }

    /**
     * Return the image resource ID of the word.
     */
    public int getImageResourceId() {
        return qImageResourceId;
    }


    /** Image resource ID for the question */
    public boolean getIsText() {return qIsText;}

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
    /**
     * Returns information about type of question - image (true) or sound (false).
     */
    public boolean getIsImageId() { return qIsImageId; }
}