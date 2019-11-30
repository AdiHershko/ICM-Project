package server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import application.Controller;
import application.DataBaseController;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer
{
  //Class variables *************************************************

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port)
  {
    super(port);
  }


  //Instance methods ************************************************

  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
	 // String s = new String((String) msg);
	  //System.out.println(s);
	  if(msg == null) return;
	  //  System.out.println("Message received: " + msg + " from " + client);
	    //this.sendToAllClients(msg);
	  ArrayList<String> arr = new ArrayList<String>(Arrays.asList(((String)msg).split(" ")));
	  switch(arr.get(0))
	  {
	  case "CONNECTED":
	  {
//		  Controller._ins.refreshTable();
		  try {
			client.sendToClient("Connected to the server: " +client);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  break;
	  }
	  case "TEST1": try {

			//response to client
			 client.sendToClient("test1: " +msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  break;
	  case "TEST2":
		  try {

				//response to client
				 client.sendToClient("test2:" +arr.subList(1, arr.size()).toString());
				 try {
					 DataBaseController.addToDB("test2,0,test2,test2,test2,test");
				 } catch (Exception e) { System.out.println("Cant add to DB"); }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  break;
	  case "ADDLINE":
	  {
		  String str=arr.subList(1, arr.size()).toString();
		  try {
			  DataBaseController.addToDB(str);
		  } catch(Exception e) { System.out.println("Could not add to db"); }
		  try {
			client.sendToClient("ADD: added to db!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  }

	  //db con



	  }


  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

  //Class methods ***************************************************

  /**
   * This method is responsible for the creation of
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555
   *          if no argument is entered.
   */
  public static void main(String[] args)
  {
    int port = 0; //Port to listen on
    DataBaseController.Connect();

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }

    EchoServer sv = new EchoServer(port);

    try
    {
      sv.listen(); //Start listening for connections
    }
    catch (Exception ex)
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
