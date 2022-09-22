package g56020.simon.model;

import g56020.simon.designPattern.Observable;
import g56020.simon.designPattern.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Model implements Observable {

    private State state;
    private final List<Observer> observers;

    private List<Color> colors;
    private double speed;

    private int level = 0;
    private boolean correctSequence = false;
    private Timeline timeline;
    private final List<Color> sequence = new ArrayList<>();
    private int playSequenceIndex;
    private Color randomColor;

    private int showSequenceIndex;
    private List<Color> longest = new ArrayList<>();
    private List<Color> last = new ArrayList<>();

    private static final long TIME_LIMIT = 2000;

    public Model() {
        observers = new ArrayList<>();
        this.state = State.NOT_STARTED;
    }

    /**
     * Retrieves the Colored Rectangles, and the speed at which the game has to be.
     *
     * @param speed speed at which the game has to be.
     */
    public void start(double speed) {
        this.state = State.STARTED;

        this.colors = new ArrayList<>();
        this.colors.add(Color.GREEN);
        this.colors.add(Color.RED);
        this.colors.add(Color.YELLOW);
        this.colors.add(Color.BLUE);
        this.speed = speed;
        playSequence();
    }

    /**
     * Verifies if the colored Rectangle which the user has clicked on, follows the sequence.
     */
    public void click(Color color) {
        if (this.state == State.SEQUENCE_FINISHED) {
            if (sequence.get(playSequenceIndex++).equals(color)) {
                notifyObservers(color);
                if (sequence.size() == playSequenceIndex) {
                    if (longest.size() <= last.size()) {
                        longest = new ArrayList<>(sequence);
                    }
                    this.correctSequence = true;
                    playSequence();
                }
            } else {
                level = 0;
                this.state = State.GAME_OVER;
                notifyObservers("GAME_OVER");
            }
        }
    }

    /**
     * Play a sequence of colors.
     * Adjust speed according to slider value.
     * level increases everytime the previous level has been succeeded.
     * retrieves lastLevel & maxLevel.
     */
    public void playSequence() {
        this.state = State.SHOWING_SEQUENCE;
        level++;
        playSequenceIndex = 0;
        sequence.clear();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1 / speed), event -> {
            randomColor = randomColor();
            notifyObservers(randomColor);
            sequence.add(randomColor);
        }));
        timeline.setCycleCount(level);
        timeline.setOnFinished(e -> {
            this.state = State.SEQUENCE_FINISHED;

            last = new ArrayList<>(sequence);
            runTimer();
        });
        timeline.play();
    }

    /**
     * Shows either the longest or last played sequence.
     *
     * @param sequence longest or last played sequence.
     */
    private void showSequence(List<Color> sequence) {
        if (this.state != State.SHOWING_SEQUENCE) {
            this.state = State.SHOWING_SEQUENCE;
            showSequenceIndex = 0;
            Timeline tl2 = new Timeline(new KeyFrame(Duration.seconds(1 / speed), event -> notifyObservers(sequence.get(showSequenceIndex++))));
            tl2.setCycleCount(sequence.size());
            tl2.setOnFinished(e -> this.state = State.SEQUENCE_FINISHED);
            tl2.play();
        }
    }

    /**
     * Starts a timer after the sequence has finished playing.
     * If the time is over, resets level to 0
     * and shows an alert with "TIME_IS_OVER" message.
     */
    private void runTimer() {
        var timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!correctSequence) {
                    Platform.runLater(() -> {
                        if (state != State.GAME_OVER) {
                            notifyObservers("TIME_IS_OVER");
                        }
                        level = 0;
                        state = State.GAME_OVER;
                    });
                }
            }
        }, (TIME_LIMIT + (level * 1000L)));
        correctSequence = false;
    }

    /**
     * Retrieves a random colored Rectangle
     *
     * @return randomly selected colored Rectangle
     */
    private Color randomColor() {
        int randInt = (int) (Math.random() * 4);
        return colors.get(randInt);
    }

    /**
     * Shows the longest sequence that the user entered correctly.
     */
    public void longest() {
        showSequence(longest);
    }

    /**
     * Shows the latest played sequence.
     */
    public void last() {
        showSequence(last);
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observer != null) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(Object args) {
        observers.forEach(observer -> observer.update(this, args));
    }
}
