import javax.swing.*;
import java.io.*;
import java.util.*;

    public class POEpart2 {
        static int totalMessagesSent = 0;
        static ArrayList<Message> messages = new ArrayList<>();
        static String registeredUsername = "";  // To store the registered username
        static String registeredPassword = "";  // To store the registered password

        public static void main(String[] args) {
            // Register the user
            register();

            // Login the user
            if (!login()) {
                JOptionPane.showMessageDialog(null, "Login failed. Exiting...");
                return;
            }

            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

            // Get the message limit from the user
            int messageLimit = getMessageLimit();
            if (messageLimit <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid message limit. Exiting...");
                return;
            }

            // Start the main menu loop
            while (true) {
                int choice = getMenuChoice();

                switch (choice) {
                    case 1 -> sendMessage(messageLimit);
                    case 2 -> showRecentMessages();
                    case 3 -> quitProgram();
                    default -> JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid option.");
                }
            }
        }

        // Method to register a new user
        private static void register() {
            JOptionPane.showMessageDialog(null, "Registration Page");

            // Get username and password for registration
            registeredUsername = JOptionPane.showInputDialog("Enter a username for registration:");
            registeredPassword = JOptionPane.showInputDialog("Enter a password for registration:");

            JOptionPane.showMessageDialog(null, "Registration successful!");
        }

        // Method to login the user
        private static boolean login() {
            JOptionPane.showMessageDialog(null, "Login Page");

            // Get username and password for login
            String username = JOptionPane.showInputDialog("Enter username:");
            String password = JOptionPane.showInputDialog("Enter password:");

            // Check if the entered credentials match the registered credentials
            if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Exiting.");
                return false;
            }
        }

        // Method to get the message limit from the user using JOptionPane
        private static int getMessageLimit() {
            while (true) {
                String input = JOptionPane.showInputDialog("How many messages would you like to send?");
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                }
            }
        }

        // Method to get the menu choice from the user
        private static int getMenuChoice() {
            String[] options = {"Send Messages", "Show Recently Sent Messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Choose an option:",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
            return choice + 1;  // Returning the menu option as 1, 2, or 3
        }

        // Method to handle sending a message
        private static void sendMessage(int messageLimit) {
            if (totalMessagesSent < messageLimit) {
                Message msg = new Message();
                msg.createMessage();

                String userChoice = msg.sentMessage();

                switch (userChoice) {
                    case "1":
                        messages.add(msg);
                        totalMessagesSent++;
                        msg.display();
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(null, "Message disregarded.");
                        break;
                    case "3":
                        msg.storeMessage();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid input. Message not sent.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Message limit reached.");
            }
        }

        // Method to show recently sent messages
        private static void showRecentMessages() {
            if (messages.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No messages sent yet.");
            } else {
                StringBuilder recentMessages = new StringBuilder("Recently Sent Messages:\n");
                for (Message msg : messages) {
                    recentMessages.append(msg.getSender()).append(": ").append(msg.getContent()).append("\n");
                }
                JOptionPane.showMessageDialog(null, recentMessages.toString());
            }
        }

        // Method to quit the program
        private static void quitProgram() {
            JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent);
            System.exit(0);
        }

        // Message class
        static class Message {
            private String content;
            private String sender;

            // Method to create a message
            public void createMessage() {
                sender = JOptionPane.showInputDialog("Enter your name:");

                // Validate sender's name
                while (sender.trim().isEmpty()) {
                    sender = JOptionPane.showInputDialog("Name cannot be empty. Enter your name:");
                }

                content = JOptionPane.showInputDialog("Enter your message:");

                // Validate message content
                while (content.trim().isEmpty()) {
                    content = JOptionPane.showInputDialog("Message cannot be empty. Enter your message:");
                }
            }

            // Display menu for the user to choose how to handle the message
            public String sentMessage() {
                String[] options = {"Send this message", "Disregard message", "Store for later"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Choose an option:",
                        "Message Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                return String.valueOf(choice + 1);  // Return "1", "2", or "3"
            }

            // Display the message details
            public void display() {
                JOptionPane.showMessageDialog(null, "--- Message Sent ---\nFrom: " + sender + "\nMessage: " + content);
            }

            // Store the message (to a file in this case, but you could use a database or other storage)
            public void storeMessage() {
                try (FileWriter writer = new FileWriter("storedMessages.txt", true)) {
                    writer.write("From: " + sender + "\n");
                    writer.write("Message: " + content + "\n\n");
                    JOptionPane.showMessageDialog(null, "Message stored for later.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error storing the message: " + e.getMessage());
                }
            }

            // Getter methods for displaying recent messages
            public String getSender() {
                return sender;
            }

            public String getContent() {
                return content;
            }
        }
    }


