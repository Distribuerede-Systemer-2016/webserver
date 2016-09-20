import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {


  private int port;
  private ServerSocket serverSocket;

  public WebServer(){
    this.port = 1337; //default port
  }

  public WebServer(int port){
    this.port = port;
  }

  public void start(){
    try {

      this.serverSocket = new ServerSocket(this.port);

      while (true){

        System.out.println("Wating for clients on port " + this.port);

        //Hang while-loop and wait for socket request
        Socket remoteSocket = this.serverSocket.accept();

        System.out.println("Connection!");

        //Read from input stream (from client)
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));

        //Print request headers from client
        String str = ".";
        while (!str.equals("")) {
          str = inFromClient.readLine();
          //System.out.println(str);
        }


        //Create output stream (to client)
        DataOutputStream outToClient = new DataOutputStream(remoteSocket.getOutputStream());


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
        remoteSocket.close();

      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
