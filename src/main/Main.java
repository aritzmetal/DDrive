package main;



import ventanas.Login;

public class Main {
	public static void main(String [] args){
			
		
		Login login = new Login(args);
		
		Thread funcionCliente =  new Thread(login);
  	  	funcionCliente.start();
			
	}
	
	
}
