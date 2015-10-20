package currentmood.util;

import java.util.Date;

import twitter4j.Status;

public class Tweet {
	
	protected long id;
	protected String user;
	protected String text, query;
	protected Date createdAt;
	protected int annotation;

	public Tweet(long id, String user, String text, Date createdAt, String query, int annotation)
	{
		this.id = id;
		this.user = user;
		this.text = text;
		this.createdAt = createdAt;
		this.query = query;
		this.annotation = annotation;
	}
	
	public Tweet(long id, String user, String text, Date createdAt, String query)
	{
		this(id,user,text,createdAt,query,-1);
	}
	
	public Tweet(Status status, String query)
	{
		this(status.getId(), status.getUser().getName(), status.getText(), status.getCreatedAt(), query, -1);
	}

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getQuery() {
		return query;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	public int getValue()
	{
		return this.annotation;
	}
	
	public void setValue(int newValue)
	{
		this.annotation=newValue;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Tweet t = (Tweet) o;
		
		return (t.getId() == this.id || t.getUser().equals(user) && t.getText().equals(text));
	}

}
