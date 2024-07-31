package com.ninetyninepercentcasino;

import com.ninetyninepercentcasino.net.BJServer;

import java.util.Properties;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class to launch the server
 */
public class ServerLauncher {
	public static void main(String[] args) throws IOException {
		String DBAddr, DBUser, DBPassword;
		int port = -1;
		Properties props = new Properties();

		try {
			props.load(new FileInputStream("server.properties"));
			DBAddr = props.getProperty("DBAddr");
			DBUser = props.getProperty("DBUser");
			DBPassword = props.getProperty("DBPassword");
			if (props.getProperty("sampleConfig") != null) {
				throw new RuntimeException("Sample config detected, please remove line from configuration.");
			}
			if (DBAddr == null || DBUser == null || DBPassword == null) {
				throw new RuntimeException("Configuration missing required fields.");
			}
			if (props.getProperty("port") == null) {
				BJServer server = new BJServer(DBAddr, DBUser, DBPassword);
				server.start();
			} else {
				try {
					port = Integer.parseInt(props.getProperty("port"));
					if (port < 0 || port > 65535) {
						throw new RuntimeException(String.format("Invalid port number: %d.", port));
					}
					BJServer server = new BJServer(DBAddr, DBUser, DBPassword, port);
					server.start();
				} catch (NumberFormatException e) {
					throw new RuntimeException(String.format("Invalid port number: %s.", props.getProperty("port")));
				}
			}
		} catch (FileNotFoundException e) {
			File configFile = new File("server.properties");
			configFile.createNewFile();
			FileWriter config = new FileWriter("server.properties");
			config.write("sampleConfig=removeme\nDBAddr=mysql://127.0.0.1:3306/ninetyNinePercent\nDBUser=ninetyNineMan\nDBPassword=amogus69420");
			config.close();
			throw new RuntimeException("Configuration file is missing, sample configuration created.");
		}
	}
}
