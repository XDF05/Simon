package g56020.simon.designPattern;

public interface Observable {
    /**
     * adds an observer to the observers list
     *
     * @param observer observer to be added (subscribed)
     */
    void addObserver(Observer observer);

    /**
     * removes an observer from the observers list
     *
     * @param observer observer to be removed (unsubscribed)
     */
    void removeObserver(Observer observer);

    /**
     * notifies every observer
     */
    void notifyObservers(Object args);
}
