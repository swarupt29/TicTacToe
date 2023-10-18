package com.game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class Game {
    private JFrame frmTicTacToe; // The main application window
    private JLabel[][] labels; // An array of labels for the Tic Tac Toe grid
    private boolean isXPlayerTurn = true; // A boolean to keep track of whose turn it is (X or O)
    private boolean isGameOver = false; // A boolean to check if the game is over
    private String playerXName; // Player X's name
    private String playerOName; // Player O's name

    public static void main(String[] args) {
        // This is the entry point of the application
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Create an instance of the Game class
                    Game window = new Game();
                    // Make the game window visible
                    window.frmTicTacToe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Game() {
        // Constructor for the Game class
        initializePlayers(); // Initialize player names
        initialize(); // Initialize the game window
    }

    // Initialize the player names
    void initializePlayers() {
        // Create a panel for player name input
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Create input fields for player names
        JTextField xNameField = new JTextField();
        JTextField oNameField = new JTextField();

        // Add labels and input fields to the panel
        panel.add(new JLabel("Player X Name:"));
        panel.add(xNameField);
        panel.add(new JLabel("Player O Name:"));
        panel.add(oNameField);

        // Show the input dialog for entering player names
        int result = JOptionPane.showConfirmDialog(frmTicTacToe, panel, "Enter Player Names", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            playerXName = xNameField.getText();
            playerOName = oNameField.getText();
        } else {
            System.exit(0); // Exit the application if "Cancel" is clicked
        }
    }

    // Initialize the game window
    private void initialize() {
        frmTicTacToe = new JFrame(); // Create the main application window
        frmTicTacToe.setResizable(false);
        frmTicTacToe.setTitle("Tic Tac Toe");
        frmTicTacToe.getContentPane().setBackground(new Color(255, 153, 0)); // Set the background color
        frmTicTacToe.setBounds(100, 100, 315, 379); // Set the window size and position
        frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        frmTicTacToe.getContentPane().setLayout(null); // Use absolute layout for components

        JLabel title = new JLabel("Tic Tac Toe"); // Create a title label
        title.setBounds(52, 20, 206, 62); // Set the position and size of the title label
        frmTicTacToe.getContentPane().add(title); // Add the title label to the window
        title.setForeground(new Color(255, 255, 255)); // Set the title text color
        title.setFont(new Font("Candara", Font.BOLD, 40)); // Set the title font

        labels = new JLabel[3][3]; // Create an array of labels for the Tic Tac Toe grid

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                labels[i][j] = new JLabel(); // Create a label for each grid cell
                labels[i][j].setFont(new Font("Arial", Font.PLAIN, 40)); // Set the font for the labels
                labels[i][j].setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
                labels[i][j].setOpaque(true); // Allow the label to have a background color
                labels[i][j].setBackground(Color.WHITE); // Set the initial background color
                labels[i][j].setBounds(50 + 70 * j, 78 + 70 * i, 64, 64); // Position and size of each label
                frmTicTacToe.getContentPane().add(labels[i][j]); // Add the label to the window
                final int x = i;
                final int y = j;
                labels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Event handler for mouse click on a grid cell
                        if (labels[x][y].getText().equals("") && !isGameOver) {
                            if (isXPlayerTurn) {
                                labels[x][y].setText("X"); // Set "X" in the cell
                            } else {
                                labels[x][y].setText("O"); // Set "O" in the cell
                            }
                            isXPlayerTurn = !isXPlayerTurn; // Toggle the player's turn
                            isGameOver(); // Check if the game is over
                        }
                    }
                });
            }
        }

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        frmTicTacToe.setJMenuBar(menuBar);

        // Create an "About" menu
        JMenu aboutMenu = new JMenu("About");
        menuBar.add(aboutMenu);

        // Create an "About" menu item
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenu.add(aboutMenuItem);

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutDialog(); // Show the "About" dialog when the menu item is clicked
            }
        });
        
        frmTicTacToe.setVisible(true); // Make the game window visible
    }

    void showAboutDialog() {
        // Define the content of the "About" dialog
        String aboutMessage = "Tic Tac Toe Game\n" +
                "Created by Swarup Thamke\n" +
                "Version 1.0";
        
        // Show the "About" dialog with the specified message
        JOptionPane.showMessageDialog(frmTicTacToe, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public void isGameOver() {
        // Check for a win
        for (int i = 0; i < 3; i++) {
            if (labels[i][0].getText().equals(labels[i][1].getText()) && labels[i][1].getText().equals(labels[i][2].getText()) && !labels[i][0].getText().isEmpty()) {
                isGameOver = true;
                announceWinner(labels[i][0].getText());
                break;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (labels[0][i].getText().equals(labels[1][i].getText()) && labels[1][i].getText().equals(labels[2][i].getText()) && !labels[0][i].getText().isEmpty()) {
                isGameOver = true;
                announceWinner(labels[0][i].getText());
                break;
            }
        }

        if (labels[0][0].getText().equals(labels[1][1].getText()) && labels[1][1].getText().equals(labels[2][2].getText()) && !labels[0][0].getText().isEmpty()) {
            isGameOver = true;
            announceWinner(labels[0][0].getText());
        }

        if (labels[0][2].getText().equals(labels[1][1].getText()) && labels[1][1].getText().equals(labels[2][0].getText()) && !labels[0][2].getText().isEmpty()) {
            isGameOver = true;
            announceWinner(labels[0][2].getText());
        }

        // Check for a tie
        boolean isTie = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (labels[i][j].getText().isEmpty()) {
                    isTie = false;
                    break;
                }
            }
        }
        if (isTie) {
            isGameOver = true;
            announceWinner("Tie");
        }
    }

    public void announceWinner(String winnerSymbol) {
        String winnerName = (winnerSymbol.equals("X")) ? playerXName : playerOName; // Determine the winner's name
        String message = winnerName + " (" + winnerSymbol + ") wins"; // Compose the win message
        
        if (winnerSymbol.equals("Tie")) {
            message = "It's a Tie!";
        }

        int choice = JOptionPane.showConfirmDialog(frmTicTacToe, message + "\nPlay again?", "Result", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame(); // Reset the game if the player chooses to play again
        } else {
            frmTicTacToe.dispose(); // Close the application when the game is closed
            System.exit(0); // Exit the application
        }
    }

    public void resetGame() {
        isGameOver = false; // Reset the game state
        isXPlayerTurn = true; // Reset the player's turn to X
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                labels[i][j].setText(""); // Clear the grid cells
            }
        }
    }
}
