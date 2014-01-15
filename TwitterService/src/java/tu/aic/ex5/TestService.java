/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tu.aic.ex5;

/**
 *
 * @author Administrator
 */
public class TestService {

    public static void main(String[] args) throws Exception {
        TwitterService test = new TwitterService();
        String[] kw = new String[2];
        kw[0]="Couch";
        kw[1]="Database";

        String[] results = test.twitterSearch(kw);
        for (String result : results)
            System.out.println(result);
    }

}
