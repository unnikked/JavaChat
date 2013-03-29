import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class ChatServer extends UnicastRemoteObject implements Server {
	private Map<String, Client> users;
	
	public ChatServer() throws RemoteException {
		users = new HashMap<String, Client>();
	}
	
	public synchronized boolean join(Client client, String username) throws RemoteException {
		if(!users.containsKey(username)) {
			users.put(username, client);
			System.out.println(username + " has joined the server");
			return true;
		}
		return false;
	}
	
	public synchronized void broadcast(String message) throws RemoteException {
		Collection<Client> clients = users.values();
		for(Client c : clients) {
			c.receive(message);
		}
		System.out.println(message);
	}
	
	public static void main(String[] args) {
		/*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
		try {
			Naming.rebind("//localhost/jChat", new ChatServer());
		} catch (RemoteException e) {
			System.out.println("Network Error!");
		} catch (MalformedURLException e) {
			System.out.println("URL not valid!");
		}
	}
}
