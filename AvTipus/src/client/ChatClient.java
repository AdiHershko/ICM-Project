// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package client;

import ocsf.client.*;
import ocsf.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.Controller;
import application.Request;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************

  /**
   * The interface type variable.  It allows the implementation of
   * the display method in the client.
   */


  //Constructors ****************************************************

  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */

  public ChatClient(String host, int port)
    throws IOException
  {
    super(host, port); //Call the superclass constructor
    openConnection();
    System.out.println("Client connected");
  }


  //Instance methods ************************************************

  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg)
  {
	  if(msg == null) return;
	  System.out.println("from server: "+msg);
	  if (msg instanceof Object[])
	  {
		  Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ObservableList<Request> l = FXCollections.observableArrayList();
					for (Object o : (Object[]) msg)
					{
						l.add((Request)o);
					}
					Controller._ins.table.setItems(l);
				}
			});
	  }
	//bla bla check action
	  //logi
	  /*
	  Platform.runLater(new Runnable() {
		@Override
		public void run() {
			Controller._ins.refreshTable();
		}
	});*/

  }

  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromClientUI(Object message)
  {
    try
    {
    	sendToServer(message);
    }
    catch(IOException e)
    {
    	System.out.println("sending message to server failed");
    	quit();
    }
  }

  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
