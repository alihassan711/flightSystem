package com.flightschedule;

import com.flightschedule.controller.HomeController;
import com.flightschedule.controller.MenuController;
import com.flightschedule.service.AdminMenuService;
import com.flightschedule.service.HomeService;
import com.flightschedule.utils.CommonUtils;
import com.flightschedule.utils.Constants;
import com.flightschedule.utils.DatabaseHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;
import com.common.beans.UserBean;
import com.common.beans.MenuBean;



public class Main {
    private static Logger LGR = LogManager.getLogger(Main.class);
    private static  Map<MenuBean,Runnable> UserMenuMap;
    private static  Map<MenuBean,Runnable> homeMenuMap;
    private static  Map<MenuBean,Runnable> adminMenuMap;
    private static UserBean user;

    public static void main(String[] args)  {

        Connection conn = null;
        LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        try{
            System.out.println("Welcome to flight scheduling system");
            handleLogin();
            handleFlightScheduling();
            System.out.println("Flight scheduling system closed");
        }
        catch(Exception ex){
            LGR.info(LGR.isInfoEnabled() ? "Exception Occurred: " : null);
        } finally {
            DatabaseHandler.cleanUp(conn, null, null);
        }
    }

    private static void handleLogin() {
        String userInput;
        boolean isLogin = false;

        UserBean loggedInUser = new UserBean();
        HomeController homeController = new HomeController();
        setHomeMenuMap(homeController.getHomeMenuMap());
        CommonUtils.displayMenu(getHomeMenuMap());

        do {
        Scanner scanner = new Scanner(System.in);
            userInput = scanner.nextLine();
            String finalUserInput = userInput;
            LGR.info(LGR.isInfoEnabled() ? "Handling Logging : " : null);
            for(Map.Entry<MenuBean,Runnable> mapEntry : homeMenuMap.entrySet()) {
                if (mapEntry.getKey().getMenuOrder().equals(finalUserInput)) {
                    LGR.info(LGR.isInfoEnabled() ? "Routing to mapped function" : null);
                    ((HomeService) mapEntry.getValue()).setAction(finalUserInput);
                    ((HomeService) mapEntry.getValue()).setUser(loggedInUser);
                    mapEntry.getValue().run();
                    System.out.println(loggedInUser.getUserId());
                    if(loggedInUser.getUserId()!= null){
                        isLogin = true;
                    }
                }
            }
        } while (!isLogin);
        if(isLogin){
            setUser(loggedInUser);
        }
        //scanner.close();
    }

    private static void handleFlightScheduling() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean isLogin = false;

        UserBean loggedInUser = getUser();
        MenuController menuController = new MenuController();
        setAdminMenuMap(menuController.getAdminMenuMap());
        CommonUtils.displayMenu(getAdminMenuMap());

        if(getUser().getUserId() != null){
            do {
                userInput = scanner.nextLine();
                String finalUserInput = userInput;

                for(Map.Entry<MenuBean,Runnable> mapEntry : adminMenuMap.entrySet()) {
                    if (mapEntry.getKey().getMenuOrder().equals(finalUserInput)) {
                        LGR.info(LGR.isInfoEnabled() ? "Routing to mapped function" : null);
                        ((AdminMenuService) mapEntry.getValue()).setAction(finalUserInput);
                        ((AdminMenuService) mapEntry.getValue()).setAdminMenuMap(getAdminMenuMap());
                        ((AdminMenuService) mapEntry.getValue()).setUser(loggedInUser);
                        mapEntry.getValue().run();
                    }
                }
            } while (!isLogin);
        }
        System.out.println("Logged Out");
        LGR.info(LGR.isInfoEnabled() ? "User Logged Out" : null);
        scanner.close();
    }

    public static Map<MenuBean, Runnable> getUserMenuMap() {
        return UserMenuMap;
    }

    public static void setUserMenuMap(Map<MenuBean, Runnable> userMenuMap) {
        UserMenuMap = userMenuMap;
    }

    public static Map<MenuBean, Runnable> getHomeMenuMap() {
        return homeMenuMap;
    }

    public static void setHomeMenuMap(Map<MenuBean, Runnable> homeMenuMap) {
        Main.homeMenuMap = homeMenuMap;
    }

    public static Map<MenuBean, Runnable> getAdminMenuMap() {
        return adminMenuMap;
    }

    public static void setAdminMenuMap(Map<MenuBean, Runnable> adminMenuMap) {
        Main.adminMenuMap = adminMenuMap;
    }

    public static void setUser(UserBean user) {
        Main.user = user;
    }

    public static UserBean getUser() {
        return user;
    }
}