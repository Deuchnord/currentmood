package currentmood.util;

public class Proxy {

	protected String host, user, password;
	protected int port;
	protected boolean hasProxy;

	public Proxy()
	{
		hasProxy = false;
	}
	
	public Proxy(String host, int port, String user, String password)
	{
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		
		hasProxy = true;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
	public void setProxy(String host, int port)
	{
		this.host = host;
		this.port = port;
		
		hasProxy = true;
	}
	
	public void setProxy(String host, int port, String user, String password)
	{
		setProxy(host, port);
		this.user = user;
		this.password = password;
	}
	
	public boolean hasProxy()
	{
		return hasProxy;
	}
	
}
