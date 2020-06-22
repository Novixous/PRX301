/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.demo;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import khanglna.jaxb.dtos.Customer;

/**
 *
 * @author Novixous
 */
public class JAXBUnmarshallingDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            JAXBContext jc = JAXBContext.newInstance(Customer.class);
            Unmarshaller um = jc.createUnmarshaller();
            File f = new File("customers.xml");
            Customer cus = (Customer) um.unmarshal(f);
            System.out.println("Id = " + cus.getCustomerid());
            System.out.println("name = " + cus.getName());
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBUnmarshallingDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
