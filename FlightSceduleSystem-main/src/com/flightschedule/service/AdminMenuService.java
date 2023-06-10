package com.flightschedule.service;

import com.common.beans.FlightBean;
import com.common.beans.StatusCodes;
import com.flightschedule.utils.CommonUtils;
import com.flightschedule.utils.Constants;
import com.flightschedule.utils.DatabaseHandler;
import com.flightservice.bean.FlightServiceRequestObject;
import com.flightservice.bean.FlightServiceResponseObject;
import com.flightservice.controller.FlightServiceController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.common.beans.MenuBean;
import com.common.beans.UserBean;

import java.util.Scanner;

public class AdminMenuService implements Runnable {
    private static Logger LGR = LogManager.getLogger(AdminMenuService.class);

    private String action;
    public UserBean user;
    private Map<MenuBean, Runnable> adminMenuMap;

    @Override
    public void run() {

        if(!getUser().getUserId().isEmpty()  && getUser().getUserId() != null){
            LGR.info(LGR.isInfoEnabled() ? "Inside run() of AdminMenuSevice" : null);
            boolean isDeleted = false;
            boolean isValidFlight = false;
            List<FlightBean> flightBeanList = new ArrayList<>();
            if (action.equals(Constants.ONE_VALUE)) {
                LGR.info(LGR.isInfoEnabled() ? "View Flights called" : null);
                flightBeanList = viewFlightDetails();
                if (flightBeanList.size() > 0) {
                    displayFlightDetails(flightBeanList);
                } else {
                    System.out.println("No Flights Found");
                }
            }
            if (action.equals(Constants.TWO_VALUE)) {
                LGR.info(LGR.isInfoEnabled() ? "Add Flights called" : null);
                addNewFlight();
            }
            if (action.equals(Constants.THREE_VALUE)) {
                LGR.info(LGR.isInfoEnabled() ? "Delete Flights called" : null);
                do {
                    System.out.println("Enter the 'Flight Number' from below list of flight you want to delete: ");
                    System.out.println("Press 'X' to exit: ");
                    displayFlightDetails(viewFlightDetails());
                    Scanner scanner = new Scanner(System.in);
                    String userInput = scanner.nextLine();
                    if(userInput.equals(Constants.EXIT)){
                        break;
                    }
                    isValidFlight = deleteFlight(userInput);
                } while (!isValidFlight);

            }
            if (action.equals(Constants.FOUR_VALUE)) {
                LGR.info(LGR.isInfoEnabled() ? "Exit Flights called" : null);
                System.out.println("Exiting...");
                getUser().setUserId(null);
            }
            if(getUser().getUserId()!= null){
                CommonUtils.displayMenu(getAdminMenuMap());
            }
        }

    }

    private boolean deleteFlight(String flight) {
        LGR.info(LGR.isInfoEnabled() ? "inside deleteFlight()" : null);

        FlightServiceResponseObject responseObject = new FlightServiceResponseObject();
        FlightServiceController controller;
        FlightServiceRequestObject requestObject;
        FlightBean flightBean = new FlightBean();


        try {
            controller = new FlightServiceController();
            requestObject = new FlightServiceRequestObject();
            Connection connection = DatabaseHandler.getConnection();

            flightBean.setFlightNumber(flight);
            requestObject.setFlightBean(flightBean);
            responseObject = controller.deleteFlight(requestObject, connection);
            if (responseObject.getResponseCode()!= null && responseObject.getResponseCode().equals(StatusCodes.STATUS_CODE_SUCCESS)) {
                System.out.println(responseObject.getTotalCount() + " Flight Deleted");
                return true;
            } else if (responseObject.getResponseCode()!= null && responseObject.getResponseCode().equals(StatusCodes.STATUS_CODE_DO_NOT_EXIST)) {
                System.out.println("Record do not exist, Please try again");
            } else {
                System.out.println("Unable to delete the record, Please try again");
            }
        } catch (Exception ex) {
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        }
        return false;
    }

    private void addNewFlight() {
        LGR.info(LGR.isInfoEnabled() ? "Inside addNewFlight()" : null);

        FlightServiceResponseObject responseObject;
        FlightServiceController controller;
        FlightServiceRequestObject requestObject;
        FlightBean flightBean = new FlightBean();
        try{
            controller = new FlightServiceController();
            requestObject = new FlightServiceRequestObject();
            responseObject = new FlightServiceResponseObject();
            Connection connection = DatabaseHandler.getConnection();

            flightBean = handleInputForFlight();
            System.out.println("Adding flight to the system.");
            requestObject.setFlightBean(flightBean);
            responseObject = controller.addFlight(requestObject, connection);
            if (!CommonUtils.isNull(responseObject) && responseObject.getResponseCode().equals(StatusCodes.STATUS_CODE_SUCCESS)) {
                System.out.println("Flight Added Sucessfully. ");
            } else if(!CommonUtils.isNull(responseObject) && responseObject.getResponseCode().equals(StatusCodes.STATUS_CODE_FAILURE)){
                System.out.println("Some error has occured. Please try again. ");
            }


        } catch (Exception ex) {
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        }
    }

    private List<FlightBean> viewFlightDetails() {
        LGR.info(LGR.isInfoEnabled() ? "inside viewFlightDetails()" : null);

        FlightServiceResponseObject responseObject;
        FlightServiceController controller;
        FlightServiceRequestObject requestObject;
        List<FlightBean> flightBeanList = new ArrayList<>();

        try {
            controller = new FlightServiceController();
            requestObject = new FlightServiceRequestObject();
            Connection connection = DatabaseHandler.getConnection();

            responseObject = controller.fetchFlightsList(requestObject, connection);
            if (!CommonUtils.isNull(responseObject) && responseObject.getResponseCode().equals(StatusCodes.STATUS_CODE_SUCCESS)) {
                flightBeanList = (List<FlightBean>) responseObject.getResponseObj();
            }
        } catch (Exception ex) {
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        }
        return flightBeanList;
    }

    private void displayFlightDetails(List<FlightBean> flightBeanList) {

        LGR.info(LGR.isInfoEnabled() ? "Printing details" : null);
        int displayCount = 1;
        System.out.println("Flight Details");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|     Sr no.         |   Flight Number  |              Departure              |                 Arrival               |          Origin          |       Destination          |");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");

        for (FlightBean flight : flightBeanList) {
            System.out.printf("|     %-13s |     %-13s |        %-30s |      %-30s |         %-13s |         %-13s |\n",
                    displayCount++,
                    flight.getFlightNumber(), flight.getDepartureTime(),
                    flight.getArrivalTime(), flight.getOriginAirport(),
                    flight.getDestinationAirport());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    private boolean isValidFlightNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.isEmpty()) {
            System.out.println("Flight number is required");
            return false;
        }
        return true;
    }

    private boolean isValidFlightTime(String time) {
        if (time == null || time.isEmpty()) {
            System.out.println("Time is required");
            return false;
        }
        return true;
    }

    private boolean isValidAirport(String airport) {
        if (airport == null || airport.isEmpty()) {
            System.out.println("Airport is required");
            return false;
        }
        return true;
    }

    private boolean isValidMaxCapacity(String maxCapacity) {
        try {
            int capacity = Integer.parseInt(maxCapacity);
            if (capacity <= 0) {
                System.out.println("Maximum capacity should be a positive value");
                return false;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Maximum capacity should be a numeric value");
            return false;
        }

        return true;
    }

    private boolean isValidFlight(FlightBean flight) {
        return isValidFlightNumber(flight.getFlightNumber()) &&
                isValidFlightTime(flight.getDepartureTime()) &&
                isValidFlightTime(flight.getArrivalTime()) &&
                isValidAirport(flight.getOriginAirport()) &&
                isValidAirport(flight.getDestinationAirport()) &&
                isValidMaxCapacity(flight.getMaxCapacity());
    }


    private FlightBean handleInputForFlight(){
        FlightBean flightBean = new FlightBean();
        String userInput = new String();
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        do{
            System.out.println("Enter the 'Flight Number': ");
            userInput = scanner.nextLine();
            if(isValidFlightNumber(userInput)){
                flightBean.setFlightNumber(userInput);
                break;
            }
            else{
                System.out.println("Enter valid flight Number in given format: ");
            }
        }while(!flag);
        flag = false;
        do{
            System.out.println("Enter the 'Departure Time(YYYY-MM-DD, HH:MM:SS) ': ");
            userInput = scanner.nextLine();
            if(isValidFlightTime(userInput)){
                flightBean.setDepartureTime(userInput);
                break;
            }
            else{
                System.out.println("Enter valid Departure Time in given format: ");
            }
        }while(!flag);

        flag = false;
        do{
            System.out.println("Enter the 'Arrival Time(YYYY-MM-DD, HH:MM:SS) ': ");
            userInput = scanner.nextLine();
            if(isValidFlightTime(userInput)){
                flightBean.setArrivalTime(userInput);
                break;
            }
            else{
                System.out.println("Enter valid Arrival Time in given format: ");
            }
        }while(!flag);

        flag = false;
        do{
            System.out.println("Enter the 'Destination Airport ': ");
            userInput = scanner.nextLine();
            if(isValidAirport(userInput)){
                flightBean.setDestinationAirport(userInput);
                break;
            }else{
                System.out.println("Enter valid Destination Airport in given format: ");
            }
        }while(!isValidAirport(userInput));
        flag = false;
        do{
            System.out.println("Enter the 'Origin Airport ': ");
            userInput = scanner.nextLine();
            if(isValidAirport(userInput)){
                flightBean.setOriginAirport(userInput);
                break;
            } else{
                System.out.println("Enter valid Origin Airport in given format: ");
            }
        }while(!flag);
        flag = false;

        do{
            System.out.println("Enter the 'Maximum Capacity': ");
            userInput = scanner.nextLine();
            if(isValidMaxCapacity(userInput)){
                flightBean.setMaxCapacity(userInput);
                break;
            } else{
                System.out.println("Enter valid Maximum Capacity in given format: ");
            }
        }while(!flag);

        return flightBean;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<MenuBean, Runnable> getAdminMenuMap() {
        return adminMenuMap;
    }

    public void setAdminMenuMap(Map<MenuBean, Runnable> adminMenuMap) {
        this.adminMenuMap = adminMenuMap;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}

