package org.foobar.minesweeper.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author eflynn
 */
public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private JCounterLabel flagCount;
    private JBoard cellPane;

    public Main() {
        initComponents();
    }

    public void makeBoard(int rows, int columns) {
        cellPane = new JBoard();
//		cellPane.setCellSize(56);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {    	
    	// Mac OS X only.
    	// take the menu bar off the jframe
    	System.setProperty("apple.laf.useScreenMenuBar", "true");

        UIManager.put("BoardUI", "org.foobar.minesweeper.plaf.BasicMinesweeperBoardUI");
    	
    	EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main mines = new Main();
                mines.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mines.setVisible(true);
            }
        });
    }

    private void initComponents() {
        setTitle("Minesweeper");
        setSize(new Dimension(240, 300));
        setResizable(false);
        setLayout(new BorderLayout());

        Container contentPane = getContentPane();
        JPanel scorePanel = new JPanel();

        /*ActionListener resetEvent = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        model.reset();
        faceButton.setIcon(JFaceButton.NORMAL);
        }
        };*/

        makeBoard(8, 8);
        contentPane.add(cellPane, BorderLayout.CENTER);

        flagCount = new JCounterLabel(0);

        scorePanel.add(flagCount);
        scorePanel.add(new JCounterLabel());
        contentPane.add(scorePanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(gameMenu);

        JMenuItem menuItem = new JMenuItem("New");
        //menuItem.addActionListener(resetEvent);

        gameMenu.add(menuItem);
        gameMenu.addSeparator();
        menuItem = new JMenuItem("Quit");

        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gameMenu.add(menuItem);

        setJMenuBar(menuBar);
    }
}
