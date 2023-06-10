package com.flightschedule.utils;

import com.flightschedule.Main;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class DatabaseHandler {
    private static final Logger LGR = Logger.getLogger(DatabaseHandler.class);

    public static Connection getConnection() throws SQLException {

        Connection conn;
        try {
            Properties prop = getPropValues();
            conn = DriverManager.getConnection(prop.getProperty("DB_CONN_STRING"), prop.getProperty("DB_USER"), prop.getProperty("DB_PASSWORD"));
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);

        } catch (Exception ex) {
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
            throw new SQLException("#Exception# onnection cannot be created for Instance:" + ex);
        }
        return conn;
    }

    public static void cleanUp(Connection conn, Statement stmt, ResultSet rst) {
        LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        if (rst != null) try {
            rst.close();
        } catch (SQLException var6) {
            LGR.error("#SQLException# Exception in Closing ResultSet", var6);
        }

        if (stmt != null) try {
            stmt.close();
        } catch (SQLException var5) {
            LGR.error("#SQLException# Exception in Closing Statement", var5);
        }

        if (conn != null) try {
            returnConnection(conn);
        } catch (SQLException var4) {
            LGR.error("#SQLException# in Closing Connection", var4);
        }

    }

    private static void returnConnection(Connection conn) throws SQLException {
        try {
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            conn.close();
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        } catch (Exception var3) {
            throw new SQLException("#Exception# onnection cannot be returned:" + var3);
        }
    }


    private static Properties getPropValues() throws IOException {
        String user = new String();
        InputStream inputStream = null;
        Properties prop = new Properties();
        try {
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
            String propFileName = "resources/dbconfig.properties";
            inputStream = Main.class.getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }}
        catch (Exception e) {
            LGR.info(LGR.isInfoEnabled() ? "Execution started" : null);
        } finally {
            inputStream.close();
        }
        return prop;
    }

}

