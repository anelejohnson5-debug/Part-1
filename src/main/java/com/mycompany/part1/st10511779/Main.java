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
    }

}

