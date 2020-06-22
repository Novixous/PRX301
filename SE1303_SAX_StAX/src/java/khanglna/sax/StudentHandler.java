/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.sax;

import java.io.Serializable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Novixous
 */
public class StudentHandler extends DefaultHandler implements Serializable {

    private String username, password;
    private boolean foundUsername, foundPassword;
    private String currentTagname;
    private String fullname;
    private boolean found;

    public StudentHandler() {
    }

    public StudentHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!found) {
            if (qName.equals("student")) {
                String id = attributes.getValue("id");
                if (id.equals(username)) {
                    this.foundUsername = true;
                }
            }
        }
        this.currentTagname = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       this.currentTagname = "";
    }
    

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!found) {
            String str = new String(ch, start, length);
            if (foundUsername) {
                if (currentTagname.equals("lastname")) {
                    this.fullname = str.trim();
                } else if (currentTagname.equals("middlename")) {
                    this.fullname += " " + str.trim();
                } else if (currentTagname.equals("firstname")) {
                    this.fullname += " " + str.trim();
                } else if (currentTagname.equals("password")) {
                    this.foundUsername = false;
                    if (str.trim().equals(password)) {
                        this.foundPassword = true;
                    }
                }
            }
            if (foundPassword) {
                if (currentTagname.equals("status")) {
                    this.foundPassword = false;
                    System.out.println("Fullname " + this.fullname);
                    if (!str.trim().equals("dropout")) {
                        found = true;
                    }
                }
            }
            System.out.println("Str: " + str);
        }
    }

    public String getFullname() {
        return fullname;
    }

    public boolean isFound() {
        return found;
    }

}
