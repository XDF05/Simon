package g56020.simon.designPattern;

public interface Observer {
    /**
     * Called whenever the observed object is modified.
     *
     * @param observable observable object
     */
    /**
     * updates the values from the view after being calculated in the model
     *
     * @param observable Model
     * @param args       arguments
     */
    public void update(Observable observable, Object args);
}
