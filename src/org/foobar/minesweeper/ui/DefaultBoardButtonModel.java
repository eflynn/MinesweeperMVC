package org.foobar.minesweeper.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * An implementation of BoardButtonModel.
 *
 * @author Evan Flynn
 */
public class DefaultBoardButtonModel {
    /** Event handling mixin. */
    private EventListenerList listenerList = new EventListenerList();
    /** A change event. */
    private ChangeEvent event = new ChangeEvent(this);
    /** Whether the mouse button is pressed. */
    private boolean pressed;
    /** Which cell on the board is selected. */
    private CoordinatePair mousePosition = CoordinatePair.EMPTY;

    /**
     * The default constructor.
     */
    public DefaultBoardButtonModel() {
    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    public void clearSelection() {
        setSelection(CoordinatePair.EMPTY);
    }

    protected void fireStateChanged() {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                ((ChangeListener) listeners[i + 1]).stateChanged(event);
            }
        }
    }

    public boolean isArmed() {
        return mousePosition != null;
    }

    public boolean isPressed() {
        return pressed;
    }

    public CoordinatePair getSelection() {
        return mousePosition;
    }

    public boolean isSelectionEmpty() {
        return mousePosition == null;
    }

    public boolean isValueAdjusting() {
        return mousePosition != null;
    }

    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    public void setPressed(boolean pressed) {
        if (this.pressed != pressed) {
            this.pressed = pressed;

            fireStateChanged();
        }

    }

    public int getRow() {
        return mousePosition.getRow();
    }

    public int getColumn() {
        return mousePosition.getColumn();
    }

    public void setSelection(CoordinatePair cell) {
        CoordinatePair oldCell = mousePosition;

        if (oldCell != cell) {
            mousePosition = cell;

            fireStateChanged();
        }
    }
}
