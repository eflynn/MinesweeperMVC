package org.foobar.minesweeper.events;

import java.util.EventObject;

import org.foobar.minesweeper.model.BoardModel;

public class BoardDataEvent extends EventObject {
	public enum EventType {
        ONE, WHOLE
    }
	private static final long serialVersionUID = -8655298334520547396L;
    private int row;
    private int column;

    private EventType event;

    public BoardDataEvent(BoardModel model) {
        this(model, -1, -1);
    }

    public BoardDataEvent(BoardModel model, int row, int column) {
        super(model);
        event = EventType.ONE;
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public EventType getEventType() {
        return event;
    }

    public int getRow() {
        return row;
    }

    @Override
    public BoardModel getSource() {
        return (BoardModel) source;
    }
}