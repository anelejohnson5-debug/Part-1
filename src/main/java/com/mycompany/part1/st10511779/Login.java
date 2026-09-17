/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1.st10511779;

/**
 *
 * @author Student
 */
public class Login {
    private String Username;
    private String Password;
    private String CellphoneNum;
    private String Name;
    private String Surname;
    public Login(String Username, String Password, String CellphoneNum, String Name, String Surname){
       this.Username=Username;
       this.Name=Name;
       this.Password=Password;
       this.Surname=Surname;
       this.CellphoneNum=CellphoneNum;
       
       
    
    }
    public boolean CheckUsername(String Username){ // this method checks if the the users username matches the requirements, it will output a message if true and another message if false
        boolean CheckUser;
        String outcome;
        if(Username.contains("_") && Username.length() <=5){
            CheckUser = true;
            outcome = "Username successfully captured";        
            
        }else{
            CheckUser = false;
            outcome = "Username is not correctly formatted please ensure that your Username contains an underscore and is no longer than 5 characters in length";
        }
        System.out.println(outcome);
        return CheckUser;
        
    }
    public boolean CheckPasswordComplexity(String Password){ // this method checks the complexity of the Password and it will output a message if the password meets the complexity requirements
        boolean CheckPassword;
        String outcome;
        String PasswordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; //CheckPassword Regex
        
        //Java-Concepts By Ram, 2024. How to Write a Regex to Validate Passwords | Java Regex Tutorial| Regex in java. Available at: https://www.youtube.com/watch?v=Bwc6apOcMlA [Accessed 17 September 2026].
        
       if(Password.matches(PasswordRegex)){
           outcome = "Password successfully captured";
           CheckPassword = true;
           
       }else{
           outcome = "Password is not correctly formatted;please ensure that the password contains atleast eight characters,"
                   + " a number and a special character";
           CheckPassword = false;
           
       }
       return CheckPassword;
    }
    public boolean CheckCellphoneNum(String CellphoneNum){
        boolean CheckCellphoneNum;
        String outcome;
        String CellphoneRegex = "^\\+27[6-8][0-9]{8}$"; //Cellphone number Regex
        
        //Coding with Thabang, 2024. Java program to validate South African phone numbers using RegeX. Available at: https://www.youtube.com/watch?v=2M1CpEJZ6rk [Accessed 17 September 2026]
        
        if(CellphoneNum.matches(CellphoneRegex)){
            outcome = "Cellphone number successfully captured";
            CheckCellphoneNum = true;
            
                    
        }else{
            outcome = "Cellphone number is incorrectly formatted or does not contain international code";
            CheckCellphoneNum = false;
            
        }
        return CheckCellphoneNum;
    }
    public String RegisterUser(String Username, String Password){
        String outcome;
        if(!CheckUsername(Username)){
            outcome = "The Username is incorrectly formatted";
                       
        }else if(!CheckPasswordComplexity(Password)){
            outcome = "The password does not meet the complexity requirements";
            
            
        }else{
            outcome = "The two above conditions have been met and the user has been registered successfully";
        }
        System.out.println(outcome); // this will output all the outcome messages for each if statement
        return outcome;
        
    }
    public boolean LoginUser(String enteredUsername, String enteredPassword, String Username, String Password){
        boolean login;
        if(enteredPassword.equals(Password) && enteredUsername.equals(Username)) {
           login = true;
                                                                          
        }else{
            login = false;
        }
        System.out.println(login);
        return login;
        
    }
    public String returnLoginStatus(String enteredUsername, String enteredPassword, String Username, String Password){
        String status;
        if(!LoginUser(enteredUsername, enteredPassword, Username, Password)){
            status = "A failed login";
        }else{
            status = "A successful login";
           
        }
        System.out.println(status);
        return status;
    }
}
