package currentmood.util;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class CMTwitter {

	protected boolean authentified;
	
	protected Twitter twitterClient;
	ConfigurationBuilder twitterConfiguration;
	
	protected String token, tokenSecret;
	
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
	
	public void connect() throws TwitterException
	{
		twitterClient = TwitterFactory.getSingleton();
		twitterClient.setOAuthConsumer(TwitterConfidentialInfo.TWITTER_CONSUMER_KEY, TwitterConfidentialInfo.TWITTER_CONSUMER_SECRET);
		RequestToken requestToken = twitterClient.getOAuthRequestToken();
		AccessToken accessToken = twitterClient.getOAuthAccessToken();
		storeAccessToken(twitterClient.verifyCredentials().getId() , accessToken);
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
		
		Query query = new Query("source:twitter4j "+keywords);
		
		return twitterClient.search(query).getTweets();
	}

}
