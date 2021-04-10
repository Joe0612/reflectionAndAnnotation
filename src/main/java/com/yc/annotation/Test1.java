package com.yc.annotation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @program: reflectionAndAnnotation
 * @description:
 * @author: Joe
 * @create: 2021-03-30 18:48
 */
@DBConnection(url="jdbc:mysql://localhost:3306/test",driverClass = "com.mysql.jdbc.Driver")
public class Test1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class c=Test1.class;

        DBConnection dbc= (DBConnection) c.getDeclaredAnnotation(DBConnection.class);

        String driverClass=dbc.driverClass();
        String url=dbc.url();
        String user=dbc.user();
        String password=dbc.password();

        Class.forName(driverClass);
        Connection con= DriverManager.getConnection(url,user,password);
        System.out.println(con);
    }

}
