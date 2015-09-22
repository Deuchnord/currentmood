package currentmood.util;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class CMTwitter {

	protected final String TWITTER_PUBLIC_KEY = "",
						   TWITTER_PRIVATE_KEY = "";
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

}
