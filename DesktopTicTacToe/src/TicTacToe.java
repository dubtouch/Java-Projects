import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
    private final JButton[] buttons = new JButton[9];
    private final JButton startResetButton = new JButton();
    private final JButton player1Button = new JButton();
    private final JButton player2Button = new JButton();
    private final JLabel statusLabel = new JLabel();
    private boolean xTurn = true;
    private boolean isXHuman = true;
    private boolean isOHuman = true;
    private int turns = 0;
    private final Random random = new Random();

    public TicTacToe() {
        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.lightGray);
        this.setSize(500, 500);

        startResetButton.setText("Start");
        startResetButton.setFont(new Font("Arial", Font.BOLD, 20));
        startResetButton.setFocusable(false);
        startResetButton.addActionListener(this);
        startResetButton.setName("ButtonStartReset");

        statusLabel.setText("Game is not started");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setPreferredSize(new Dimension(100, 50));
        statusLabel.setName("LabelStatus");
        statusLabel.setBackground(Color.lightGray);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3 ));
        buttonPanel.setBackground(Color.lightGray);
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].addActionListener(this);
            buttons[i].setText(" ");
            buttons[i].setEnabled(false);
            buttons[i].setFocusable(false);
        }
        buttons[0].setName("ButtonA3");
        buttons[1].setName("ButtonB3");
        buttons[2].setName("ButtonC3");
        buttons[3].setName("ButtonA2");
        buttons[4].setName("ButtonB2");
        buttons[5].setName("ButtonC2");
        buttons[6].setName("ButtonA1");
        buttons[7].setName("ButtonB1");
        buttons[8].setName("ButtonC1");

        player1Button.setText("Human");
        player2Button.setText("Human");
        player1Button.setFocusable(false);
        player2Button.setFocusable(false);
        player1Button.addActionListener(this);
        player2Button.addActionListener(this);
        player1Button.setName("ButtonPlayer1");
        player2Button.setName("ButtonPlayer2");

        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new GridLayout(1, 3));
        toolbarPanel.setPreferredSize(new Dimension(100, 50));
        toolbarPanel.add(player1Button);
        toolbarPanel.add(startResetButton);
        toolbarPanel.add(player2Button);
        toolbarPanel.setBackground(Color.lightGray);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setName("MenuGame");

        JMenuItem humanVsHuman = new JMenuItem("Human vs Human");
        humanVsHuman.setName("MenuHumanHuman");
        JMenuItem humanVsRobot = new JMenuItem("Human vs Robot");
        humanVsRobot.setName("MenuHumanRobot");
        JMenuItem robotVsHuman = new JMenuItem("Robot vs Human");
        robotVsHuman.setName("MenuRobotHuman");
        JMenuItem robotVsRobot = new JMenuItem("Robot vs Robot");
        robotVsRobot.setName("MenuRobotRobot");
        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("MenuExit");

        gameMenu.add(humanVsHuman);
        gameMenu.add(humanVsRobot);
        gameMenu.add(robotVsHuman);
        gameMenu.add(robotVsRobot);
        gameMenu.addSeparator();
        gameMenu.add(exit);
        humanVsHuman.addActionListener(e -> {
            resetFromMenu(true, true);
            reset();
        });
        humanVsRobot.addActionListener(e -> {
            resetFromMenu(true, false);
            reset();
        });
        robotVsHuman.addActionListener(e -> {
            resetFromMenu(false, true);
            reset();
        });
        robotVsRobot.addActionListener(e -> {
            resetFromMenu(false, false);
            reset();
        });
        exit.addActionListener(e -> System.exit(0));

        menuBar.add(gameMenu);
        this.add(toolbarPanel, BorderLayout.NORTH);
        this.add(buttonPanel);
        this.add(statusLabel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton temp =(JButton) e.getSource();
        if (startResetButton.equals(temp)) {
            reset();
        } else if (player1Button.equals(temp)) {
            changePlayer(player1Button);
            isXHuman = !isXHuman;
        } else if (player2Button.equals(temp)) {
            changePlayer(player2Button);
            isOHuman = !isOHuman;
        } else {
            pressButton(temp);
        }
    }

    private String updateLabel() {
        if (xTurn && isXHuman) return  "Human Player (X)";
        if (xTurn) return  "Robot Player (X)";
        if (isOHuman) return  "Human Player (O)";
        return "Robot Player (O)";
    }

    private void resetFromMenu(boolean player1, boolean player2) {
        startResetButton.setText("Start");
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
            buttons[i].setText(" ");
        }
        player1Button.setEnabled(true);
        player2Button.setEnabled(true);
        if (player1) {
            isXHuman = true;
            player1Button.setText("Human");
        } else {
            isXHuman = false;
            player1Button.setText("Robot");
        }
        if (player2) {
            isOHuman = true;
            player2Button.setText("Human");
        } else {
            isOHuman = false;
            player2Button.setText("Robot");
        }
    }

    private void reset() {
        if (startResetButton.getText().equals("Start")) {
            startResetButton.setText("Reset");
            for(int i = 0; i < 9; i++) {
                buttons[i].setEnabled(true);
                buttons[i].setText(" ");
            }
            turns = 0;
            xTurn = true;
            statusLabel.setText("The turn of " + updateLabel());
            player1Button.setEnabled(false);
            player2Button.setEnabled(false);
            play();
        } else {
            startResetButton.setText("Start");
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
                buttons[i].setText(" ");
            }
            isXHuman = true;
            isOHuman = true;
            player1Button.setText("Human");
            player2Button.setText("Human");
            player1Button.setEnabled(true);
            player2Button.setEnabled(true);
            statusLabel.setText("Game is not started");
        }
    }

    private void play() {
        if (xTurn && !isXHuman) {
            buttons[pcMove()].doClick();
        } else if(!xTurn && !isOHuman) {
            buttons[pcMove()].doClick();
        }
    }

    public int pcMove() {
        if (checkPossibleWin(0, 1, 2) != -1) return checkPossibleWin(0, 1, 2);
        if (checkPossibleWin(3, 4, 5) != -1) return checkPossibleWin(3, 4, 5);
        if (checkPossibleWin(6, 7, 8) != -1) return checkPossibleWin(6, 7, 8);
        if (checkPossibleWin(0, 4, 8) != -1) return checkPossibleWin(0, 4, 8);
        if (checkPossibleWin(2, 4, 6) != -1) return checkPossibleWin(2, 4, 6);
        if (checkPossibleWin(0, 3, 6) != -1) return checkPossibleWin(0, 3, 6);
        if (checkPossibleWin(1, 4, 7) != -1) return checkPossibleWin(1, 4, 7);
        if (checkPossibleWin(2, 5, 8) != -1) return checkPossibleWin(2, 5, 8);
        int temp;
        while (true) {
            temp = random.nextInt(9);
            if (buttons[temp].getText().equals(" ")) {
                return temp;
            }
        }
    }

    private void changePlayer(JButton button) {
        if (button.getText().equals("Human")) {
            button.setText("Robot");
        } else {
            button.setText("Human");
        }
    }

    private void pressButton(JButton temp) {
        if (xTurn && temp.getText().equals(" ")) {
            temp.setText("X");
            temp.setForeground(Color.blue);
            temp.setFont(new Font("Arial Bold", Font.BOLD, 80));
            turns++;
            if (isGameOver()) return;
            xTurn = false;
            statusLabel.setText("The turn of " + updateLabel());
            if (!isOHuman) play();
        } else if (!xTurn && temp.getText().equals(" ")) {
            temp.setText("O");
            temp.setForeground(Color.red);
            temp.setFont(new Font("Arial Bold", Font.BOLD, 80));
            turns++;
            if (isGameOver()) return;
            xTurn = true;
            statusLabel.setText("The turn of " + updateLabel());
            if (!isXHuman) play();
        }
    }

    private boolean isGameOver() {
        if (check(0, 1, 2) ||
                check(3, 4, 5) ||
                check(6, 7, 8) ||
                check(0, 4, 8) ||
                check(2, 4, 6) ||
                check(0, 3, 6) ||
                check(1, 4, 7) ||
                check(2, 5, 8)) {
            statusLabel.setText("The " + updateLabel() + " wins");
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
            }
            player1Button.setEnabled(false);
            player2Button.setEnabled(false);
            return true;
        } else if (turns == 9) {
            statusLabel.setText("Draw");
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
            }
            player1Button.setEnabled(false);
            player2Button.setEnabled(false);
            return true;
        }
        return false;
    }

    private boolean check(int a, int b, int c) {
        return !buttons[a].getText().equals(" ")
                && buttons[a].getText().equals(buttons[b].getText())
                && buttons[a].getText().equals(buttons[c].getText());
    }

    private int checkPossibleWin(int a, int b, int c) {
        if (isEmpty(a) && !isEmpty(b) && buttons[b].getText().equals(buttons[c].getText())) return a;
        if (isEmpty(b) && !isEmpty(a) && buttons[a].getText().equals(buttons[c].getText())) return b;
        if (isEmpty(c) && !isEmpty(a) && buttons[a].getText().equals(buttons[b].getText())) return c;
        return -1;
    }

    private boolean isEmpty(int a) {
        return buttons[a].getText().equals(" ");
    }
}