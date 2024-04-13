package com.ninetyninenercentcasino.game;
import java.sql.*;
import java.util.Arrays;
import java.security.*;
import java.nio.charset.StandardCharsets;

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
        database=DriverManager.getConnection("jdbc:mysql://localhost:3306/ninetyNinePercent", "root", "password");
    }
    /*
     * Name: loadUser
     * Purpose: Takes a username and password. Checks if the username coresponds to a real account. Checks if the password supplied is the correct password for that account. If the account exists and the password is correct, it returns a Account object.
     * Precondition: None
     * Postcondition: None
     */
    public Account loadUser(String username, String password) throws SQLException, AccountNonExistent, PasswordIncorrect {
        PreparedStatement loadUser=database.prepareStatement("select 'Username' from Accounts");
        ResultSet result=loadUser.executeQuery();
        boolean foundUser=false;
        while(result.next()) { //Next returns false if there is not another value.
            if(result.getString("Username").equals(username)) {
                foundUser=true;
                break;
            }
        }
        if(foundUser==false) {
            throw new AccountNonExistent();
        } else {
            if(Arrays.equals(result.getBytes("Hash"), hash(password, result.getBytes("Salt")))) {
                Account user=new Account(result.getString("Username"));
                return user;
            } else {
                throw new PasswordIncorrect();
            }
        }
    }
    /*
     * Name: hash
     * Purpose: Computes the hash. For use in verify user passwords. Hash algorithim is SHA-256.
     * Preconditon: None
     * Postcondition: The SHA-256 hash of the password with the salt appended to the end is returned as a byte array.
     */
    private byte[] hash(String password, byte[] salt) {
        byte[] hashedValue=null;
        try {
            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            hashedValue=digest.digest(ByteArray.merge(password.getBytes(StandardCharsets.UTF_8), salt));
        } catch(Exception e) {
            System.out.println(e);
        }
        return hashedValue;
    }
}