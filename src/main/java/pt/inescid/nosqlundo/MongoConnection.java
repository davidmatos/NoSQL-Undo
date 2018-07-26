package pt.inescid.nosqlundo;

import java.io.Serializable;
import java.util.Date;

public class MongoConnection implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String connectionName;
	private String serverAddress;
	private int serverPort;
	private String username;
	private String password;
	private String timestamp;
	
	public MongoConnection(String connectionName, String serverAddress, int port, String username, String password) {
		super();
		this.connectionName = connectionName;
		this.serverAddress = serverAddress;
		this.serverPort = port;
		this.username = username;
		this.password = password;
		this.timestamp = new Date().toString();
	}
	public String getConnectionName() {
		return connectionName;
	}
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
	
	
	

}
