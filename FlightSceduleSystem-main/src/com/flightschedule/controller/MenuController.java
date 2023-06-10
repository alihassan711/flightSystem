package com.flightschedule.controller;

import com.flightschedule.utils.Constants;
import com.common.beans.MenuBean;

import java.util.Map;

import static com.flightschedule.utils.CommonUtils.initializeMenu;

public class MenuController {

    private Map<MenuBean,Runnable> adminMenuMap;
    public MenuController(){
        this.adminMenuMap = initializeMenu(Constants.ADMIN);
    }

    public Map<MenuBean, Runnable> getAdminMenuMap() {
        return adminMenuMap;
    }

    public void setAdminMenuMap(Map<MenuBean, Runnable> adminMenuMap) {
        this.adminMenuMap = adminMenuMap;
    }
}
