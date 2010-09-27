package org.foobar.minesweeper.ui;

import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.foobar.minesweeper.events.BoardDataEvent;
import org.foobar.minesweeper.model.BoardModel;
import org.foobar.minesweeper.model.BoardModelListener;
import org.foobar.minesweeper.plaf.BasicMinesweeperBoardUI;
import org.foobar.minesweeper.plaf.MinesweeperBoardUI;

/**
 * JBoard is a Swing component for Minesweeper.  Typical usage:
 *
 * <pre>
 * BoardModel model = new BoardModel(8, 8, 10);
 * JBoard board = new JBoard(model);
 * </pre>
 *
 * @author eflynn
 */
public class JBoard extends JComponent {
	private static final long serialVersionUID = 1L;
	/**
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "BoardUI";
    /** The default number of rows. */
    private static final int DEFAULT_ROW_COUNT = 8;
    /** The default number of columns. */
    private static final int DEFAULT_COLUMN_COUNT = 8;
    /** The current size of the cells in pixels. */
    private int cellsize = 24;
    /** The data model for the field. */
    private BoardModel dataModel;
    /** The button model for the field. */
    private DefaultBoardButtonModel buttonModel;
    /** ChangeListener for BoardModel. */
    private ChangeListener changeListener;
    /** BoardModelListener for BoardModel. */
    private BoardModelListener dataListener;
    /** Contains callbacks for BoardModel. */
    private Handler handler;

    /** Contains callbacks for BoardModel. */
    class Handler implements ChangeListener, BoardModelListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            repaint();
        }

		@Override
		public void boardChanged(BoardDataEvent e) {
            repaint();
		}
    }

    /**
     * Constructs a default JBoard.
     */
    public JBoard() {
        this(new BoardModel(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT, 10));
    }

    /**
     * Constructs a JBoard with a BoardModel.
     * @param model BoardModel object
     */
    public JBoard(BoardModel model) {
        setFont(new Font("Dialog", Font.BOLD, 12));

        setModel(model);
        setButtonModel(createButtonModel());

        updateUI();
    }

    /**
     * Creates a ButtonModel.
     * @return new ButtonModel
     */
    protected DefaultBoardButtonModel createButtonModel() {
        return new DefaultBoardButtonModel();
    }

    /**
     * Creates a ChangeListener.
     *
     * @return new ChangeListener
     */
    protected ChangeListener createChangeListener() {
        return getHandler();
    }

    /**
     * Returns the button model.
     *
     * @return the button model
     */
    public DefaultBoardButtonModel getButtonModel() {
        return buttonModel;
    }

    /**
     * Returns the current cell size in pixels.
     *
     * @return the cell size
     */
    public int getCellSize() {
        return cellsize;
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    /**
     * Returns the data model.
     *
     * @return the data model
     */
    public BoardModel getModel() {
        return dataModel;
    }

    /**
     * Returns the look-and-feel delegate that renders this component.
     *
     * @return the BoardUI object that renders this component
     */
    public BasicMinesweeperBoardUI getUI() {
        return (BasicMinesweeperBoardUI) ui;
    }

    /**
     * Returns the suffix used to construct the name of the L&F class used to
     * render this component.
     *
     * @return the string "BoardUI"
     */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Registers a new ButtonModel.
     *
     * @param newModel the ButtonModel
     */
    public void setButtonModel(DefaultBoardButtonModel newModel) {
        DefaultBoardButtonModel oldModel = getButtonModel();

        if (oldModel != null) {
            oldModel.removeChangeListener(changeListener);
            changeListener = null;
        }

        buttonModel = newModel;

        if (newModel != null) {
            changeListener = createChangeListener();
            newModel.addChangeListener(changeListener);
        }

        firePropertyChange("buttonModel", oldModel, newModel);

        if (newModel != oldModel) {
            revalidate();
            repaint();
        }
    }

    /**
     * Change the size of cells.  This is a bound property
     *
     * @param size in pixels
     */
    public void setCellSize(int size) {
        if (cellsize != size) {
            int old = cellsize;
            cellsize = size;

            firePropertyChange("cellSize", old, size);
        }
    }

    /**
     * Sets the data model for this board to <code>newModel</code> and registers
     * with it for listener notifications from the new data model.
     *
     * @param newModel the new data source for this board
     */
    public void setModel(BoardModel newModel) {
        BoardModel oldModel = getModel();

        if (newModel == null) {
            throw new IllegalArgumentException("newModel must not be null");
        }

        if (oldModel != null) {
            oldModel.removeBoardModelListener(dataListener);
            dataListener = null;
        }

        dataModel = newModel;

        dataListener = createBoardModelListener();
        newModel.addBoardModelListener(dataListener);

        firePropertyChange("model", oldModel, newModel);

        if (newModel != oldModel) {
            revalidate();
            repaint();
        }
    }

    public void toggleFlag(int row, int col) {
    	dataModel.toggleFlag(row, col);
    }
    
    /**
     * Creates a BoardModelListener object.  This method can be overriden by
     * subclasses.
     * 
     * @return new BoardModelListener
     */
    protected BoardModelListener createBoardModelListener() {
        return getHandler();
    }

    /**
     * Sets the look-and-feel object that renders this component and repaints.
     *
     * @param newUI the MinesweeperBoardUI L&F object
     */
    public void setUI(MinesweeperBoardUI newUI) {
        super.setUI(newUI);
    }

    /**
     * Notification from the UIManager that the L&F has changed. Replaces the
     * current UI object with the latest version from the UIManager.
     *
     * @see JComponent#updateUI()
     */
    @Override
    public void updateUI() {
        setUI((BasicMinesweeperBoardUI) UIManager.getUI(this));
    }
}

