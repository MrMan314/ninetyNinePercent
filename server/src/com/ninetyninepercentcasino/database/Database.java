package com.ninetyninepercentcasino.database;
import java.sql.*;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Class: Database
 * Purpose: to handle database operations. Database stores user account information.
 */
public class Database {
    private Connection database; //This is our database connection

    /**
     * Name: Database
     * Description: Constructor. Initializes the database connection.
     * Precondition: None
     * Postcondition: Connection to the database established
    */
    public Database() throws SQLException {
        database=DriverManager.getConnection("jdbc:mysql://localhost:3306/ninetyNinePercent", "root", "password");
    }
    /**
     * Name: loadUser
     * Description: Takes a username and password. Checks if the username coresponds to a real account. Checks if the password supplied is the correct password for that account. If the account exists and the password is correct, it returns a Account object.
     * Precondition: None
     * Postcondition: None
     */
    public Account loadUser(String username, String password) throws SQLException, AccountNonExistent, PasswordIncorrect {
        PreparedStatement loadUser=database.prepareStatement("select * from Accounts");
        ResultSet result=loadUser.executeQuery();
        if(userExists(result, username)) { //User does exist
            if(result.getString("Hash").equals(Base64Utils.byteArrayTobase64(hash(password, Base64Utils.base64ToByteArray(result.getString("Salt")))))) { //Compares the password provided to the password in the database
                Account user=new Account(result.getString("Username"));
                return user;
            } else {
                throw new PasswordIncorrect(); //Password wrong
            }
        } else { //User does not exist
            throw new AccountNonExistent(); //Account does not exist
        }
    }
    /**
     * Name: createUser
     * Description: Takes username and password. Checks if username already exists. If it does, UserAlreadyExists exception thrown. If not, user is added to database.
     * Precondition: None
     * Postcondition: Account added to database or UserAlreadyExists exception thrown.
     */
    public void createUser(String username, String password) throws SQLException, UserAlreadyExists {
        PreparedStatement loadUser=database.prepareStatement("select Username from Accounts");
        ResultSet result=loadUser.executeQuery();
        if(!userExists(result, username)) { //User doesn't exist
            SecureRandom secureRandom=new SecureRandom();
            byte[] salt=new byte[32];
            secureRandom.nextBytes(salt);
            PreparedStatement createUser=database.prepareStatement("insert into Accounts (Username, Hash, Salt) values ('"+username+"','"+Base64Utils.byteArrayTobase64(hash(password, salt))+"','"+Base64Utils.byteArrayTobase64(salt)+"');");
            createUser.executeUpdate();
        } else { //User already exists
            throw new UserAlreadyExists();
        }
    }
    /**
     * Name: hash
     * Description: Computes the hash. For use in verify user passwords. Hash algorithim is SHA-256.
     * Preconditon: None
     * Postcondition: The SHA-256 hash of the password with the salt appended to the end is returned as a byte array.
     */
    private byte[] hash(String password, byte[] salt) {
        byte[] hashedValue=null;
        try {
            MessageDigest digest=MessageDigest.getInstance("SHA-256"); //SHA-256
            hashedValue=digest.digest(ByteArray.merge(password.getBytes(StandardCharsets.UTF_8), salt)); //Hashes the password+salt
        } catch(Exception e) {
            System.out.println(e);
        }
        return hashedValue;
    }
    /**
     * Name: userExists
     * Description: Checks if the username exists in the database.
     * Precondition: None
     * Postcondition: True returned if username is in database, false if not.
     */
    private boolean userExists(ResultSet result, String username) throws SQLException {
        while(result.next()) { //Next returns false if there is not another value.
            if(result.getString("Username").equals(username)) {
                return true; //Username exists
            }
        }
        return false; //Username does not exist
    }
}