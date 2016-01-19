package pt.ist.mongoundo.db;

import pt.ist.mongoundo.MongoConnection;

public class DBUtils {

	public static String getConnectionString(String serverAddress, int port, String databaseName, String username, String password){
		return "mongodb://"+username+":"+password+"@"+serverAddress+":"+port+"/"+databaseName;
	}
	
	
	public static String getConnectionString(MongoConnection mongoConnection){
		return "mongodb://"+mongoConnection.getUsername()+":"+mongoConnection.getPassword()+"@"+mongoConnection.getServerAddress()+":"+mongoConnection.getServerPort();
	}
}
