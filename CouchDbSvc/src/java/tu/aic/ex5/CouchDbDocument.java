package tu.aic.ex5;

import java.io.Serializable;

public class CouchDbDocument implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1430934098235488489L;
	private String id;
	private String rev;
	private String title;
	private String content;
	
	public CouchDbDocument() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
