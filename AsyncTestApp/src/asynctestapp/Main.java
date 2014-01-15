/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asynctestapp;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.soap.AddressingFeature;
import org.netbeans.enterprise.bpel.asynchronoussampleclient.*;
import org.netbeans.enterprise.bpel.asynchronoussampleschemanamespace.*;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try { // Call Web Service Operation
            AsynchronousClientService service = new org.netbeans.enterprise.bpel.asynchronoussampleclient.AsynchronousClientService();
            MyPortTypeClient port = service.getAsynchronousClientPortName();
            // TODO initialize WS operation arguments here
            TypeA inputType = new org.netbeans.enterprise.bpel.asynchronoussampleschemanamespace.TypeA();

            inputType.setId(new BigInteger(128, new Random()).toString(16)); //QuickNDirty Hex
            List<String> keywords = inputType.getKeyword();
            keywords.add("Google");
            keywords.add("Apple");

            TypeB result = port.operationA(inputType);
            String fault = result.getFaultInfo();
            if (fault != null) {
                System.out.println("Fault: " + fault);
            } else {
                System.out.println("Result = " + new String(result.getPdfData()));

                FileOutputStream out = new FileOutputStream("out.pdf");
                out.write(result.getPdfData());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("FIN");
    }
}
