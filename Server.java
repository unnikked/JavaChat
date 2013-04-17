import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Server extends Remote, Serializable {
	boolean join(Client c, String username) throws RemoteException;
	void broadcast(String message) throws RemoteException;
}
