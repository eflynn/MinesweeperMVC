package org.foobar.minesweeper.model;

import java.util.EventListener;

import org.foobar.minesweeper.events.BoardDataEvent;

public interface BoardModelListener extends EventListener {
    void boardChanged(BoardDataEvent e);
}
