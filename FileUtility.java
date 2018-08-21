package akshay.javaInternship.filemanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.io.*;
import java.util.*;
/**
 * @author akshay
 */
public class FileUtility {
    // constant variable declaration
    private static final String zipFolder = "E:\\zip folder\\";

    private static final String extractFolder = "E:\\Extracted Files";

    private static final String encryptedFileLocation = "E:\\Encrypted Files\\";

    private static final String decryptedFileLocation = "E:\\Decrypted File\\";


    ///file operations
    public static void createFile(String inputFile, String content) throws IOException {
        try {
            File file = new File(inputFile);
            // if file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //get the file in File writer
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            
            //initialize the buffer writter with the file
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            
            //write the content in the file
            bufferWriter.write(content);
            
            //flush and close the buffer
            bufferWriter.flush();
            bufferWriter.close();

            System.out.println("File " + inputFile + " has been created successfully..!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reNameFile(String inputFile, String newFileName) {
        //get the file path in File object
        File file = new File(inputFile);
        //validate the file
        if (file.isFile()) {
            String fileDirectory = file.getParent();
            File newName = new File(fileDirectory + "\\" + newFileName);
            //perform rename and check it also
            if (file.renameTo(newName)) {
                System.out.println("File has been Renamed.");
            } else {
                System.out.println("Error in Renaming the file.");
            }
        } else {
            System.out.println("Invalid file path");
        }
    }


    public static void deleteFile(String inputFile) throws FileNotFoundException {
        //get the file path in File object
        File file = new File(inputFile);
        //validate the file
         if (file.isFile()) {
            try {
                //delete and check if the opertaion has completed or not
            	
	                	file.delete();
	                	System.out.println("File deleted successfully."); 
                }

        	 catch(Exception e)
        	{
       	     System.out.println("No such file exists");
        	}
        }
							
    }
   
    public static void createDirectory(String inputDirectory) {
        //get the file path in File object
        File file = new File(inputDirectory);
        if (file.exists()) {
            System.out.println("The directory is already present");
        } 
        else {
            //use mkdirs() or mkdir() and check its return value
            if (!file.exists()) {
			file. mkdir();
			}
			
			
   		 }
   	}
    public static void reNameDirectory(String inputFile, String newDirName) {
        //get the file path in File object
        File file = new File(inputFile);
        if (file.isDirectory()) {
            File newName = new File(file.getParent() + "\\" + newDirName);
            //use renameTo() and check its return value 
			file.renameTo(newName);
			}			
    }

    public static void deleteDirectory(String inputDirectory) 
    {
        //get the file path in File object
        File directory = new File(inputDirectory);
        //check if its a directory  or not
        if (directory.isDirectory ()) 
        {
        //check if the directory has childs or not
            if (directory.list().length == 0) 
            {
                directory.delete();
                System.out.println("Directory is deleted : "+ directory.getAbsolutePath());
            } 
            else  
            {
        		//ask user whether he wants to delete the directory or not
           		System.out.println("Directory not empty do you still want to delete it?\n1. Yes\t2.No");
           		int decision;
           		Scanner sc = new Scanner(System.in);
           		decision = sc.nextInt();    
				if(decision == 1)
				{	
		       		//delete files inside the directory one by one
		       		deleteFilesInsideDirectory(directory);
		       		//delete parent directory
		       		directory.delete();

		       		if (!directory.exists()) 
		       		{
		       			System.out.println("Directory has been deleted.");
		       		} 
			   		else 
			   		{
		       			System.out.println("Deletion failed");
		       		}
       			} 
				else if(decision == 2) 
				{
		       		System.out.println("Delete directory request cancelled by user.");
		    	} 
		    	else 
		    	{
			       deleteDirectory(inputDirectory);
			    }
       		}
      	} 
	   	else 
	   	{
       		System.out.println("Invalid file directory");
       	}
    }

	// code this method in a recursive fashion
    private static void deleteFilesInsideDirectory(File element) 
    {
        if (element.isDirectory()) 
        {
            if (element.listFiles().length == 0) 
            {
    			//delete directory
                element.delete();
            } 	
            else 
            {
            	for(File subDirectory : element.listFiles())
            	{
    				//delete files one by one
  					deleteFilesInsideDirectory(subDirectory);
	    		}
    		} // end of else-part
    	} // end of outer-if
      
	  // delete file
      element.delete();
    }

    public static void listFilesFromDirectory(String inputDirectory) {
        //get the file path in File object
        File directory = new File(inputDirectory);
        //check if its a directory  or not
        if (directory.isDirectory())
        	{
            //check if the directory has childs or not
            if (directory.listFiles().length == 0)
				{
					System.out.println("Directiory has no child.");
				}
			else
				{
					 //seperate the files and print [Folder] or [File]
					for(File subDirectory : directory.listFiles())
					{
						if(subDirectory.isFile())
						{
							System.out.println("[File] "+subDirectory.getName());
						}
						else
						{
							System.out.println("[Folder] "+subDirectory.getName());
						}
					}
				}
			}
        else 
        	{
            System.out.println("Invalid file directory");
        	}
    }

	
    public static void copyFile(String inputPath, String outputPath) throws IOException {
        
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath + "//" + inputFile.getName());
        File outputLocation = new File(outputPath);
        if(outputLocation.isDirectory())
        {
	    // Check if same fileName or DirectoryName does not exist
	    	
        //for(File subDirectory : outputPath.listFiles())
        	if(outputFile.exists())
        	{
        		System.out.println("File already exist.");
        	}
        	else
        	{
				//write the streams to the OutputStream to copy the data
				InputStream is = new FileInputStream(inputFile);
				OutputStream os = new FileOutputStream(outputFile);
				byte[] buffer = new byte[1024];
				int length;
				//write the streams to the OutputStream to copy the data
				while ((length = is.read(buffer)) > 0) 
				{
					os.write(buffer, 0, length);
				}
					is.close();
					os.flush();
            		os.close();
			}

        } 
		else
		{
            System.out.println("Please enter valid file path");
        }
        			
    }

    
// --------------------
	
// 	//Phase 2 of the project : security operations
    public static void encryptFile(String inputPath) {
       	 File file = new File(encryptedFileLocation);
        if (file.exists()) {
            System.out.println("The directory is already present");
        } 
        else {
            //use mkdirs() or mkdir() and check its return value
            if (!file.exists()) {
			file. mkdir();
			}
			
   		 }
        File inputFile = new File(inputPath);
        //check if file is exist or not
        
        if(!inputFile.exists())
        	{
        		System.out.println("File does not exist.");

        	}
		
        else
        {
		//Encrypted file location
            File encryptedFileLocation = new File(FileUtility.encryptedFileLocation + inputFile.getName());
            try 
            {
				//Encrypt file
		        EncryptDecrypt.encryptFile(inputFile, encryptedFileLocation);
	        } 
	        catch (Exception ex) 
	        {
                ex.printStackTrace();
            }
        }

    }
  	 public static void decryptFile(String inputPath) {
  	 	 File file = new File(decryptedFileLocation);
        if (file.exists()) {
            System.out.println("The directory is already present");
        } 
        else {
            //use mkdirs() or mkdir() and check its return value
            if (!file.exists()) {
			file. mkdir();
			}
		}
			
        File inputFile = new File(inputPath);
        //check if file is exist or not
        if(!inputFile.exists())
        	{
        		System.out.println("File does not exist.");

        	}
        
        //Decrypted file location
        else
            {
            		File decryptedFileLocation = new File(FileUtility.decryptedFileLocation + inputFile.getName());

            try 
            {
        		//Decrypt file
          		EncryptDecrypt.decryptFile(inputFile, decryptedFileLocation);
            } 
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        } 
    }



// // --------------

    //Phase 3 of the project : compress - decompress
      public static void compressFile(String inputFile) throws IOException {

        File file = new File(inputFile);
        String fileNameWithExtension = file.getName();
        String fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
        if(file.exists() && file.isFile())
        {
        //make directory if not exists
        File directory=new File(zipFolder);
        boolean flag=false;
        if(!directory.exists())
        {
            flag=directory.mkdir();
        }    
        //perform zip - logic and code explained in the pdf doc 
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(fileName+".zip"));
        ZipEntry zipEntry = new ZipEntry(fileNameWithExtension);
        zip.putNextEntry(zipEntry);

        FileInputStream fis = new FileInputStream(file);
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        int length;
        while ((length = fis.read(buffer)) > 0) {
        zip.write(buffer, 0, length);
        }
        zip.close();
        fis.close();
        
        System.out.print("\nFile has been compressed successfully..!\n");
    }
        else
            System.out.print("\nInvalid file path \n");
    }
            

        	

    public static void decompress(String zFile)throws IOException {
		File file = new File(zFile);
		ZipFile zip = new ZipFile(file);
		
		BufferedInputStream is=null;
		boolean flag=false;
		  
		File directory=new File(extractFolder);
	     if(!directory.exists())
		  {
		
		  flag=directory.mkdir();
		  } 
        //perform un-zip - logic and code explained in the pdf doc 
        Enumeration zipFileEntries= zip.entries();
         while (zipFileEntries.hasMoreElements()) {
        //Decompress here
		
		ZipEntry entry = (ZipEntry)zipFileEntries.nextElement();
		is = new BufferedInputStream(zip.getInputStream(entry));
		 
       
       // establish buffer for writing file
      
	   final int BUFFER = 2048;
	   byte data[] = new byte[BUFFER];
	   
   
   
	// write the current file to disk
   
       File get=new File(entry.getName());
	   File fos1= new File(extractFolder+"//"+get);
	   FileOutputStream fos= new FileOutputStream(fos1);
       BufferedOutputStream dest= new BufferedOutputStream(fos,BUFFER);
	   System.out.print("\n File has been Decompressed successfully..! to folder E:\\Extracted Files\\ \n");
      // read and write until last byte is encountered
	  int currentByte;
      while ((currentByte= is.read(data,0,BUFFER)) != -1) {
	  dest.write(data, 0, currentByte);
 	  }
			dest.flush();
            dest.close();
            is.close();
	
		 }
	}
} // end of FileUtility class


