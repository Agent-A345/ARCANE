# ARCANE

ARCANE: A Java-based encryption and decryption system using rotor-based logic, inspired by the Enigma machine, with additional cipher layers like Caesar and Vigenère. This project allows for multi-layered encryption and secure message handling.

## Features
1. **Rotor-based Encryption**: Inspired by Enigma, ARCANE uses customizable rotor settings for added security.
2. **Multi-Layered Ciphers**: Supports various encryption techniques:
   - **Caesar Cipher**: Shifts each letter by a specified number.
   - **Vigenère Cipher**: Uses a keyword to shift each letter based on its position.
   - **Atbash Cipher**: Mirrors the alphabet (A ↔ Z, B ↔ Y, etc.).
   - **ROT13 Cipher**: Shifts each letter by 13 places.
   - **Rail Fence Cipher**: Encrypts text by writing in a zigzag pattern across multiple rows.
3. **Multi-Step Decryption**: Decrypts using the same rotor and cipher settings as encryption.

## Code Structure
- **ArcaneMachine.java**: Core encryption/decryption logic, including rotor settings.
- **ArcaneUI.java**: Handles user inputs and displays the interface.
- **CaesarCipher.java**: Implements Caesar Cipher encryption and decryption.
- **VigenereCipher.java**: Implements Vigenère Cipher.
- **AtbashCipher.java**: Implements Atbash Cipher.
- **ROT13Cipher.java**: Implements ROT13 Cipher.
- **RailFenceCipher.java**: Implements Rail Fence Cipher.

## Installation

### Prerequisites
- **Java Development Kit (JDK)**: Ensure JDK is installed.
- **IDE**: IntelliJ, Eclipse, or any other Java IDE.

### Steps
1. Clone the repository: `git clone https://github.com/Agent-A345/ARCANE.git`
2. Open the project in your IDE.
3. Build and run the project.

## Types of Cipher Keys Required for each Cipher
- **Caesar Cipher**: Numeric cipher key required.
- **Vigenère Cipher**: Word as a cipher key required (e.g., "KEY", "DOG").
- **Rail Fence Cipher**: Numeric cipher key required.
- **Atbash Cipher**: No cipher key required.
- **ROT13 Cipher**: No cipher key required.

## How to Execute
1. Enter a message to be encrypted.
2. Enter the rotor values.
3. Select the desired cipher language.
4. Provide the required cipher key.
5. Click on the "Encrypt" button.
6. Manually decrypt the encrypted message as per the cipher language used (i.e., intermediate decryption).
7. Check whether the intermediate encryption is correct.
8. Enter rotor values and check the final decryption.
9. Upon entering the correct rotor values, you get the original message.

## Contributors
1. Atharva Pagar(me)
2. Tanmay Mewada
3. Omkar Malasne
4. Abhishek Kadam

## License
This project is licensed under the Apache License 2.0.
