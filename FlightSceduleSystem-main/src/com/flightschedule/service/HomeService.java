package com.flightschedule.service;
import com.common.beans.UserBean;
import com.flightschedule.utils.Constants;

import java.util.Scanner;

public class HomeService implements Runnable {

    private UserBean user;
    private String action;

    public HomeService() {

    }
    @Override
    public void run() {
        if(action.equals(Constants.ONE_VALUE)){
            signUpUser();
        }
        if(action.equals(Constants.TWO_VALUE)){
            signInUser();
        }
    }

    private void signUpUser() {
            System.out.println("signUpUser");
            return;
    }

    private void signInUser() {
        System.out.println("signInUser");
        String userInput = null;
        Scanner scanner = new Scanner(System.in);

        boolean validInupt = false;
        do {
            System.out.println("Enter User Id:");
            userInput = scanner.nextLine();
            if(userInput.equals("admin")) {
                validInupt = true;
                user.setUserId(userInput);
            }
        } while (!validInupt);
        validInupt = false;
        do {
            System.out.println("Enter User Password:");
            userInput = scanner.nextLine();
            if(userInput.equals("admin")) {
                validInupt = true;
                user.setUserId(userInput);
            }
        } while (!validInupt);
        System.out.println("Login successfully");

    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
