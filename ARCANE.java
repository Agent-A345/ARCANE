//ARCANE
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ARCANE {
    private String originalMessage; // To store the original message

    // UI class
    public class ArcaneUI extends JFrame {
        private JPasswordField messageInput;
        private JPasswordField[] rotorPositions;
        private JComboBox<String> cipherChoice;
        private JTextField cipherKey;
        private JTextArea encryptedOutput;
        private JTextField intermediateDecryptionInput;
        private JTextField finalDecryptionInput;
        private JPasswordField[] finalRotorPositions;
        private JLabel attemptLabel;
        private JLabel timerLabel;
        private JLabel resultLabel;
        private JLabel hintLabel;
        private JButton encryptButton, intermediateDecryptButton, finalDecryptButton;
        private int attempts;
        private Timer timer;
        private int seconds;
        private ArcaneMachine arcaneMachine;
        private String intermediateDecryption;
        private Cipher selectedCipher;

        public ArcaneUI() {
            attempts = 3;
            arcaneMachine = new ArcaneMachine(new Rotor[]{
                    new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
                    new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
                    new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO")
            });

            // UI Elements
            messageInput = new JPasswordField(20);
            rotorPositions = new JPasswordField[3];
            for (int i = 0; i < 3; i++) {
                rotorPositions[i] = new JPasswordField(3);
            }
            cipherChoice = new JComboBox<>(new String[]{"Caesar", "Vigenere", "Atbash", "ROT13", "Rail Fence"});
            cipherKey = new JTextField(10);
            encryptedOutput = new JTextArea(5, 20);
            encryptedOutput.setBackground(Color.DARK_GRAY);
            encryptedOutput.setForeground(Color.LIGHT_GRAY);
            intermediateDecryptionInput = new JTextField(20);
            finalDecryptionInput = new JTextField(20);
            finalRotorPositions = new JPasswordField[3];
            // Set a larger font size for the encrypted output text area
            Font outputFont = new Font("Arial", Font.BOLD, 16); // Adjust font size here
            encryptedOutput.setFont(outputFont);

            for (int i = 0; i < 3; i++) {
                finalRotorPositions[i] = new JPasswordField(3);
            }
            attemptLabel = new JLabel("Attempts left: 3");
            timerLabel = new JLabel("Time: 0s");
            resultLabel = new JLabel("Result will appear here");
            hintLabel = new JLabel("Hint:");
            encryptButton = new JButton("Encrypt");
            intermediateDecryptButton = new JButton("Intermediate Decrypt");

            finalDecryptButton = new JButton("Final Decrypt");

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 20, 20, 20); // Enlarge spacing
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Styling for components
            Font labelFont = new Font("Arial", Font.BOLD, 18); // Larger font size
            Color bgColor = new Color(43, 43, 43);
            Color textColor = Color.LIGHT_GRAY;
            attemptLabel.setFont(labelFont);
            timerLabel.setFont(labelFont);
            resultLabel.setFont(labelFont);
            hintLabel.setFont(labelFont);

            getContentPane().setBackground(bgColor);
            attemptLabel.setForeground(textColor);
            timerLabel.setForeground(textColor);
            resultLabel.setForeground(textColor);
            hintLabel.setForeground(textColor);

            // Left column
            JPanel leftPanel = new JPanel(new GridBagLayout());
            leftPanel.setBackground(bgColor);
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel enterMessageLabel = new JLabel("Enter Message:");
            enterMessageLabel.setForeground(textColor);
            enterMessageLabel.setFont(labelFont);
            leftPanel.add(enterMessageLabel, gbc);
            gbc.gridy++;
            leftPanel.add(messageInput, gbc);

            gbc.gridy++;
            JPanel rotorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rotorPanel.setBackground(bgColor);
            JLabel rotorLabel = new JLabel("Rotor Positions(0-9 only):");
            rotorLabel.setForeground(textColor);
            rotorLabel.setFont(labelFont);
            rotorPanel.add(rotorLabel);
            for (JPasswordField rotorPosition : rotorPositions) {
                rotorPosition.setBackground(Color.GRAY);
                rotorPosition.setForeground(Color.BLACK);
                rotorPanel.add(rotorPosition);
            }
            leftPanel.add(rotorPanel, gbc);

            gbc.gridy++;
            JPanel cipherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            cipherPanel.setBackground(bgColor);
            JLabel cipherLabel = new JLabel("Cipher:");
            cipherLabel.setForeground(textColor);
            cipherLabel.setFont(labelFont);
            cipherPanel.add(cipherLabel);
            cipherPanel.add(cipherChoice);
            leftPanel.add(cipherPanel, gbc);

            gbc.gridy++;
            JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            keyPanel.setBackground(bgColor);
            JLabel keyLabel = new JLabel("Cipher Key:");
            keyLabel.setForeground(textColor);
            keyLabel.setFont(labelFont);
            keyPanel.add(keyLabel);
            keyPanel.add(cipherKey);
            leftPanel.add(keyPanel, gbc);

            gbc.gridy++;
            leftPanel.add(encryptButton, gbc);
            gbc.gridy++;
            JLabel encryptedMessageLabel = new JLabel("Encrypted Message:");
            encryptedMessageLabel.setForeground(textColor);
            encryptedMessageLabel.setFont(labelFont);
            leftPanel.add(encryptedMessageLabel, gbc);
            gbc.gridy++;
            leftPanel.add(new JScrollPane(encryptedOutput), gbc);

            // Right column
            JPanel rightPanel = new JPanel(new GridBagLayout());
            rightPanel.setBackground(bgColor);
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel intermediateDecryptionLabel = new JLabel("Intermediate Decryption Input:");
            intermediateDecryptionLabel.setForeground(textColor);
            intermediateDecryptionLabel.setFont(labelFont);
            rightPanel.add(intermediateDecryptionLabel, gbc);
            gbc.gridy++;
            rightPanel.add(new JScrollPane(intermediateDecryptionInput), gbc);

            gbc.gridy++;
            rightPanel.add(intermediateDecryptButton, gbc);

            gbc.gridy++;
            JLabel finalDecryptLabel = new JLabel("Enter Rotor Positions & Final Decryption Input:");
            finalDecryptLabel.setForeground(textColor);
            finalDecryptLabel.setFont(labelFont);
            rightPanel.add(finalDecryptLabel, gbc);
            gbc.gridy++;
            JPanel finalRotorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            finalRotorPanel.setBackground(bgColor);
            JLabel finalRotorLabel = new JLabel("Rotor Positions:");
            finalRotorLabel.setForeground(textColor);
            finalRotorLabel.setFont(labelFont);
            finalRotorPanel.add(finalRotorLabel);
            for (JPasswordField finalRotorPosition : finalRotorPositions) {
                finalRotorPosition.setBackground(Color.GRAY);
                finalRotorPosition.setForeground(Color.BLACK);
                finalRotorPanel.add(finalRotorPosition);
            }
            rightPanel.add(finalRotorPanel, gbc);

            gbc.gridy++;
            rightPanel.add(new JScrollPane(finalDecryptionInput), gbc);

            gbc.gridy++;
            rightPanel.add(finalDecryptButton, gbc);
            gbc.gridy++;
            rightPanel.add(attemptLabel, gbc);
            gbc.gridy++;
            rightPanel.add(timerLabel, gbc);
            gbc.gridy++;
            rightPanel.add(resultLabel, gbc);
            gbc.gridy++;
            rightPanel.add(hintLabel, gbc);

            // Add left and right panels to the main frame
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            add(leftPanel, gbc);
            gbc.gridx = 1;
            add(rightPanel, gbc);

            // Add action listeners
            encryptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleEncryption();
                }
            });

            intermediateDecryptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleIntermediateDecryption();
                }
            });

            finalDecryptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleFinalDecryption();
                }
            });

            // Set up timer
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seconds++;
                    timerLabel.setText("Time: " + seconds + "s");
                }
            });

            // Set JFrame properties
            setTitle("ARCANE");
            setSize(2000, 1200);  // Larger window size for readability
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private String getCipherHint(String cipherName) {
            switch (cipherName) {
                case "Caesar":
                    return "Shift your fate—what lies ahead isn't always what it seems.";
                case "Vigenere":
                    return "A word holds the key, but its path is anything but straight.";
                case "Atbash":
                    return "In the world of opposites, what begins at the end reveals the truth.";
                case "ROT13":
                    return "A simple twist, halfway to the unknown—can you see through it?";
                case "Rail Fence":
                    return "Your message weaves through shadows, but only the keen will follow its tracks.";
                default:
                    return "No hint available for this cipher.";
            }
        }

        private String getCipherKeyHint(String cipherName, String key) {
            switch (cipherName) {
                case "Caesar":
                    int shift = Integer.parseInt(key); // Convert the key to an integer
                    String hint = "in the lightless lair, ill winds lie idle";
                    return randomCapitalize(hint, shift); // Randomly capitalize 'shift' letters in the sentence

                case "Vigenere":
                    StringBuilder asciiHint = new StringBuilder("When in doubt, reach for the agent: ");
                    for (char c : key.toCharArray()) {
                        asciiHint.append((int) c).append("");  // Append ASCII values of each letter in the key
                    }
                    return asciiHint.toString().trim();  // Display ASCII values as the hint

                case "Rail Fence":
                    StringBuilder railFenceHint = new StringBuilder("Lost? Required coordinates are: ");
                    for (char c : key.toCharArray()) {
                        int asciiValue = (int) c;  // ASCII value
                        int reverseAscii = reverseNumber(asciiValue);  // Reverse of the ASCII value
                        railFenceHint.append("Latitude: ").append(asciiValue).append(".").append(generateRandomDecimal())
                                .append(", Longitude: ").append(reverseAscii).append(".").append(generateRandomDecimal()).append(" ");
                    }
                    return railFenceHint.toString().trim();

                case "Atbash":
                    return "To see the whole picture, sometimes you need to take a step back...";

                case "ROT13":
                    return "Something has changed... the answer is closer than you think.";

                default:
                    return "A secret lies within—look closer.";
            }
        }

        private int reverseNumber(int num) {
            int reversed = 0;
            while (num != 0) {
                int digit = num % 10;
                reversed = reversed * 10 + digit;
                num /= 10;
            }
            return reversed;
        }

        // Helper function to generate a random decimal for realistic coordinates
        private String generateRandomDecimal() {
            Random random = new Random();
            return String.format("%03d", random.nextInt(1000));  // Generates a 3-digit random number
        }

        private String randomCapitalize(String sentence, int count) {
            Random random = new Random();
            String[] words = sentence.split(" ");
            int capitalized = 0;

            // Convert the entire sentence into a char array
            char[] sentenceChars = sentence.toCharArray();

            // Keep track of the positions that have already been capitalized
            boolean[] capitalizedPositions = new boolean[sentenceChars.length];

            // Loop until the desired number of characters are capitalized
            while (capitalized < count) {
                // Select a random index within the sentence
                int index = random.nextInt(sentenceChars.length);

                // Only capitalize if it's a letter and not already capitalized
                if (Character.isLowerCase(sentenceChars[index]) && !capitalizedPositions[index]) {
                    sentenceChars[index] = Character.toUpperCase(sentenceChars[index]);
                    capitalizedPositions[index] = true; // Mark this position as capitalized
                    capitalized++;
                }
            }

            return new String(sentenceChars);
        }

        private void handleEncryption() {
            originalMessage = new String(messageInput.getPassword()).trim();
            if (originalMessage.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Message cannot be empty. Please enter a message.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int[] positions = new int[3];
            StringBuilder asciiValues = new StringBuilder("Hint: Rotor values: ");
            for (int i = 0; i < 3; i++) {
                positions[i] = Integer.parseInt(new String(rotorPositions[i].getPassword())) % 26;
                asciiValues.append((int) new String(rotorPositions[i].getPassword()).charAt(0)).append("");
            }
            arcaneMachine.resetRotors(positions);

            originalMessage = new String(messageInput.getPassword()); // Store the original message
            String enigmaEncrypted = arcaneMachine.encryptMessage(originalMessage);

            String cipherName = (String) cipherChoice.getSelectedItem();
            switch (cipherName) {
                case "Caesar":
                    selectedCipher = new CaesarCipher();
                    break;
                case "Vigenere":
                    selectedCipher = new VigenereCipher();
                    break;
                case "Atbash":
                    selectedCipher = new AtbashCipher();
                    break;
                case "ROT13":
                    selectedCipher = new Rot13Cipher();
                    break;
                case "Rail Fence":
                    selectedCipher = new RailFenceCipher();
                    break;
            }

            String finalEncrypted = selectedCipher.encrypt(enigmaEncrypted, cipherKey.getText());
            encryptedOutput.setText(finalEncrypted);

            // Hide the original message and rotor positions
            messageInput.setText("");
            for (JPasswordField rotorPosition : rotorPositions) {
                rotorPosition.setText("*");
            }

            // Disable cipher choice and cipher key fields to hide them
            cipherChoice.setEnabled(false);
            cipherKey.setEnabled(false);

            // Get the cipher key hint based on the selected cipher
            String cipherKeyHint = getCipherKeyHint(cipherName, cipherKey.getText());

            // Display the ASCII values of the rotor positions as a hint and add cipher hints
            String cipherHint = getCipherHint(cipherName);
            hintLabel.setText("<html>" + asciiValues.toString() + "<br><br> Cipher Hint: " + cipherHint + "<br><br> Key Hint: " + cipherKeyHint + "</html>");

            // Start the timer
            seconds = 0;
            timer.start();
        }

        private void handleIntermediateDecryption() {
            String intermediateDecryptionAttempt = intermediateDecryptionInput.getText();
            String expectedIntermediateDecryption = selectedCipher.decrypt(encryptedOutput.getText(), cipherKey.getText());

            if (intermediateDecryptionAttempt.equals(expectedIntermediateDecryption)) {
                intermediateDecryption = intermediateDecryptionAttempt;
                resultLabel.setText("Intermediate Decryption correct. Enter the rotor positions and final decryption input.");
            } else {
                resultLabel.setText("Incorrect intermediate decryption. Try again.");
                attempts--;
                attemptLabel.setText("Attempts left: " + attempts);
                if (attempts == 0) {
                    resultLabel.setText("Decryption failed. No attempts left.");
                    timer.stop();
                    System.exit(0);
                }
            }
        }

        private void handleFinalDecryption() {
            if (intermediateDecryption == null || intermediateDecryption.isEmpty()) {
                resultLabel.setText("Please correctly decrypt the intermediate message first.");
                return;
            }

            int[] finalPositions = new int[3];
            for (int i = 0; i < 3; i++) {
                finalPositions[i] = Integer.parseInt(new String(finalRotorPositions[i].getPassword())) % 26;
            }
            arcaneMachine.resetRotors(finalPositions);

            String finalDecrypted = arcaneMachine.decryptMessage(intermediateDecryption);

            // Compare the final decrypted message with the original message
            if (finalDecrypted.equals(originalMessage)) {
                resultLabel.setText("Decryption successful! The message was: " + finalDecrypted);
                timer.stop();
            } else {
                attempts--;
                if (attempts > 0) {
                    attemptLabel.setText("Attempts left: " + attempts);
                    resultLabel.setText("Incorrect final decryption. Try again.");
                } else {
                    resultLabel.setText("Decryption failed. No attempts left.");
                    timer.stop();
                }
            }
        }
    }

    // Rotor class
    public static class Rotor {
        private String wiring;
        private int position;

        public Rotor(String wiring) {
            this.wiring = wiring;
            this.position = 0;
        }

        public char encrypt(char input) {
            if (!Character.isLetter(input)) return input;
            boolean isLower = Character.isLowerCase(input);
            char upperInput = Character.toUpperCase(input);
            int index = (upperInput - 'A' + position) % 26;
            char result = wiring.charAt(index);
            return isLower ? Character.toLowerCase(result) : result;
        }

        public char decrypt(char input) {
            if (!Character.isLetter(input)) return input;
            boolean isLower = Character.isLowerCase(input);
            char upperInput = Character.toUpperCase(input);
            int index = wiring.indexOf(upperInput);
            char result = (char) ('A' + (index - position + 26) % 26);
            return isLower ? Character.toLowerCase(result) : result;
        }

        public void rotate() {
            position = (position + 1) % 26;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position % 26;
        }
    }

    // ArcaneMachine class
    public static class ArcaneMachine {
        private Rotor[] rotors;

        public ArcaneMachine(Rotor[] rotors) {
            this.rotors = rotors;
        }

        public String encryptMessage(String message) {
            StringBuilder encryptedMessage = new StringBuilder();

            for (char ch : message.toCharArray()) {
                for (Rotor rotor : rotors) {
                    ch = rotor.encrypt(ch);
                }
                encryptedMessage.append(ch);
                advanceRotors();
            }

            return encryptedMessage.toString();
        }

        public String decryptMessage(String encryptedMessage) {
            StringBuilder decryptedMessage = new StringBuilder();

            for (char ch : encryptedMessage.toCharArray()) {
                for (int i = rotors.length - 1; i >= 0; i--) {
                    ch = rotors[i].decrypt(ch);
                }
                decryptedMessage.append(ch);
                advanceRotors();
            }

            return decryptedMessage.toString();
        }

        private void advanceRotors() {
            for (int i = 0; i < rotors.length; i++) {
                rotors[i].rotate();
                if (rotors[i].getPosition() != 0) {
                    break;
                }
            }
        }

        public void resetRotors(int[] positions) {
            for (int i = 0; i < rotors.length; i++) {
                rotors[i].setPosition(positions[i]);
            }
        }
    }

    // Cipher Interface
    public interface Cipher {
        String encrypt(String message, String key);

        String decrypt(String message, String key);
    }

    // Example Cipher Implementations
    public static class CaesarCipher implements Cipher {
        @Override
        public String encrypt(String message, String key) {
            int shift = Integer.parseInt(key);
            StringBuilder encrypted = new StringBuilder();

            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? 'a' : 'A';
                    c = (char) ((c - base + shift) % 26 + base);
                }
                encrypted.append(c);
            }

            return encrypted.toString();
        }

        @Override
        public String decrypt(String message, String key) {
            return encrypt(message, Integer.toString(26 - Integer.parseInt(key)));
        }
    }

    public static class VigenereCipher implements Cipher {
        @Override
        public String encrypt(String message, String key) {
            StringBuilder result = new StringBuilder();
            key = key.toUpperCase();
            int keyIndex = 0;

            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? 'a' : 'A';
                    c = (char) ((c - base + (key.charAt(keyIndex % key.length()) - 'A')) % 26 + base);
                    keyIndex++;
                }
                result.append(c);
            }

            return result.toString();
        }

        @Override
        public String decrypt(String message, String key) {
            StringBuilder result = new StringBuilder();
            key = key.toUpperCase();
            int keyIndex = 0;

            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? 'a' : 'A';
                    c = (char) ((c - base - (key.charAt(keyIndex % key.length()) - 'A') + 26) % 26 + base);
                    keyIndex++;
                }
                result.append(c);
            }

            return result.toString();
        }
    }

    public static class AtbashCipher implements Cipher {
        @Override
        public String encrypt(String message, String key) {
            StringBuilder result = new StringBuilder();

            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? 'a' : 'A';
                    c = (char) (base + ('Z' - Character.toUpperCase(c)));
                }
                result.append(c);
            }

            return result.toString();
        }

        @Override
        public String decrypt(String message, String key) {
            return encrypt(message, key);  // Atbash is symmetric
        }
    }

    public static class Rot13Cipher implements Cipher {
        @Override
        public String encrypt(String message, String key) {
            StringBuilder result = new StringBuilder();

            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? 'a' : 'A';
                    c = (char) ((c - base + 13) % 26 + base);
                }
                result.append(c);
            }

            return result.toString();
        }

        @Override
        public String decrypt(String message, String key) {
            return encrypt(message, key);  // ROT13 is symmetric
        }
    }

    public static class RailFenceCipher implements Cipher {
        @Override
        public String encrypt(String message, String key) {
            int depth = Integer.parseInt(key);
            if (depth <= 1) return message;

            StringBuilder[] lines = new StringBuilder[depth];
            for (int i = 0; i < depth; i++) {
                lines[i] = new StringBuilder();
            }

            int line = 0;
            boolean down = true;
            for (char c : message.toCharArray()) {
                lines[line].append(c);
                if (line == depth - 1) {
                    down = false;
                } else if (line == 0) {
                    down = true;
                }
                line += down ? 1 : -1;
            }

            StringBuilder result = new StringBuilder();
            for (StringBuilder lineBuilder : lines) {
                result.append(lineBuilder);
            }

            return result.toString();
        }

        @Override
        public String decrypt(String message, String key) {
            int depth = Integer.parseInt(key);
            if (depth <= 1) return message;

            char[] decrypted = new char[message.length()];
            int[] indices = new int[depth];
            boolean[] down = new boolean[depth];

            int line = 0;
            for (int i = 0; i < message.length(); i++) {
                indices[line]++;
                if (line == depth - 1) {
                    down[line] = false;
                } else if (line == 0) {
                    down[line] = true;
                }
                line += down[line] ? 1 : -1;
            }

            line = 0;
            int pos = 0;
            for (int i = 0; i < depth; i++) {
                for (int j = 0; j < indices[i]; j++) {
                    decrypted[pos++] = message.charAt(line++);
                }
            }

            line = 0;
            for (int i = 0; i < message.length(); i++) {
                decrypted[i] = decrypted[line];
                if (line == depth - 1) {
                    down[line] = false;
                } else if (line == 0) {
                    down[line] = true;
                }
                line += down[line] ? 1 : -1;
            }

            return new String(decrypted);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ARCANE().new ArcaneUI().setVisible(true);
            }
        });
    }
}
