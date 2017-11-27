package ventanas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import usuarios.Usuario;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.AncestorListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.jvnet.substance.skin.SubstanceRavenLookAndFeel;

import excepciones.BaneadoException;
import excepciones.NoUsuarioException;
import excepciones.PassIncorrectaException;
import seguridad.Encriptado;

import javax.swing.JTextPane;
import javax.swing.LookAndFeel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Cliente.Mensaje;
import datos.BD;

public class Login extends JFrame implements Runnable {
	private Logger logerLogin;
	private int login =0;
	
	private static Socket clientSocket = null;
	  
	//Flujos de salida/entrada de datos
	private static ObjectInputStream is = null;
	private static ObjectOutputStream os = null;
	  
	//Datos de conexion
	private static String host = "localhost";
	private static int puertoDefecto = 1050;
	
	
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JTextPane textPane;

	// ------------------------------------------------>Paneles
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;

	// ------------------------------------------------>Botones
	private JButton btnAceptar;
	private JButton btnCorregir;
	private JButton btnCancelar;
	private JButton btnRegistro;
	private JButton btnKey;
	private JButton btnCorregir_1;

	private JPopupMenu popupMenu;
	private JPanel panel_4;	
	private JPanel panel_5;
	private JTextField textField_3;
	private JPanel panel_6;
	private JTextPane textPane_1;
	private JPanel panel_7;


	// ------------------------------------------------>Labels
	private JLabel lblComprobarContrasea;
	private JLabel lblNuevaContrasea;
	private JLabel lblRespuestaSeguridad;
	private JLabel lblPreguntaSeguridad;
	
	//----------------------------------------------->Lista
	
	private String[] lista;
	Usuario us;
	
	public Login(String[] args) {
		
		
		//Inicializacion del socket y puerto
		 if (args.length < 2) {
		      System.out.println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
		              + "Now using host=" + host + ", portNumber=" + puertoDefecto);
		    } else {
		      host = args[0];
		      puertoDefecto = Integer.valueOf(args[1]).intValue();
		    }
		
		//Iniciamos aqui la base de datos
		logerLogin = Logger.getLogger("ventanas.Login");
		
		us=null;
		lista = new String[4];

		this.setVisible(true);
		this.setBounds(600, 300, 600, 400);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnAceptar = new JButton("Aceptar");

		btnCorregir = new JButton("Corregir");
		btnCorregir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				textField.setText("");
				passwordField.setText("");
			}
		});

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				try {
					os.writeObject(new Mensaje(new Usuario(),"CLOSE"));
					Thread.sleep(1000);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});

		// -------------------------------------->Añadir botones
		panel.add(btnAceptar);
		panel.add(btnCorregir);
		panel.add(btnCancelar);

		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("DDrive");
		panel_1.add(lblNewLabel);

		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);

		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 14));

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Verdana", Font.BOLD, 14));

		passwordField = new JPasswordField();

		textPane = new JTextPane();
		textPane.setEditable(false);

		btnRegistro = new JButton();

		JLabel lblCambiarContrasea = new JLabel("Cambiar contrase\u00F1a...");
		lblCambiarContrasea.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 13));

		btnKey = new JButton("");
		btnKey.setBackground(new Color(255, 235, 205));



		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
				.createSequentialGroup().addGap(158)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_2.createSequentialGroup().addGap(23)
								.addComponent(lblCambiarContrasea, GroupLayout.PREFERRED_SIZE, 161,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnKey))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnRegistro, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
								.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createSequentialGroup()
										.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 76,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblContrasea, GroupLayout.PREFERRED_SIZE, 93,
														GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
												.addComponent(passwordField, 158, 158, 158).addComponent(textField,
														GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)))))
				.addContainerGap(167, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addComponent(btnRegistro, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGap(26)
						.addGroup(
								gl_panel_2.createParallelGroup(Alignment.BASELINE)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 31,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblContrasea))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCambiarContrasea, GroupLayout.PREFERRED_SIZE, 21,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnKey, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addGap(22)));
		panel_2.setLayout(gl_panel_2);

		// -----------------------------> Cambiar el tamaño a las imagenes
		try {
			Image registro = ImageIO.read(getClass().getResource("Register.png"));
			Image key = ImageIO.read(getClass().getResource("key.png"));

			Image registrotam = registro.getScaledInstance(btnRegistro.getWidth(), btnRegistro.getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			Image keyTamanyo = key.getScaledInstance(btnKey.getWidth(), btnKey.getHeight(),
					java.awt.Image.SCALE_SMOOTH);

			registro = registrotam;
			key = keyTamanyo;

			btnRegistro.setIcon(new ImageIcon(registro));
			btnKey.setIcon(new ImageIcon(key));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
			popupMenu = new JPopupMenu();
			addPopup(this,us,textField,btnKey, popupMenu);

			panel_7 = new JPanel();

			lblPreguntaSeguridad = new JLabel("Pregunta Seguridad");
			lblPreguntaSeguridad.setFont(new Font("Verdana", Font.BOLD, 14));

			textPane_1 = new JTextPane();
			textPane_1.setEditable(false);

			panel_6 = new JPanel();

			lblRespuestaSeguridad = new JLabel("Respuesta Seguridad");
			lblRespuestaSeguridad.setFont(new Font("Verdana", Font.PLAIN, 14));

			textField_3 = new JTextField();
			textField_3.setColumns(10);

			panel_5 = new JPanel();

			lblNuevaContrasea = new JLabel("Nueva Contrase\u00F1a");
			lblNuevaContrasea.setFont(new Font("Verdana", Font.PLAIN, 14));

			passwordField_1 = new JPasswordField();
			panel_4 = new JPanel();

			lblComprobarContrasea = new JLabel("Comprobar Contrase\u00F1a");
			lblComprobarContrasea.setFont(new Font("Verdana", Font.PLAIN, 14));

			passwordField_2 = new JPasswordField();
			JPanel panel_3 = new JPanel();

			// --------------------------->Añadir componentes al popup menu

			popupMenu.add(panel_7);
			panel_7.add(lblPreguntaSeguridad);
			popupMenu.add(textPane_1);
			popupMenu.add(panel_6);
			panel_6.add(lblRespuestaSeguridad);
			popupMenu.add(textField_3);
			popupMenu.add(panel_5);
			panel_5.add(lblNuevaContrasea);

			popupMenu.add(passwordField_1);
			popupMenu.add(panel_4);
			panel_4.add(lblComprobarContrasea);

			popupMenu.add(passwordField_2);
			popupMenu.add(panel_3);
			popupMenu.addPopupMenuListener(new PopupMenuListener() {
				
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
					try {
						os.writeObject(new Mensaje(new Usuario(), "NOPOPUP"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});

			btnCorregir_1 = new JButton("Corregir");

			JButton btnAceptar_1 = new JButton("Aceptar");
			panel_3.add(btnAceptar_1);
			
			
			btnAceptar_1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Usuario us = new Usuario(textField.getText(), passwordField_1.getText(), textField_3.getText());
					try {
						if(passwordField_1.getText().equals(passwordField_2.getText())) {
							os.writeObject(new Mensaje(us, "CAMBIO"));
						}else {
							os.writeObject(new Mensaje(us,"NOPOPUP"));
							textPane.setText("\nError en el cambio:\nContrase\u00f1as diferentes");
						}
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					popupMenu.setVisible(false);
					}
					
				
				}
			);
			
			panel_3.add(btnCorregir_1);

		

		btnRegistro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				crearRegistro(e);

			}
		});

		btnAceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Usuario us = new Usuario(textField.getText(),passwordField.getText());
					Mensaje msg = new Mensaje(us, "envio");
					//Enviar mensaje al servidor para realizar el login
					os.writeObject(msg);
					System.out.println(msg.toString());
					//Recibir la respuesta del server
					synchronized(is) {
					 msg=(Mensaje) is.readObject();
					}
					textPane.setText(msg.getMess());
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
	}
	
	private void limpiarPopup() {
		Login.this.textPane_1.setText("");
		Login.this.textField_3.setText("");
		Login.this.passwordField_1.setText("");
		Login.this.passwordField_2.setText("");
	}

	private void crearRegistro(ActionEvent e) {
		try {
			os.writeObject(new Mensaje(new Usuario(),"REG"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Registro reg = new Registro(this,os, is) ;
		this.dispose();
		reg.setVisible(true);
			revalidate();
	}



	


	 public void mostrarPregSeguridad (int pregunta) throws  NoUsuarioException{
		 Registro.cargarSeleccion(lista);		 
		 int  preg_seg=0;
			
		 if(!(this.textField.getText().length()==0)) {
			 
			preg_seg = pregunta;			
			textPane_1.setText(lista[preg_seg]);
			System.out.println("Finalizado");
		 }
		 
		 
			
				
				
			
		
	 }

	 
	 
	 
	private static void addPopup(Login log,Usuario us,JTextField textF,Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				log.limpiarPopup();
				showMenu(e,textF);
				try {
					os.writeObject(new Mensaje(new Usuario(textF.getText(), null), "CAMBIO"));
					int pregunta = (int) is.readObject();
					
					if(pregunta!=5)
					log.mostrarPregSeguridad(pregunta);
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoUsuarioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
					
			}

			public void mouseReleased(MouseEvent e) {
			  showMenu(e,textF);
			
			}

			private void showMenu(MouseEvent e,JTextField text) {
				if (!popup.isVisible() && text.getText()!="" ){
						popup.show(e.getComponent(), e.getX(), e.getY());
						
				}
			}
		});
	}

	@Override
	public void run() {
		 try {
			 /*
			 try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			 
				clientSocket = new Socket(host, puertoDefecto);
			
		     
				os = new ObjectOutputStream(clientSocket.getOutputStream());
				is = new ObjectInputStream(clientSocket.getInputStream());

			
				 while(login==0) {
					 Thread.sleep(1500);
				 }
				 
				 os.close();
			     is.close();
			     clientSocket.close();
			      
		 } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  	}
		
}
