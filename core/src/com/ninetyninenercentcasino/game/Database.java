package com.ninetyninenercentcasino.game;
import java.sql.*;

/*
 * Class: Database
 * Purpose: to handle database operations. Database stores user account information.
 */
public class Database {
    private Connection database; //This is our database connection

    /*
     * Name: Database
     * Purpose: Constructor. Initializes the database connection.
     * Precondition: None
     * Postcondition: Connection to the database established
    */
    public Database() throws SQLException {
        database=DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "password");
    }
    public Account loadUser(String userName, String password) throws SQLException {
        PreparedStatement loadUser=database.prepareStatement("select * from users");
        ResultSet result=loadUser.executeQuery();
        result.next();
        Account user=new Account(result.getString("Username"),result.getString("Salt"),result.getString("Hash"));
        return user;
    }
}