package currentmood.util;
import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;

public class CMTwitter {

	protected final String TWITTER_CONSUMER_KEY = "",
						   TWITTER_CONSUMER_SECRET = "";
	protected boolean authentified;
	
	protected Twitter twitterClient;
	ConfigurationBuilder twitterConfiguration;
	
	public CMTwitter()
	{
		this.authentified = false;
	}
	
	public void setProxy(Proxy proxy)
	{
		twitterConfiguration.setHttpProxyHost(proxy.getHost());
		twitterConfiguration.setHttpProxyPort(proxy.getPort());
		twitterConfiguration.setHttpProxyUser(proxy.getUser());
		twitterConfiguration.setHttpProxyPassword(proxy.getPassword());
	}
	
	public void connect()
	{
		
	}

}
