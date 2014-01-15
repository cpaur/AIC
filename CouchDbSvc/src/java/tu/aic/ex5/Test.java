/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tu.aic.ex5;

/**
 *
 * @author Install
 */
public class Test {


    public static void main(String[] args) {
        CouchDbSvc test = new CouchDbSvc();
        String[] params = {"Google", ""};
        String[] response = test.searchDocumentsFor(params);
        for (String s : response)
            System.out.println(s);
    }
}
