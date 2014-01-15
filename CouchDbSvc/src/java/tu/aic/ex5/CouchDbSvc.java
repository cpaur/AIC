/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tu.aic.ex5;

import javax.jws.WebService;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Vector;

import javax.jws.WebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fourspaces.couchdb.CouchResponse;
import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.ViewResults;
/**
 *
 * @author Administrator
 */
@WebService()
public class CouchDbSvc {

	private String host = "127.0.0.1";
	//public String getHost() { return host; }
	//public void setHost(String newHost) { host = newHost; }

	private int port = 5984;
	//public int getPort() { return port; }
	//public void setPort(int newPort) { port = newPort; }

	private String database = "aic";
	//public String getDatabase() { return database; }
	//public void setDatabaseName(String switchToDatabase) { database = switchToDatabase; }

	/*
	public String getVersion() {
		try {
			Content c = Request.Get("http://" + host + ":" + port + "/").execute().returnContent();
			JSONObject o = JSONObject.fromObject(c.asString());
			return o.get("version").toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String getUUID() {
		try {
			String uuidUri = host + "_uuids";

			Content c = Request.Get(URI.create(uuidUri)).execute()
					.returnContent();
			JSONObject o = JSONObject.fromObject(c.asString());
			JSONArray a = o.getJSONArray("uuids");

			return a.get(0).toString();
		} catch (Exception ex) {
			return "Fehler\n" + ex.getMessage();
		}
	}

	public String createDb(String databaseName) {
		try {
			String dbUri = host + databaseName;

			Content c = Request.Put(dbUri).execute().returnContent();
			return c.asString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	*/

	public String createDocument(String id, String json) {
		try {
			Session s = getSession();
			Database db = s.getDatabase(database);
			Document doc = new Document(JSONObject.fromObject(json));
			if (id == null) {
				db.saveDocument(doc);
			} else {
				db.saveDocument(doc, id);
			}
			CouchResponse r = s.getLastResponse();
			return r.getBody();
		} catch (Exception e) {
			return e.getMessage();
		}
	}


	public String getDocument(String id) throws IOException {
		Session s = getSession();
		Database db = s.getDatabase(database);
		Document doc = db.getDocument(id);

		//CouchDbEntry entry = new CouchDbEntry(doc.toString());

		return doc.toString();
	}

	public String getTitle(String id) throws IOException {
		CouchDbEntry entry = new CouchDbEntry(getDocument(id).toString());
		return entry.getTitle();
	}

	public String getContent(String id) throws IOException {
		CouchDbEntry entry = new CouchDbEntry(getDocument(id).toString());
		return entry.getContent();
	}

	/**
	 * Durchsucht CouchDb nach allen Dokumenten, in denen alle Keywords im Content gefunden werden.
	 * @param keywords Ein String-Array der alle Keywords enth�lt. Ein Keyword darf keine Hochkomma (" oder ') enthalten!";
	 * @return Eine String-Liste mit den gesuchten Dokument-IDs.
	 */
	@SuppressWarnings("rawtypes")
	public String[] searchDocumentsFor(String[] keywords) {
		if (keywords == null || keywords.length == 0) return null;
		StringBuilder qry = new StringBuilder("function(doc) { if (");

		for (String keyword : keywords) {
			if (keyword.contains("\"") || keyword.contains("'")) return null;
			qry.append("(doc.content.indexOf('").append(keyword).append("') >= 0) && ");
		}
		int len = qry.length();
		qry.delete(len - 3, len - 1);

		qry.append(") emit(doc._id); }");

		Session s = getSession();
		Database db = s.getDatabase(database);
		ViewResults res = db.adhoc(qry.toString());
		JSONArray rows = res.getJSONArray("rows");

		if (rows.isEmpty() ) return null;

		Vector<String> list = new Vector<String>();

		Iterator iter = rows.iterator();
		do {
			JSONObject current = (JSONObject) iter.next();
			String id = current.getString("id");
			list.add(id);
		} while (iter.hasNext());

		return list.toArray(new String[] {});
	}

	public String searchDocumentsForCsv(String csv) {
		String[] keywords = csv.split(",");
		return join(searchDocumentsFor(keywords));
	}

	private Session getSession() {
		return new Session(host, port);
	}

	private static String join(String[] s) {
	     StringBuilder builder = new StringBuilder();

	     for (String str : s ) {
	    	 builder.append(str).append(",");
	     }

	     return builder.substring(0, builder.length() - 1);
	 }

}
