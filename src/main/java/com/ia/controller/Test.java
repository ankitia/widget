package com.ia.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;




public class Test {


    public static void main(String[] args)
    {
    	
    	
    	/*ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);*/
    	ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
        
        for (int i = 1; i <= 5; i++)
        {
            Task task = new Task("Task " + i);
            System.out.println("Created : " + task.getName());
 
            /*executor.execute(task);*/
            executor.scheduleWithFixedDelay(task, 2, 2, TimeUnit.SECONDS);
        } 
        //executor.shutdown();
    	
    	
    	
    	
    	
    	        File file = new File("write.txt");

    	        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
    	            String contents = "The quick brown fox" + 
    	                System.getProperty("line.separator") + "jumps over the lazy dog.";

    	            writer.write(contents);
    	            
    	            System.out.println(file.getAbsolutePath());
    	            
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    
    	System.out.println("Done");
		 /* List<Book> books = readBooksFromCSV("/home/jaynil/Desktop/shagnik/BH-xls.csv");
		  
		  String action = "BH";
		  
		  
	      // let's print all the person read from CSV file
	      List<Book> books2 = new ArrayList<>();
		  for (Book b : books) {
			  Book book = new Book();
			  
			  
			  if(action.equalsIgnoreCase("BH")) {
				  if(b.getName().equalsIgnoreCase("Message Subject:")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }else if(b.getName().equalsIgnoreCase("Message Subject:")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }else if(b.getName().equalsIgnoreCase("Sent")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }else if(b.getName().equalsIgnoreCase("Delivered")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }else if(b.getName().equalsIgnoreCase("Bounced")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }else if(b.getName().equalsIgnoreCase("opened")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }else if(b.getName().equalsIgnoreCase("Clicked")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }else if(b.getName().equalsIgnoreCase("Soft Bounce")) {
		        	  book.setName(b.getName());
		        	  book.setValue(b.getValue());
		          }
			  }else if(action.equalsIgnoreCase("ER")) {
				  
			  }
			  
	         
	          
	          if(book.getName()!=null && !book.getName().equalsIgnoreCase("null")) {
	        	  books2.add(book);
	          }
	         // System.out.println(book.getName());
	      }
		  
		  
      System.out.println("Test----"+books2.size());
      
      for (int i=0;i<books2.size();i++) {
		
    	  System.out.println(books2.get(i).getName() +"--"+books2.get(i).getValue());
		
	}*/
      
    }
  
   
    private static List<Book> readBooksFromCSV(String fileName) {
        List<Book> books = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Book book = createBook(attributes);

                // adding book into ArrayList
                books.add(book);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return books;
    }
    private static Book createBook(String[] metadata) {
        
    	String name = "",value="";
    	if(metadata.length > 0) {
    		name = metadata[0];	
    	}
    	if(metadata.length > 1) {
    		value = metadata[1];	
    	}
    	

        // create and return book of this metadata
        return new Book(name, value);
    }

}

class Book {
    private String name;
    private String value;
    
    
    public Book() {
    	
    }

    public Book(String name, String value) {
        this.name = name;
        this.value = value;
    
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

    @Override
    public String toString() {
        return "Book [name=" + name + ", value=" + value  + "]";
    }

}

