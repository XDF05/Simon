package g56020.simon.model;

public enum State {
    /**
     * The game hasn't started yet.
     * Only possibility is to start the game.
     */
    NOT_STARTED,

    /**
     * The game has started.
     */
    STARTED,

    /**
     * The sequence is showing because the user passed the previous level,
     * or he asked to show the last / the longest sequence
     */
    SHOWING_SEQUENCE,
    /**
     * Once the sequence finished showing.
     */
    SEQUENCE_FINISHED,

    /**
     * The sequence entered by the user was incorrect,
     * or the timer has finished without the user entering the complete sequence.
     */
    GAME_OVER;
}

