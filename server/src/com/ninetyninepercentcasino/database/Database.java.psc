package com.ninetyninepercentcasino.database
public class Database:
	private Connection database
	public Database(String DBAddr, String DBUser, String DBPassword):
		database=DriverManager.getConnection("jdbc:" + DBAddr, DBUser, DBPassword)
	public Account loadUser(String username, String password):
		PreparedStatement loadUser=database.prepareStatement("SELECT * FROM Accounts")
		ResultSet result=loadUser.executeQuery()
		if userExists(result, username):
			if result.getString("Hash").equals(Base64Utils.byteArrayTobase64(hash(password, Base64Utils.base64ToByteArray(result.getString("Salt"))))):
				Account user=new Account(result.getString("Username"))
				return user
			else:
				throw new PasswordIncorrect()
		else:
			throw new AccountNonExistent()
	public Account createUser(String username, String password):
		PreparedStatement loadUser=database.prepareStatement("SELECT Username FROM Accounts")
		ResultSet result=loadUser.executeQuery()
		if !userExists(result, username):
			SecureRandom secureRandom=new SecureRandom()
			byte[] salt=new byte[32]
			secureRandom.nextBytes(salt)
			PreparedStatement createUser=database.prepareStatement("INSERT INTO Accounts (Username, Hash, Salt) VALUES ('"+username+"','"+Base64Utils.byteArrayTobase64(hash(password, salt))+"','"+Base64Utils.byteArrayTobase64(salt)+"');")
			createUser.executeUpdate()
			return loadUser(username, password)
		else:
			throw new UserAlreadyExists()
	private byte[] hash(String password, byte[] salt):
		byte[] hashedValue=null
		try:
			MessageDigest digest=MessageDigest.getInstance("SHA-256")
			hashedValue=digest.digest(ByteArray.merge(password.getBytes(StandardCharsets.UTF_8), salt))
		catch(Exception e):
			println(e)
		return hashedValue
	private boolean userExists(ResultSet result, String username):
		while result.next():
			if result.getString("Username").equals(username):
				return true
		return false
