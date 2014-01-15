package tu.aic.ex5;

import net.sf.json.JSONObject;

public class CouchDbEntry {

	private static final String ID = "_id";
	private static final String REV = "_rev";
	private static final String TITLE = "title";
	private static final String CONTENT = "content"; 
	
	public JSONObject o;
	
	public String getId() {
		return o.has(ID) ? o.getString(ID) : null;
	}
	public String getRev() {
		return o.has(REV) ? o.getString(REV) : null;
	}
	
	protected String getTitle() {
		return o.has(TITLE) ? o.getString(TITLE) : null;
	}
	
	protected String getContent() {
		return o.has(CONTENT) ? o.getString(CONTENT) : null;
	}
	
	public CouchDbEntry(String json) {
		o = JSONObject.fromObject(json);
	}
	
	public CouchDbEntry(CouchDbDocument doc) {
		o = new JSONObject();
		if (doc.getId() != null) o.accumulate(ID, doc.getId());
		if (doc.getRev() != null) o.accumulate(REV, doc.getRev());
		if (doc.getTitle() != null) o.accumulate(TITLE, doc.getTitle());
		if (doc.getContent() != null) o.accumulate(CONTENT, doc.getContent());
	}
	
	public CouchDbDocument getDocument() {
		CouchDbDocument retval = new CouchDbDocument();
		retval.setId(getId());
		retval.setRev(getRev());
		retval.setTitle(getTitle());
		retval.setContent(getContent());
		return retval;
	}
	
	public String getJSON() {
		return o.toString();
	}
}
