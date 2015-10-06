package currentmood.util;

import java.util.Date;

public class Tweet {
	
	private static final long serialVersionUID = 612840971502887287L;
	protected long id;
	protected String user;
	protected String text, query;
	protected Date createdAt;

	public Tweet(long id, String user, String text, Date createdAt, String query)
	{
		this.id = id;
		this.user = user;
		this.text = text;
		this.createdAt = createdAt;
		this.query = query;
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
	
	@Override
	public boolean equals(Object o)
	{
		Tweet t = (Tweet) o;
		
		if(t.getUser().equals(user) && t.getText().equals(text))
			return true;
		
		else return false;
	}

}
