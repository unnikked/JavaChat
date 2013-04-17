import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerManager extends Remote {
	String listServers() throws RemoteException;
	Server joinServer(String serverName) throws RemoteException;
	void addServer(String serverName) throws RemoteException;
}
