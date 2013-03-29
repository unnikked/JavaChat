import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements Client, Runnable {
	private static Scanner sc;
	private String username;
	private Server server;
	private Thread t;
	
	public ChatClient() throws RemoteException, NotBoundException, MalformedURLException {
		sc = new Scanner(System.in);
		System.out.print("Choose your name: ");
		this.username = sc.nextLine();
		System.out.print("Insert server ip/domain: ");
		String url = sc.nextLine();
		server = (Server) Naming.lookup("rmi://"+url+"/jChat");
		if(server.join(this, this.username)) {
			System.out.println("Successfully logged in!");
		} else {
			System.out.println("Error: You are not logged in!");
		}
		t = new Thread(this);
		t.start();
	}
	
	public synchronized void receive(String message) throws RemoteException {
		System.out.println(message);
	}//
	
	public void run() {
		String msg;
		while(true) {
			try {
				System.out.print("> ");
				msg = sc.nextLine();
				server.broadcast("[" + new Date().toString() + "]" + username + ": " + msg);
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
