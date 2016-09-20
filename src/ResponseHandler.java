import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by jesperbruun on 20/09/2016.
 */
public class ResponseHandler implements Runnable {

  private Socket remoteSocket;

  public ResponseHandler(Socket remoteSocket) {
    this.remoteSocket = remoteSocket;
  }

  @Override
  public void run() {

    try {
      System.out.println("Connection!");
      System.out.println("Thread count: " + Thread.activeCount());

      //Read from input stream (from client)
      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(this.remoteSocket.getInputStream()));

      //Print request headers from client
      String str = ".";
      while (!str.equals("")) {
        str = inFromClient.readLine();
        //System.out.println(str);
      }


      //Create output stream (to client)
      DataOutputStream outToClient = new DataOutputStream(this.remoteSocket.getOutputStream());


      //Write response headers
      outToClient.writeBytes("HTTP/1.0 200 OK\n");
      outToClient.writeBytes("Content-Type: application/json\n");
      outToClient.writeBytes("Server: Hackerbot\n");
      outToClient.writeBytes("\n");

      Thread.sleep(1000);

      outToClient.writeBytes("{\"hello\":\"world\"}");

      //Flush'n'close
      outToClient.flush();

      outToClient.close();
      this.remoteSocket.close();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
