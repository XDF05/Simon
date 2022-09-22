package g56020.simon.view;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class SoundMaker {
    /**
     * Plays a sound according the colored rectangle's color.
     *
     * @param recColor color of the Colored Rectangle
     * @throws MidiUnavailableException exception thrown if sound can't be played
     */
    public static void playSound(ColoredRectangle recColor) throws MidiUnavailableException {

        int noteNumber;
        switch (recColor.getId()) {
            case "green":
                noteNumber = 69;
                break;
            case "red":
                noteNumber = 71;
                break;
            case "yellow":
                noteNumber = 72;
                break;
            case "blue":
                noteNumber = 74;
                break;
            default:
                noteNumber = 0;
                break;
        }

        var synth = MidiSystem.getSynthesizer();
        synth.open();
        var channel = synth.getChannels()[0];
        channel.noteOn(noteNumber, 80);

        var pause = new PauseTransition(Duration.millis(500));

        pause.setOnFinished(event -> channel.noteOff(noteNumber));
        pause.play();

    }
}