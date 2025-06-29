import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AIChatBot {

    private static Map<String, String> knowledgeBase = new HashMap<>();

    public static void main(String[] args) {
        // Initialize knowledge base
        initKnowledgeBase();

        // Build GUI
        JFrame frame = new JFrame("AI ChatBot");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        JTextField inputField = new JTextField();

        JButton sendButton = new JButton("Send");

        JScrollPane scrollPane = new JScrollPane(chatArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.add(sendButton, BorderLayout.EAST);

        // Event listener
        ActionListener sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText().trim();
                chatArea.append("You: " + userInput + "\n");
                String response = getResponse(userInput);
                chatArea.append("Bot: " + response + "\n\n");
                inputField.setText("");
            }
        };

        inputField.addActionListener(sendAction);
        sendButton.addActionListener(sendAction);

        frame.setVisible(true);
    }

    private static void initKnowledgeBase() {
        knowledgeBase.put("hello", "Hello! How can I help you?");
        knowledgeBase.put("hi", "Hi there! What can I do for you?");
        knowledgeBase.put("how are you", "I'm just a bot, but I'm doing great! Thanks for asking.");
        knowledgeBase.put("what is your name", "I am AI ChatBot built in Java.");
        knowledgeBase.put("bye", "Goodbye! Have a great day.");
        knowledgeBase.put("thank you", "You're welcome!");
        knowledgeBase.put("what is java", "Java is a popular programming language used for developing various types of applications.");
        knowledgeBase.put("what is artificial intelligence", "Artificial Intelligence is the simulation of human intelligence processes by machines.");
        // Add more FAQ patterns here
    }

    private static String getResponse(String input) {
        input = preprocess(input);

        // Exact match in knowledge base
        if (knowledgeBase.containsKey(input)) {
            return knowledgeBase.get(input);
        }

        // Partial match / keyword-based
        for (String key : knowledgeBase.keySet()) {
            if (input.contains(key)) {
                return knowledgeBase.get(key);
            }
        }

        // Fallback
        return "I'm sorry, I don't understand that.";
    }

    private static String preprocess(String input) {
        // Basic NLP: Lowercase, remove punctuation
        input = input.toLowerCase();
        input = input.replaceAll("[^a-zA-Z0-9\\s]", "");
        return input;
    }
}
