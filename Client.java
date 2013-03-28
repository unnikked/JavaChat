import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
	void receive(String message) throws RemoteException;
}
