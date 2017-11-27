package main;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceRavenLookAndFeel;

import ventanas.Login;

public class Main {
	public static void main(String [] args){
			
		
		Login login = new Login(args);
		
		Thread funcionCliente =  new Thread(login);
  	  	funcionCliente.start();
			
	}
	
	
}
