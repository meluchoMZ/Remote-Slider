package remoteslider;

import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import java.io.IOException;

/**
 * This class manages a single thread TCP server. 
 * @author Miguel Blanco Godón [github(meluchoMZ)]
 */
class RSserver extends Thread {
    final private int port;
    
    /**
     * Creates an TCP multithread server.
     * @param portNumber an integer, the port where the server will start. 
     */
    public RSserver(int portNumber) {
        this.port = portNumber;
    }
    
    /**
     * Starts a TCP server on a different thread.
     */
    @Override
    public void run() {
        
        ServerSocket socketTcp = null;
        ServerSocketFactory serverFactory;
        
        boolean done = false;
        try {
            serverFactory = ServerSocketFactory.getDefault();
            socketTcp = serverFactory.createServerSocket(port);
            socketTcp.setSoTimeout(0);
            
            Socket custSocket;
            while (!done) {
                custSocket = socketTcp.accept();
                done = ServerUtils.ManageConnection(custSocket);
                try {
                    custSocket.close();
                } catch (IOException ioE) {
                    System.err.print("Cannot close socket. Error: " + ioE.getMessage());
                }
            }   
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (socketTcp != null) socketTcp.close();
            } catch (IOException ioEX) {
                System.err.println("Cannot close socket. Error :" + ioEX.getMessage());
            }
        }
    }
}
