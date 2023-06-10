package com.flightschedule.controller;

import com.common.beans.MenuBean;
import com.flightschedule.utils.CommonUtils;
import com.flightschedule.utils.Constants;

import java.util.Map;

public class HomeController {

    private Map<MenuBean,Runnable> homeMenuMap;
    public HomeController(){
        this.homeMenuMap = CommonUtils.initializeMenu(Constants.HOME);
    }


    public Map<MenuBean, Runnable> getHomeMenuMap() {
        return homeMenuMap;
    }

    public void setHomeMenuMap(Map<MenuBean, Runnable> homeMenuMap) {
        this.homeMenuMap = homeMenuMap;
    }
}
