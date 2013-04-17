import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class ChatServer extends UnicastRemoteObject implements Server {
	private String serverName;
	private Map<String, Client> users;
	
	private static final long serialVersionUID = 1L;
	
	public ChatServer(String serverName) throws RemoteException {
		this.serverName = serverName;
		users = new HashMap<String, Client>();
	}
	
	public synchronized boolean join(Client client, String username) throws RemoteException {
		if(!users.containsKey(username)) {
			users.put(username, client);
			Collection<Client> clients = users.values();
			for(Client c : clients) {
				if(c != client) c.receive(username + " has joined the channel");
			}
			return true;
		}
		return false;
	}
	
	public synchronized void broadcast(String message) throws RemoteException {
		Collection<Client> clients = users.values();
		for(Client c : clients) {
			c.receive(message);
		}
	}
	
	public String toString() {
		return serverName;
	}
}
