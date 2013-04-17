import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements Client, Runnable {
	private static Scanner sc;
	private String username;
	private ServerManager hand;
	private Server server;
	private Registry registry;
	private Thread t;
	
	public ChatClient() throws RemoteException, NotBoundException, MalformedURLException {
		sc = new Scanner(System.in);
		System.out.print("Choose your name: ");
		this.username = sc.nextLine();
		System.out.print("Insert server ip/domain (at the moment use localhost): ");
		String url = sc.nextLine();
		registry = LocateRegistry.getRegistry(url);
		hand = (ServerManager) Naming.lookup("jChat");
		t = new Thread(this);
		t.start();
	}
	
	public synchronized void receive(String message) throws RemoteException {
		System.out.println(message);
	}//
	
	public void run() {
		String cmd, cmd2, msg;
		while(true) {
			try {
				cmd = sc.nextLine();
				if(cmd.equals("/list")) {
					System.out.println("List of active channels:\n" + hand.listServers());
				}
				else if(cmd.equals("/join")) {
					System.out.println("Select which channel do you want to join:");
					cmd2 = sc.nextLine();
					server = hand.joinServer(cmd2);
					if(server != null && server.join(this, this.username)) {
						System.out.println("Successfully logged in!");
						System.out.println("Now you can chat! Type your messages");
					} else {
						System.out.println("Error: You are not logged in!");
						continue;
					}
					while(true) {
						msg = sc.nextLine();
						server.broadcast("[" + new Date().toString() + "]" + username + ": " + msg);				
					}				
				} else {
					System.out.println("Command list:");
					System.out.println("/list - show the list of active channels");
					System.out.println("/join - join a channel and starts to chat");
				}
			} catch (RemoteException e) {
				System.out.println("Error with Remote Connection!");
			}
		}
	}
	
	public static void main(String[] args) {
		/*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
		try {
			new ChatClient();
		} catch (RemoteException e) {
			System.out.println("Network Error!");
		} catch (NotBoundException e) {
			System.out.println("Server not reachable!");
		} catch (MalformedURLException e) {
			System.out.println("URL not valid!");
		}
	}
}
