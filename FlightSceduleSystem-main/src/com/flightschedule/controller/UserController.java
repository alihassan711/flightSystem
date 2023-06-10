package com.flightschedule.controller;

import com.flightschedule.utils.CommonUtils;
import com.flightschedule.utils.Constants;

import java.util.Map;
import java.util.Scanner;
import com.common.beans.UserBean;

public class UserController {

    private UserBean user;
    private Map<com.common.beans.MenuBean,Runnable> menuMap;
    public UserController(UserBean userBean){
        this.menuMap = CommonUtils.initializeMenu(Constants.CUSTOMER);
    }
    public void loggedIn(UserBean user) {

    }

    public boolean isValidUser(UserBean userBean){
        return true;
    }

    public UserBean LogUserIn(UserBean userBean){
        System.out.println("Enter User Id");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        userBean.setUserId(userInput);
        System.out.println("Enter User Password");
        userInput = scanner.nextLine();
        userBean.setPassword(userInput);

        userBean.setRoleId(Constants.ADMIN);
        System.out.println("Login Successful");
        return userBean;
    }

}
