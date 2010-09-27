/**
 * 
 */
package org.foobar.minesweeper.plaf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import org.foobar.minesweeper.model.BoardModel;
import org.foobar.minesweeper.model.BoardModel.State;
import org.foobar.minesweeper.ui.CoordinatePair;
import org.foobar.minesweeper.ui.DefaultBoardButtonModel;
import org.foobar.minesweeper.ui.JBoard;
import org.foobar.minesweeper.ui.Utilities;
import static org.foobar.minesweeper.ui.Utilities.toPair;

/**
 * Basic pluggable look-and-feel for JBoard.
 *
 * @author eflynn
 */
public final class BasicMinesweeperBoardUI extends MinesweeperBoardUI {

    /** The singleton instance. */
    private static BasicMinesweeperBoardUI ui;
    /** An array of ordered colors. */
    private static Color[] numberColors = {Color.BLUE, Color.GREEN.darker(),
        Color.RED, Color.BLUE.darker(), Color.ORANGE, Color.YELLOW,
        Color.CYAN, Color.BLACK};
    /** An icon of a mine. */
    private final ImageIcon mineIcon;
    /** An icon of a flag. */
    private final ImageIcon flagIcon;
    /** Used for drawing a single cell. */
    private final Border RAISED = BorderFactory.createRaisedBevelBorder();
    /** Used for drawing a single cell. */
    private static final Border PRESSED = BorderFactory.createLineBorder(
            Color.LIGHT_GRAY, 1);
    /** The mouse listener. */
    private MouseAdapter mouseListener;

    private BasicMinesweeperBoardUI() {
        mineIcon = Utilities.createImageIcon(BasicMinesweeperBoardUI.class, "../ui/resources/mine.gif");
        flagIcon = Utilities.createImageIcon(BasicMinesweeperBoardUI.class, "../ui/resources/flag.gif");    	
    }

    /** Handles MouseEvents. */
    class Listener extends MouseAdapter {


        @Override
        public void mousePressed(MouseEvent e) {
            final JBoard b = (JBoard) e.getSource();
            final DefaultBoardButtonModel buttonModel = b.getButtonModel();
            final int cellSize = b.getCellSize();
            
            if (SwingUtilities.isLeftMouseButton(e)) {
                buttonModel.setSelection(toPair(cellSize, e.getPoint()));
                buttonModel.setPressed(true);
            } else if (SwingUtilities.isMiddleMouseButton(e)) {
                return;
            } else if (SwingUtilities.isRightMouseButton(e)) {
                CoordinatePair p = toPair(cellSize, e.getPoint());
                b.getModel().toggleFlag(p.getRow(), p.getColumn());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            final JBoard b = (JBoard) e.getSource();
            final DefaultBoardButtonModel buttonModel = b.getButtonModel();

            if (SwingUtilities.isLeftMouseButton(e)) {
                int row = buttonModel.getRow();
                int col = buttonModel.getColumn();

                buttonModel.setPressed(false);
                buttonModel.clearSelection();
                b.getModel().reveal(row, col);

            } else if (SwingUtilities.isMiddleMouseButton(e)) {
                return;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JBoard b = (JBoard) e.getSource();
            DefaultBoardButtonModel buttonModel = b.getButtonModel();

            if (buttonModel.isPressed()) {
                buttonModel.setSelection(toPair(b.getCellSize(), e.getPoint()));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JBoard b = (JBoard) e.getSource();
            DefaultBoardButtonModel buttonModel = b.getButtonModel();

            buttonModel.clearSelection();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            JBoard b = (JBoard) e.getSource();
            DefaultBoardButtonModel buttonModel = b.getButtonModel();

            if (SwingUtilities.isLeftMouseButton(e)) {
                CoordinatePair p = toPair(b.getCellSize(), e.getPoint());
                buttonModel.setSelection(p);

            } else if (SwingUtilities.isMiddleMouseButton(e)) {
                return;
            }
        }
    }

    public static ComponentUI createUI(JComponent c) {
        if (ui == null) {
            ui = new BasicMinesweeperBoardUI();
        }

        return ui;
    }

    /** {@inheritDoc} */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        if (mouseListener == null) {
            mouseListener = new Listener();
        }

        c.addMouseListener(mouseListener);
        c.addMouseMotionListener(mouseListener);
    }

    @Override
    public void uninstallUI(JComponent c) {
        c.removeMouseListener(mouseListener);
        c.removeMouseMotionListener(mouseListener);
    }

    @Override
    public void paint(Graphics _gfx, JComponent c) {
        assert _gfx instanceof Graphics2D;
    	Graphics gfx = (Graphics2D) _gfx;
    	Rectangle r = gfx.getClipBounds();

        JBoard b = (JBoard) c;
        int cellsize = b.getCellSize();

        final int rows = b.getModel().getRowCount();
        final int columns = b.getModel().getColumnCount();
        final int maxrows = Math.min((r.y + r.height) / cellsize + 1, rows);
        final int maxcols = Math.min((r.x + r.width) / cellsize + 1, columns);

        for (int i = r.y / cellsize + 1; i <= maxrows; i++) {
            for (int j = r.x / cellsize + 1; j <= maxcols; j++) {
                gfx.translate((j - 1) * cellsize, (i - 1) * cellsize);
                paintCell(gfx, b, b.getModel(), b.getButtonModel(), i, j,
                        cellsize);
                gfx.translate(-(j - 1) * cellsize, -(i - 1) * cellsize);
            }
        }
    }
    
    /**
     * Paint a cell
     *
     * @param g
     * @param b
     * @param model
     * @param bmodel
     * @param r
     * @param c
     * @param cellsize
     */
    private void paintCell(Graphics g, JBoard b, BoardModel model,
                           DefaultBoardButtonModel bmodel, int r, int c, int cellsize) {
        Border border = model.isExposed(r, c) || bmodel.getSelection().equals(r, c) ? PRESSED : RAISED;

        border.paintBorder(b, g, 1, 1, cellsize - 1, cellsize - 1);

        int third = cellsize / 3 - 3;

        if (model.isFlagged(r, c)) {
            flagIcon.paintIcon(b, g, third, third);
        } else if (model.isExposed(r, c) && model.getMineCount(r, c) > 0) {
            int val = model.getMineCount(r, c);
            Color old = g.getColor();
            g.setColor(numberColors[val - 1]);
            g.drawString("" + val, 8, 16);
            g.setColor(old);
        } else if (model.getState() == State.LOST && model.isMine(r, c)) {
            mineIcon.paintIcon(b, g, third, third);
        }

        /*
         * switch (cell) { case HITMINE: Color oldC1 = g.getColor();
         * g.setColor(Color.RED); g.fillRect(1, 1, cellsize - 1, cellsize - 1);
         * g.setColor(oldC1); case MINE: MINEICON.paintIcon(this, g, 5, 5);
         * break; case QUESTION: g.drawString("?", 8, 16); break; case FLAGGED:
         *
         * break; case WRONG: Graphics2D g2 = (Graphics2D) g;
         *
         * MINEICON.paintIcon(this, g2, 5, 5); Stroke oldS = g2.getStroke();
         * Color oldC = g2.getColor(); g2.setStroke(new BasicStroke(2.0f,
         * BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
         * g2.setColor(Color.RED); g2.draw(new Line2D.Float(5.0f, 5.0f, 20.0f,
         * 20.0f)); g2.draw(new Line2D.Float(20.0f, 5.0f, 5.0f, 20.0f));
         * g2.setStroke(oldS); g2.setColor(oldC); break; case BLANK: int val =
         * boardModel.getNearbyMineCount(r, c); if (val > 0) { Color old =
         * g.getColor(); g.setColor(numberColors[val - 1]); g.drawString("" +
         * val, 8, 16); g.setColor(old); } break; }
         */
    }


    @SuppressWarnings("unused")
    private void repaintCell(int row, int column) {
        /*int cellsize = 24;
        repaint((column - 1) * cellsize, (row - 1) * cellsize, cellsize,
                cellsize);*/
    }
}
