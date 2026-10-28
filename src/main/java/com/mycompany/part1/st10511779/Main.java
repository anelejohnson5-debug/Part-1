/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.part1.st10511779;

import java.util.Scanner;


/**
 *
 * @author Student
 */
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name");
        String Name = input.nextLine();

        System.out.println("Enter your surname");
        String Surname = input.nextLine();

        System.out.println("Enter your username");
        String Username = input.nextLine();

        System.out.println("Enter password");
        String Password = input.nextLine();

        System.out.println("Please enter your cellphone number");
        String CellphoneNum = input.nextLine();

        Login obj = new Login(Username, Surname, Password, Name, CellphoneNum);
        obj.RegisterUser(Username, Password);
   
        String result = obj.RegisterUser(Username, Password);
       

        if (!result.equals("The two above conditions have been met, and the user has been registered successfully\n")) { //this if statement makes the user appear if the user registered correctly
            
             System.out.println("Please enter your Username");
            String EnteredUsername = input.nextLine();

            System.out.println("Please enter your password");
            String EnteredPassword = input.nextLine();

            obj.LoginUser(EnteredUsername, EnteredPassword, Username, Password);
            obj.returnLoginStatus(EnteredUsername, EnteredPassword, Username, Password);
        } else {
            System.out.println("Registered user is false, please attempt again");
        }
        
        System.out.println("Please enter your Username");
        String enteredUsername = input.nextLine();
        System.out.println("Please enter your password");
        String enteredPassword = input.nextLine();
        
        boolean loggedIn = obj.LoginUser(enteredUsername, enteredPassword, Username, Password);
        obj.returnLoginStatus(enteredUsername, enteredPassword, Username, Password);
        
        if (!loggedIn);{
        System.out.println("You must log in successfully before sending a message/messages");
        
        
    }
        System.out.println("Welcome to QuikChat");
        int loadedCount = Message.loadStoredMessages();  //reads storedMessages.json into the arrays
        if (loadedCount > 0) {
            System.out.println("Stored messages loaded from storedMessages.json:\n" + Message.displayStoredMessages());
        }
        System.out.println("How many messages do want to enter?");
        int maxMessages = Integer.parseInt(input.nextLine());
        int messagesEntered = 0;
        int totalMessagesSent = 0;
        
        while(true) {
         System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
         String menuOption = input.nextLine();
         
         if (menuOption.equals("1")) {
            if (messagesEntered >= maxMessages){
                System.out.println("You have reached your limit of " + maxMessages + "messages");  // message to display message limit
                continue;
            }
             System.out.println("Recipient (e.g +277186930020): ");
             String recipientCell = input.nextLine();
             System.out.println("Message: ");
             String messageText = input.nextLine();
             
             Message newMessage = new Message(recipientCell, messageText);
             if(messageText.length() > 250){
                 System.out.println("Please enter a message of less than 250 characters");
                 continue;
            }
             String cellCheckResult = newMessage.checkRecipientCell();
             System.out.println(cellCheckResult);
             if (!cellCheckResult.equals("Cell phone number successfully captured."))
                 continue;
             System.out.println("Message sent");
             System.out.println("1) Send Message 2) Disregard Message 3) Store Message to send later");
             int sendChoice = Integer.parseInt(input.nextLine());
             System.out.println(newMessage.sentMessage(sendChoice));
             if (sendChoice == 1) System.out.println(newMessage.details());
             totalMessagesSent = newMessage.returnTotalMessages();
             messagesEntered++;
         } else if (menuOption.equals("2")) {
             System.out.println("Coming Soon"); // when 2) is selected nothing should display but "Coming Soon"
             
         } else if (menuOption.equals("3")){
             break;
         } else if (menuOption.equals("4")) {
            storedMessagesMenu(input, Username);         
         } else {
             System.out.println("Invalid menuOption");
         }
         
        }
        System.out.println("Total messages sent: " + totalMessagesSent);
        }
        
    
        private static void storedMessagesMenu(Scanner input, String sender) {
            String choice = "";
            while(!choice.equals("g")){
                System.out.println("\nStored Messages\n"
                        + "a) Display sender and recipient of all stored messages\n"
                        + "b) Display the longest stored message\n"
                        + "c) Search for a message ID\n"
                        + "d) Search for all messages for a recipient\n"
                        + "e) Delete a message using the message hash\n"
                        + "f) Display a report of all stored messages\n"
                        + "g) Back to main menu\n");
                choice = input.nextLine().trim().toLowerCase();
                if (choice.equals("a")) {
                    System.out.println(Message.displaySendersAndRecipients(sender));
                } else if (choice.equals("b")) {
                    String longest = Message.getLongestStoredMessage();
                    System.out.println(longest == null ? "No stored messages." : longest);
                } else if (choice.equals("c")) {
                    System.out.println("Enter the message ID: ");
                    String[] found = Message.searchByMessageID(input.nextLine().trim());
                    System.out.println(found == null ? "Message ID not found."
                            : "Recipient: " + found[0] + "\nMessage: " + found[1]);
                } else if (choice.equals("d")) {
                    System.out.println("Enter the recipient: ");
                    String[] matches = Message.searchByRecipient(input.nextLine().trim());
                    if (matches.length == 0) {
                        System.out.println("No messages found for that recipient.");
                    }
                    for (int i = 0; i < matches.length; i++) {
                        System.out.println(matches[i]);
                    }
                } else if (choice.equals("e")) {
                    System.out.println("Enter the message hash: ");
                    System.out.println(Message.deleteByHash(input.nextLine()).trim());
                } else if (choice.equals("f")) {
                    System.out.println(Message.buildReport());
                } else if (!choice.equals("g")) {
                    System.out.println("Invalid option.");
                    
                }
                        
            }
        }
         
        
        
        
    }




