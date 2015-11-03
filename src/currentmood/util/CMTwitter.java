package currentmood.util;

import java.util.List;
import twitter4j.Query;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class is an interface between Twitter4J and #Currentmood.
 * It provides methods to make it easier to communicate with Twitter.
 */
public class CMTwitter {

	protected boolean authentified;
	
	protected Twitter twitterClient;
	protected ConfigurationBuilder twitterConfiguration;
	protected Proxy proxy;
	
	protected String token, tokenSecret;
	
	public CMTwitter()
	{
		proxy = null;
		reset();
	}
	
	/**
	 * Regenerates the object before making a new query.
	 * This method is called automatically.
	 */
	protected void reset()
	{
		this.authentified = false;
		
		twitterConfiguration = new ConfigurationBuilder();
		
		twitterConfiguration.setOAuthConsumerKey(TwitterConfidentialInfo.TWITTER_CONSUMER_KEY);
		twitterConfiguration.setOAuthConsumerSecret(TwitterConfidentialInfo.TWITTER_CONSUMER_SECRET);
		twitterConfiguration.setOAuthAccessToken(TwitterConfidentialInfo.TWITTER_ACCESS_TOKEN);
		twitterConfiguration.setOAuthAccessTokenSecret(TwitterConfidentialInfo.TWITTER_ACCESS_TOKEN_SECRET);
		
		if(proxy != null)
			setProxy(proxy);
	}
	
	/**
	 * Gives the proxy settings to Twitter4J.
	 * @param proxy a <code>Proxy</code> object containing the setting.
	 */
	public void setProxy(Proxy proxy)
	{
		if(authentified)
			reset();
		
		twitterConfiguration.setHttpProxyHost(proxy.getHost());
		twitterConfiguration.setHttpProxyPort(proxy.getPort());
		
		this.proxy = proxy;
	}
	
	/**
	 * Connects to Twitter. This must be done before each query.
	 */
	public void connect()
	{
		if(authentified)
			reset();
		
		TwitterFactory twitterFactory = new TwitterFactory(twitterConfiguration.build());
		twitterClient = twitterFactory.getInstance();
		
		authentified = true;
	}
	
	public List<Status> searchTweets(String keywords, String lang) throws TwitterException, NotConnectedException
	{
		if(!authentified)
			throw new NotConnectedException();
		
		Query query = new Query(keywords);
		query.lang(lang);
		query.count(100);
		
		
		return twitterClient.search(query).getTweets();
	}
	
	public List<Status> searchTweets(String keywords) throws TwitterException, NotConnectedException
	{
		return searchTweets(keywords, "fr");
	}
	
	public RateLimitStatus getRateLimit() throws TwitterException
	{
		return this.twitterClient.getRateLimitStatus().get(("/search/tweets"));
	}

}
