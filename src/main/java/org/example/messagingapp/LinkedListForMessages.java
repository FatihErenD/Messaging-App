package org.example.messagingapp;

public class LinkedListForMessages {
    public Node first;
    private String chatName;
    private int size = 0;


    public LinkedListForMessages(String chatName) {
        this.chatName = chatName;
    }

    public void append(String message, boolean isSender) {
        if (first == null) {
            first = new Node(message, isSender);
            size++;
            return;
        }
        Node temp = first;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(message, isSender);
        size++;
    }

    public String getChatName() {
        return chatName;
    }

    public int getSize() {
        return size;
    }


    public class Node {
        public Node next;

        private String message;
        private boolean isSender;

        Node(String message, boolean isSender) {
            this.message = message;
            this.isSender = isSender;
            this.next = null;
        }

        public String getMessageInfo() {
            return message;
        }

        public boolean isSenderMessage() {
            return isSender;
        }
    }
}
