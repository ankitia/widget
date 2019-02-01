package com.ia.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {


    public static void main(String[] args)
    {
        String passwordToHash = "Info123*";
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
    }
  
   /* public static void check(String host, String storeType, String user,
    	      String password) 
    	   {
    	      try {

    	      //create properties field
    	      Properties properties = new Properties();

    	      properties.put("mail.pop3.host", host);
    	      properties.put("mail.pop3.port", "995");
    	      properties.put("mail.pop3.starttls.enable", "true");
    	      Session emailSession = Session.getDefaultInstance(properties);
    	  
    	      //create the POP3 store object and connect with the pop server
    	      Store store = emailSession.getStore("pop3s");

    	      store.connect(host, user, password);

    	      //create the folder object and open it
    	      Folder emailFolder = store.getFolder("INBOX");
    	      emailFolder.open(Folder.READ_ONLY);
    	      

    	      // retrieve the messages from the folder in an array and print it
    	      Message[] messages = emailFolder.getMessages();
    	      System.out.println("messages.length---" + messages.length);

    	      for (int i = 0, n = messages.length; i < n; i++) {
    	         Message message = messages[i];
    	         System.out.println("---------------------------------");
    	         System.out.println("Email Number " + (i + 1));
    	         System.out.println("Subject: " + message.getSubject());
    	         System.out.println("From: " + message.getFrom()[0]);
    	         System.out.println("Text: " + message.getContent().toString());
    	         System.out.println("Date: " + message.getSentDate() +"--");

    	      }

    	      //close the store and folder objects
    	      emailFolder.close(false);
    	      store.close();

    	      } catch (NoSuchProviderException e) {
    	         e.printStackTrace();
    	      } catch (MessagingException e) {
    	         e.printStackTrace();
    	      } catch (Exception e) {
    	         e.printStackTrace();
    	      }
    	   }*/
    
	
}
