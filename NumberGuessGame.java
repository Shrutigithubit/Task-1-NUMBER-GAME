import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessGame extends JFrame {

    private int randomNumber;
    private int attemptsLeft;
    private int maxRange;
    private int maxAttempts;
    private int score = 0;

    private JLabel titleLabel, infoLabel, messageLabel, attemptsLabel, scoreLabel;
    private JTextField guessField;
    private JButton guessButton, restartButton;
    private JComboBox<String> difficultyBox;

    private Random random = new Random();

    //  Theme Colors
    private Color backgroundColor = new Color(30, 30, 60);
    private Color panelColor = new Color(45, 45, 85);
    private Color buttonColor = new Color(70, 130, 180);
    private Color restartColor = new Color(220, 20, 60);

    public NumberGuessGame() {

        setTitle(" Number Guess Game");
        setSize(520, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(backgroundColor);

        // ===== TITLE =====
        titleLabel = new JLabel("🎮 NUMBER GUESS GAME 🎮", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7, 1, 12, 12));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        centerPanel.setBackground(panelColor);

        difficultyBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        styleComponent(difficultyBox);
        centerPanel.add(difficultyBox);

        infoLabel = new JLabel("Select Difficulty to Start", SwingConstants.CENTER);
        styleLabel(infoLabel);
        centerPanel.add(infoLabel);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        styleLabel(messageLabel);
        centerPanel.add(messageLabel);

        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.BOLD, 16));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        centerPanel.add(guessField);

        guessButton = new JButton("Guess");
        styleButton(guessButton, buttonColor);
        centerPanel.add(guessButton);

        restartButton = new JButton("Restart Game");
        styleButton(restartButton, restartColor);
        centerPanel.add(restartButton);

        attemptsLabel = new JLabel("", SwingConstants.CENTER);
        styleLabel(attemptsLabel);
        centerPanel.add(attemptsLabel);

        add(centerPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.YELLOW);
        add(scoreLabel, BorderLayout.SOUTH);

        // ===== EVENTS =====
        difficultyBox.addActionListener(e -> startGame());
        guessButton.addActionListener(e -> checkGuess());
        restartButton.addActionListener(e -> resetGame());

        setVisible(true);
    }

    private void startGame() {

        String level = (String) difficultyBox.getSelectedItem();

        switch (level) {
            case "Easy":
                maxRange = 50;
                maxAttempts = 10;
                break;
            case "Medium":
                maxRange = 100;
                maxAttempts = 10;
                break;
            case "Hard":
                maxRange = 200;
                maxAttempts = 10;
                break;
        }

        infoLabel.setText(level + " Level | Range: 1-" + maxRange +
                " | Attempts: " + maxAttempts);

        resetGame();
    }

    private void resetGame() {

        randomNumber = random.nextInt(maxRange) + 1;
        attemptsLeft = maxAttempts;

        messageLabel.setForeground(Color.WHITE);
        messageLabel.setText("Game Started! Start Guessing ");
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        guessField.setText("");
    }

    private void checkGuess() {

        if (attemptsLeft <= 0) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Game Over! Click Restart.");
            return;
        }

        try {
            int guess = Integer.parseInt(guessField.getText());

            if (guess < 1 || guess > maxRange) {
                messageLabel.setForeground(Color.PINK);
                messageLabel.setText("Enter number between 1 and " + maxRange);
                return;
            }

            attemptsLeft--;

            if (guess == randomNumber) {
                score += attemptsLeft * 10;
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText(" Correct! Number was " + randomNumber);
                scoreLabel.setText("Score: " + score);
                attemptsLeft = 0;
            }
            else if (guess > randomNumber) {
                messageLabel.setForeground(Color.ORANGE);
                messageLabel.setText("Too High! ");
            }
            else {
                messageLabel.setForeground(Color.CYAN);
                messageLabel.setText("Too Low! ");
            }

            attemptsLabel.setText("Attempts Left: " + attemptsLeft);

            if (attemptsLeft == 0 && guess != randomNumber) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Game Over! Number was " + randomNumber);
            }

        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please enter a valid number!");
        }
    }

    // 🎨 Styling Methods
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
    }

    private void styleComponent(JComponent comp) {
        comp.setFont(new Font("Arial", Font.BOLD, 16));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessGame::new);
    }
}
