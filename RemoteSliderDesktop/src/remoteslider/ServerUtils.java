package remoteslider;

import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Implements the server side of the application. It is intended to be used by 
 * a TCP server.
 * @author Miguel Blanco Godón [github(meluchoMZ)]
 */
class ServerUtils {
    
    /**
     * Mananges the behaviour of the TCP server that runs this object.
     * @param socket a Socket that has the TCP connection to manage.
     * @return false if the server is closed remotely by a close order, true in 
     * other case.
     */
    static boolean ManageConnection(Socket socket) {
        try {
            InputStream input = new BufferedInputStream(socket.getInputStream());
            byte[] data = new byte[1024];
            input.read(data);
            System.out.println(new String(data));      
        } catch (IOException ioEX) {
            System.err.print("Error" + ioEX.getMessage());
            return false;
        }
        return false;
    }
}
