# ARCANE
ARCANE: A Java-based encryption and decryption system using rotor-based logic, inspired by the Enigma machine, with additional cipher layers like Caesar and Vigenère. This project allows for multi-layered encryption and secure message handling.

#Features
1)Rotor-based Encryption: Inspired by Enigma, ARCANE uses customizable rotor settings for added security.
2)Multi-Layered Ciphers: Supports various encryption techniques:
->Caesar Cipher: Shifts each letter by a specified number.
->Vigenère Cipher: Uses a keyword to shift each letter based on its position.
->Atbash Cipher: Mirrors the alphabet (A ↔ Z, B ↔ Y, etc.).
->ROT13 Cipher: Shifts each letter by 13 places.
->Rail Fence Cipher: Encrypts text by writing in a zigzag pattern across multiple rows.
3)Multi-Step Decryption: Decrypts using the same rotor and cipher settings as encryption.

#Code Structure
->ArcaneMachine.java: Core encryption/decryption logic, including rotor settings.
->ArcaneUI.java: Handles user inputs and displays the interface.
->CaesarCipher.java: Implements Caesar Cipher encryption and decryption.
->VigenereCipher.java: Implements Vigenère Cipher.
->AtbashCipher.java: Implements Atbash Cipher.
->ROT13Cipher.java: Implements ROT13 Cipher.
->RailFenceCipher.java: Implements Rail Fence Cipher.

#Installation
=>Prerequisites
Java Development Kit (JDK)
IDE: IntelliJ, Eclipse, or any other Java IDE.
=>Steps
Clone the repository: git clone https://github.com/Agent-A345/ARCANE.git
Open the project in your IDE.
Build and run the project.

#Types of Cipher Keys Required for each Cipher
->Caesar Cipher: Numeric cipher key required.
->Vigenère Cipher: Word as a cipher key required (e.g., "KEY", "DOG").
->Rail Fence Cipher: Numeric cipher key required.
->Atbash Cipher: No cipher key required.
->ROT13 Cipher: No cipher key required.


#How to execute
->Enter a message to be encrypted
->Enter the rotor values
->Select the desired cipher language
->Provide the required cipher key
->Click on Encrypt button
->Manually decrypt the encrypted message as per cipher language used ie intermediate decryption
->Check whether the intermediate encryption is correct or not
->Enter rotor values and check the final decryption
->Upon entering correct rotor values you get the Original Message

#License
This project is licensed under the Apache License 2.0
