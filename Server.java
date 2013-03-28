import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	boolean join(Client c, String username) throws RemoteException;
	void broadcast(String message) throws RemoteException;
}
