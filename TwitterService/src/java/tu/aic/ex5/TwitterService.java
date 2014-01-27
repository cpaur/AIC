/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tu.aic.ex5;

import java.util.LinkedList;
import javax.jws.WebService;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


/**
 *
 * @author Install
 */
@WebService()
public class TwitterService {

    private TwitterFactory tf;

	private Twitter getTwitter() {
		if (tf == null) {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey("2ZTIyBAnDHbBlexGLwjILQ")
			  .setOAuthConsumerSecret("IobnjEAF8YKmyDgMNLyjJlU4iaozyESK1jtra404q2I")
			  .setOAuthAccessToken("2210259386-rkUPkMEVzjHJtFlvciOF19fLqOXdQTJkQCTZFk8")
			  .setOAuthAccessTokenSecret("H8GyfbkyrtqrYy7ZxvQhIGgIh55L69QamJad3GEOuzIJd");

            cb.setUseSSL(true);
			tf = new TwitterFactory(cb.build());
            
		}
		return tf.getInstance();
	}
    
	public String[] twitterSearch(String[] keywords) throws Exception {
		Twitter t = getTwitter();
        Query q = new Query(join(keywords, " "));
		q.setCount(150);
        q.setLang("en");
        
		QueryResult result;
		try {
			result = t.search(q);
		} catch (TwitterException e) {
			return new String[] {"Error during Twitter Search: " + e.getMessage()};
            //throw new Exception("Error during Twitter Search: " + e.getMessage());
		}
		List<Status> list = result.getTweets();
		List<String> retval = new LinkedList<String>();

		int i = 0;
		for (Status st : list) {
            String fin = st.getText().replace('\n', ' ').trim();
            String finLower = fin.toLowerCase();

            if (fin.length() > 2) {
                boolean missingKw = false;
                for (String kw : keywords)
                    if (kw != null && kw.trim().length() > 1 && !finLower.contains(kw.toLowerCase())) {
                        missingKw = true;
                        break;
                    }
                if (missingKw) continue;
                
                if (fin.length() > 50) fin = fin.substring(0,50);
                retval.add(fin);
                if (retval.size() >= 30) break;
            }
		}

		return retval.toArray(new String[0]);
	}

	public String twitterSearchCsv(String keywordsCsv) throws Exception {
		String[] kw = keywordsCsv.split(",");
		String[] ergebnis = twitterSearch(kw);
		int l = ergebnis.length;
		for (int i = 0; i < l; i++)
			ergebnis[i] = ergebnis[i].replace('|', ' ');

		return join(ergebnis, "|");
	}

	private static String join(String[] s, String delim) {
	     StringBuilder builder = new StringBuilder();

	     for (String str : s ) {
	    	 builder.append(str).append(delim);
	     }

	     return builder.substring(0, builder.length() - 1);
	 }

}
