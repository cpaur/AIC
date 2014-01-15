/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testPackage;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.netbeans.enterprise.bpel.asynchronoussampleschemanamespace.TypeB;

/**
 *
 * @author Install
 */
@WebService()
public class HumanService {

    private static LinkedList<TypeB> queue = new LinkedList<TypeB>();
    private static LinkedList<String> finished = new LinkedList<String>();

    public static String[] getIDs() {
        String[] retVal = new String[queue.size()];
        int c = 0;
        for (TypeB item : queue) {
            retVal[c] = item.getId();
            c++;
        }
        return retVal;
    }

    public static boolean finishTypeB(TypeB fin) {
        TypeB queueItem = getItem(fin.getId());
        if (queueItem!=null) {
            queueItem.setPdfData(fin.getPdfData());
            finished.add(fin.getId());
            return true;
        } else {
            return false;
        }
    }

    public static boolean finishID(String id) {
        TypeB queueItem = getItem(id);
        if (queueItem != null) {
            finished.add(id);
            return true;
        } else {
            return false;
        }
    }

    public static TypeB getItem(String id) {
        for (TypeB content : queue) {
            if (content.getId().equals(id))
                return content;
        }
        return null;
    }

    @WebMethod()
    public TypeB confirm(TypeB input) {
        queue.add(input);
        String id = input.getId();
        if (finished.contains(id)) finished.remove(id);

        while (!finished.contains(id)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(HumanService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        TypeB retVal = input;
        for (TypeB content : queue) {
            if (content.getId().equals(id)) {
                retVal = content;
                queue.remove(retVal);
                break;
            }
        }
        finished.remove(retVal.getId());

        return retVal;
    }
}