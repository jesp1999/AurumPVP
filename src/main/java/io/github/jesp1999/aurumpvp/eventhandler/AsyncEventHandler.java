package io.github.jesp1999.aurumpvp.eventhandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Handles all asynchronous events
 * @author Ian McDowell
 */
public class AsyncEventHandler {
    private List<AsyncEvent> events;

    /**
     * Creates new AsyncEventHandler
     */
    public AsyncEventHandler() {
        this.events = new LinkedList<AsyncEvent>();
    }

    /**
     * Adds new asynchronous event to the list of events
     * @param e Asynchronous Event to be added
     * @return If the event was successfully added
     */
    public boolean addEvent(AsyncEvent e) {
        return this.events.add(e);
    }

    /**
     * Removes existing asynchronous event from the list of events
     * @param e Asynchronous Event to be removed
     * @return If the event was successfully removed
     */
    public boolean removeEvent(AsyncEvent e) {
        return this.events.remove(e);
    }
}
