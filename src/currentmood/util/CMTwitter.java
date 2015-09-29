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
	
	public void setProxy(Proxy proxy)
	{
		if(authentified)
			reset();
		
		twitterConfiguration.setHttpProxyHost(proxy.getHost());
		twitterConfiguration.setHttpProxyPort(proxy.getPort());
		
		this.proxy = proxy;
	}
	
	public void connect()
	{
		if(authentified)
			reset();
		
		TwitterFactory twitterFactory = new TwitterFactory(twitterConfiguration.build());
		twitterClient = twitterFactory.getInstance();
		
		authentified = true;
	}
	
	protected void storeAccessToken(long token, AccessToken accessToken)
	{
		this.token = accessToken.getToken();
		this.tokenSecret = accessToken.getTokenSecret();
	}
	
	public List<Status> searchTweets(String keywords) throws TwitterException, NotConnectedException
	{
		if(!authentified)
			throw new NotConnectedException();
		
		Query query = new Query(keywords);
		
		return twitterClient.search(query).getTweets();
	}
	
	public RateLimitStatus getRateLimit() throws TwitterException
	{
		return this.twitterClient.getRateLimitStatus().get(("/search/tweets"));
	}

}
