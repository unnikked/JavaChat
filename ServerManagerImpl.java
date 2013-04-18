import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class ServerManagerImpl extends UnicastRemoteObject implements ServerManager {
	private HashMap<String, Server> servers;
	
	public ServerManagerImpl() throws RemoteException {
		servers = new HashMap<String, Server>();
		
		try {
		    System.setProperty("java.rmi.server.hostname", "199.230.111.115");
		    //Registry registry = LocateRegistry.getRegistry("localhost");
		    Registry registry = LocateRegistry.createRegistry(1099);

		    registry.rebind("jChat", this);
		    System.out.println("ServerManager factory registered.");
		} catch (Exception e) {
		    System.err.println("Error registering service: "
		            + e.getMessage());
		}
		
	}
	
	public synchronized String listServers() throws RemoteException {
		return servers.values().toString();
	}
	
	public synchronized Server joinServer(String serverName) throws RemoteException {
		return servers.get(serverName);
	}
	
	public void addServer(String serverName) throws RemoteException {
		servers.put(serverName, new ChatServer(serverName));
		System.out.println(serverName + " created");
	}
	
	public static void main(String[] args) {
		try {
			ServerManager hand = new ServerManagerImpl();
			hand.addServer("FreeStuf");
			hand.addServer("UnnikkedBlog");
			hand.addServer("Linux");
		} catch (RemoteException e) {
			System.out.println("Network Error!");
		}
	}
}
