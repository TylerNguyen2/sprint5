import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.File;
import java.io.IOException;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class SOSGUI extends JFrame {

    public static final int CELL_SIZE = 50;
    public static final int GRID_WIDTH = 4;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    private SOSGameBoard SOSGameBoard;
    private JLabel gameStatusBar;

    private SOSGame game;

    private boolean flag;

    JFrame jf;
    JRadioButton blueBottonS;
    JRadioButton blueBottonO;
    JRadioButton redButtonS;
    JRadioButton redButtonO;

    public SOSGUI() {
        this(new SOSGame(5));
        jf = this;
    }

    public SOSGUI(SOSGame game) {
    	//Set game class
        this.game = game;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("SOS");
        setVisible(true);
        jf = this;
        flag = false;
    }

    private void setContentPane() {
        SOSGameBoard = new SOSGameBoard();
        //Set game board size
        SOSGameBoard
                .setPreferredSize(new Dimension(CELL_SIZE * game.getTotalRows(), CELL_SIZE * game.getTotalColumns()));
        gameStatusBar = new JLabel("  ");
        gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        gameStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel p = new JPanel();
        p.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        p.setLayout(new BorderLayout());
        p.add(SOSGameBoard, BorderLayout.CENTER);
        p.add(gameStatusBar, BorderLayout.SOUTH);
        contentPane.add(p, BorderLayout.CENTER);
        //Main JPanel
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(255, 255, 255));
        JLabel sosLabel = new JLabel("SOS Game");
        sosLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        JLabel boardLabel = new JLabel("Apply board size");
        JTextArea text = new JTextArea("");
        text.setBackground(new Color(192, 192, 192));
        text.setFont(new Font("Tahoma", Font.PLAIN, 13));
        text.setPreferredSize(new Dimension(20, 20));
        JButton confirm = new JButton("Change Size");
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        SOSGUI gui = new SOSGUI(new SOSGame(Integer.valueOf(text.getText())));
                        new TestThread(gui).start();
                    }
                });

            }
        });
        // on panel one add buttons
        ButtonGroup sosGroup = new ButtonGroup();
        p1.add(sosLabel);
        p1.add(boardLabel);
        p1.add(text);
        p1.add(confirm);
        contentPane.add(p1, BorderLayout.NORTH);
        //Second panel to contain player infromation
        JPanel p2 = new JPanel();
        p2.setBackground(SystemColor.activeCaption);
        JRadioButton blueHuman = new JRadioButton("Human", true);
        blueHuman.setFont(new Font("Tahoma", Font.PLAIN, 20));
        blueHuman.setBackground(new Color(255, 255, 255));
        blueHuman.setBounds(0, 132, 136, 21);
        JRadioButton blueComputer = new JRadioButton("Computer");
        blueComputer.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                game.isBuleComputer = blueComputer.isSelected();
            }
        });
        blueComputer.setFont(new Font("Tahoma", Font.PLAIN, 20));
        blueComputer.setBackground(new Color(255, 255, 255));
        blueComputer.setBounds(0, 234, 136, 21);
        blueComputer.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                
            }
        });
        //Blue buttons
        blueBottonS = new JRadioButton("S", true);
        blueBottonS.setFont(new Font("Tahoma", Font.PLAIN, 20));
        blueBottonS.setBackground(new Color(255, 255, 255));
        blueBottonS.setBounds(88, 156, 48, 31);
        blueBottonO = new JRadioButton("O");
        blueBottonO.setFont(new Font("Tahoma", Font.PLAIN, 20));
        blueBottonO.setBackground(new Color(255, 255, 255));
        blueBottonO.setBounds(88, 190, 48, 31);
        ButtonGroup bluePlayerGroup = new ButtonGroup();
        bluePlayerGroup.add(blueBottonS);
        bluePlayerGroup.add(blueBottonO);
        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueHuman);
        blueGroup.add(blueComputer);
        p2.setLayout(null);
        p2.add(blueHuman);
        p2.add(blueBottonS);
        p2.add(blueBottonO);
        p2.add(blueComputer);

        contentPane.add(p2, BorderLayout.WEST);
        p2.setPreferredSize(new Dimension(200, 400));
        
        JLabel blueLabel = new JLabel("Blue Player");
        blueLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        blueLabel.setBounds(10, 84, 103, 31);
        p2.add(blueLabel);
        JRadioButton sosButton1 = new JRadioButton("Simple game", true);
        sosButton1.setBounds(22, 7, 85, 23);
        p2.add(sosButton1);
        sosButton1.setBackground(new Color(255, 255, 255));
        sosGroup.add(sosButton1);
        JRadioButton sosButton2 = new JRadioButton("General game");
        sosButton2.setBounds(22, 33, 91, 23);
        p2.add(sosButton2);
        sosButton2.setBackground(new Color(255, 255, 255));
        sosGroup.add(sosButton2);
        sosButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.setCurrentGameType(SOSGame.GameType.General);
            }
        });
        sosButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.setCurrentGameType(SOSGame.GameType.Simple);
            }
        });
        
        //to be implemented
        /*recordButton.addActionListener(new ActionListener() {
        });*/

        JPanel p3 = new JPanel();
        p3.setBackground(new Color(128, 0, 0));
        JRadioButton redHuman = new JRadioButton("Human", true);
        redHuman.setFont(new Font("Tahoma", Font.PLAIN, 20));
        redHuman.setBackground(new Color(255, 255, 255));
        redHuman.setBounds(52, 116, 142, 21);
        JRadioButton redComputer = new JRadioButton("Computer");
        redComputer.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                game.isRedComputer = redComputer.isSelected();
            }
        });
        redComputer.setFont(new Font("Tahoma", Font.PLAIN, 20));
        redComputer.setBackground(new Color(255, 255, 255));
        redComputer.setBounds(52, 237, 142, 21);
        redComputer.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                
            }
        });
        
        //Red buttons
        redButtonS = new JRadioButton("S", true);
        redButtonS.setFont(new Font("Tahoma", Font.PLAIN, 20));
        redButtonS.setBackground(new Color(255, 255, 255));
        redButtonS.setBounds(102, 151, 50, 21);
        redButtonO = new JRadioButton("O");
        redButtonO.setFont(new Font("Tahoma", Font.PLAIN, 20));
        redButtonO.setBackground(new Color(255, 255, 255));
        redButtonO.setBounds(102, 184, 50, 21);
        ButtonGroup redPlayerGroup = new ButtonGroup();
        redPlayerGroup.add(redButtonS);
        redPlayerGroup.add(redButtonO);
        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redHuman);
        redGroup.add(redComputer);
        p3.setLayout(null);
        p3.add(redHuman);
        p3.add(redButtonS);
        p3.add(redButtonO);
        p3.add(redComputer);
        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        newGameButton.setBounds(20, 368, 160, 21);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        SOSGUI gui = new SOSGUI(new SOSGame(game.TOTALROWS));
                        new TestThread(gui).start();
                    }
                });
            }

        });
        p3.add(newGameButton);

        contentPane.add(p3, BorderLayout.EAST);
        p3.setPreferredSize(new Dimension(200, 400));
        
        JLabel redPlayer = new JLabel("Red Player");
        redPlayer.setFont(new Font("Tahoma", Font.PLAIN, 20));
        redPlayer.setBounds(52, 78, 103, 31);
        p3.add(redPlayer);
        JButton recordButton = new JButton("   Record   ");
        recordButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                try {
                    Process process = Runtime.getRuntime().exec("cmd.exe /c notepad.exe record.txt");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        recordButton.setBounds(10, 336, 166, 21);
        p3.add(recordButton);
        recordButton.setFont(new Font("Tahoma", Font.PLAIN, 20));

    }

    class SOSGameBoard extends JPanel {
    	//Get size of gameBoard
        SOSGameBoard() {
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (game.getGameState() == SOSGame.GameState.PLAYING) {
                        int rowSelected = e.getY() / CELL_SIZE;
                        int colSelected = e.getX() / CELL_SIZE;
                        char turn = game.getTurn();
                        int type;
                        if (turn == 'B')
                            type = blueBottonS.isSelected() ? 0 : 1;
                        else
                            type = redButtonS.isSelected() ? 0 : 1;
                        game.makeMove(rowSelected, colSelected, type);
                    } else {
                        game.resetGame();
                    }
                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
            drawLines(g);
            printStatusBar();
        }
        //draw grid lines of the board
        private void drawGridLines(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < game.getTotalRows(); ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF, CELL_SIZE * game.getTotalRows() - 1, GRID_WIDTH,
                        GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < game.getTotalColumns(); ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH,
                        CELL_SIZE * game.getTotalColumns() - 1, GRID_WIDTH, GRID_WIDTH);
            }
        }
        //Create gameboard
        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < game.getTotalRows(); ++row) {
                for (int col = 0; col < game.getTotalColumns(); ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (game.getCell(row, col) == SOSGame.Cell.S) {
//						int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
//						int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawArc(x1 + CELL_SIZE / 5, y1, CELL_SIZE / 2 - CELL_PADDING, CELL_SIZE / 2 - CELL_PADDING,
                                60, 210);
                        g2d.drawArc(x1 + CELL_SIZE / 5, y1 + CELL_SIZE / 2 - CELL_PADDING, CELL_SIZE / 2 - CELL_PADDING,
                                CELL_SIZE / 2 - CELL_PADDING, 240, 210);
                    } else if (game.getCell(row, col) == SOSGame.Cell.O) {
                        g2d.drawOval(x1 + CELL_SIZE / 10, y1, (int) (SYMBOL_SIZE * 0.8), SYMBOL_SIZE);
                    }
                }
            }
        }
        //Draw lines when user complete SOS
        private void drawLines(Graphics g) {
            ArrayList<ArrayList<Integer>> info = game.getSosInfo();
            Graphics2D g2d = (Graphics2D) g;
            if (info == null)
                return;
            for (ArrayList<Integer> it : info) {
                if (it.size() > 1) {
                    if (it.get(0) == 0)
                        g2d.setColor(Color.BLUE);
                    else
                        g2d.setColor(Color.RED);
                    for (int i = 1; i < it.size(); i += 4) {
                        int x1 = it.get(i + 1) * CELL_SIZE + CELL_SIZE / 2;
                        int y1 = it.get(i) * CELL_SIZE + CELL_SIZE / 2;
                        int x2 = it.get(i + 3) * CELL_SIZE + CELL_SIZE / 2;
                        int y2 = it.get(i + 2) * CELL_SIZE + CELL_SIZE / 2;
                        g2d.drawLine(x1, y1, x2, y2);
                    }
                }

            }
        }
        //Select who turn it is
        private void printStatusBar() {
            if (game.getGameState() == SOSGame.GameState.PLAYING) {
                gameStatusBar.setForeground(Color.BLACK);
                if (game.getTurn() == 'B') {
                    gameStatusBar.setText("Blue's Turn");
                    gameStatusBar.setForeground(Color.BLUE);
                } else {
                    gameStatusBar.setText("Red's Turn");
                    gameStatusBar.setForeground(Color.RED);
                }
            } else if (game.getGameState() == SOSGame.GameState.DRAW) {
                gameStatusBar.setForeground(Color.BLACK);
                gameStatusBar.setText("It's a Draw! Click to play again.");
                if (!flag) {
                    game.writeLine("It's a Draw!\n\n");
                    flag = true;
                }
            } else if (game.getGameState() == SOSGame.GameState.BLUE_WON) {
                gameStatusBar.setForeground(Color.BLUE);
                gameStatusBar.setText("'Blue' Won! Click to play again.");
                if (!flag) {
                    game.writeLine("'Blue' Won!\n\n");
                    flag = true;
                }
            } else if (game.getGameState() == SOSGame.GameState.RED_WON) {
                gameStatusBar.setForeground(Color.RED);
                gameStatusBar.setText("'Red' Won! Click to play again.");
                if (!flag) {
                    game.writeLine("'Red' Won!\n\n");
                    flag = true;
                }
            }
        }

    }


    public static class TestThread extends Thread {
        private SOSGUI gui;

        public TestThread(SOSGUI gui) {
            this.gui = gui;
        }

        public void run() {
            while (true) {
                try {
                    if (gui.game.currentGameState != SOSGame.GameState.PLAYING || gui.game.getNumberOfEmptyCells() == 0)
                        break;
                    sleep(10);
                    if (gui.game.isBuleComputer && gui.game.getTurn() == 'B') {
                        gui.game.makeRandomMove();
                        gui.repaint();
                    }
                    if (gui.game.isRedComputer && gui.game.getTurn() == 'R') {
                        gui.game.makeRandomMove();
                        gui.repaint();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SOSGUI gui = new SOSGUI();
                new TestThread(gui).start();
            }
        });
    }
}
