package currentmood.util;

import java.util.Date;

public class Tweet {
	
	private static final long serialVersionUID = 612840971502887287L;
	protected long id;
	protected String user;
	protected String text, query;
	protected Date createdAt;
	protected int value;

	public Tweet(long id, String user, String text, Date createdAt, String query, int value)
	{
		this.id = id;
		this.user = user;
		this.text = text;
		this.createdAt = createdAt;
		this.query = query;
		this.value = value;
	}
	
	public Tweet(long id, String user, String text, Date createdAt, String query)
	{
		this(id,user,text,createdAt,query,-1);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		return this.value;
	}
	
	public void setValue(int newValue)
	{
		this.value=newValue;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Tweet t = (Tweet) o;
		
		return (t.getId() == this.id || t.getUser().equals(user) && t.getText().equals(text));
	}

}
