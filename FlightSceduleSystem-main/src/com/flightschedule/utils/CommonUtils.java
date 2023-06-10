package com.flightschedule.utils;

import com.flightschedule.Main;
import com.flightschedule.service.HomeService;
import com.flightschedule.service.AdminMenuService;
import com.flightschedule.service.UserService;
import com.common.beans.MenuBean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CommonUtils {
    private static Logger LGR = LogManager.getLogger(Main.class);
    private static List<com.common.beans.MenuBean> fetchMenus(String menuType){
        Connection con = null;
        PreparedStatement pStm = null;
        ResultSet rs = null;
        int counter = 1;
        com.common.beans.MenuBean menu = null;
        List<com.common.beans.MenuBean> menuList = null;
        try{
            menuList = new ArrayList<>();
            con = DatabaseHandler.getConnection();
            pStm = con.prepareStatement(Constants.FETCH_MENU);
            if(pStm != null){
                pStm.setString(counter++, menuType);
                rs = pStm.executeQuery();
                while (rs.next()){
                    menu = new MenuBean();
                    menu.setMenuId(rs.getString("menu_id"));
                    menu.setMenuName(rs.getString("menu_name"));
                    menu.setMenuOrder(rs.getString("menu_order"));
                    menu.setMenuType(menuType);
                    menuList.add(menu);
                }
            }
        } catch (SQLException ex){
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        }
        finally {
            DatabaseHandler.cleanUp(con, pStm, rs);
        }
        return menuList;
    }

    public static Map<MenuBean,Runnable> initializeMenu(String menuType){
        Map<MenuBean,Runnable> menuMap = new HashMap<>();

        UserService userService = new UserService();
        HomeService homeService = new HomeService();
        AdminMenuService menuService = new AdminMenuService();

        List<MenuBean> menuList = fetchMenus(menuType);

        for (MenuBean menu : menuList) {
            if(menu.getMenuType().equals(Constants.HOME)){
                menu.setAction(homeService);
            }
            if(menu.getMenuType().equals(Constants.ADMIN)){
                menu.setAction(menuService);
            }
            if(menu.getMenuType().equals(Constants.CUSTOMER)){
                menu.setAction(userService);
            }
            menuMap.put(menu, menu.getAction());
        }
        return menuMap;
    }

    public static void displayMenu(Map<MenuBean,Runnable> menuMap) {

        System.out.println("Enter the number from below menu of your choice:");
        TreeMap<MenuBean, Runnable> sortedMenuMap = new TreeMap<>(Comparator.comparing(MenuBean::getMenuOrder));

        sortedMenuMap.putAll(menuMap);

        for (Map.Entry<MenuBean, Runnable> menuEntry : sortedMenuMap.entrySet()) {
            System.out.println(menuEntry.getKey().getMenuOrder() + " : " + menuEntry.getKey().getMenuName());
        }
    }

    public static boolean isNullOrEmptyString(String string) {
        return string == null || "".equals(string) || "null".equals(string);
    }

    public static boolean isNull(Object object) {
        if (null == object) {
            return true;
        }

        return false;
    }
}
