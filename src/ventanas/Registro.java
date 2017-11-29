package ventanas;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

import usuarios.Usuario;
import javax.swing.LayoutStyle.ComponentPlacement;

import datos.Mensaje;
import excepciones.ExisteException;

import javax.swing.JComboBox;

public class Registro extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ObjectOutputStream os;
	private ObjectInputStream is;
//---------------------------------------->Flujos de datos
	
	public JFrame padre;
	private JTextPane textPane;
//--------------------------------------->Campos de texto	
	private JTextField textField;
	private JTextField textField_1;
//--------------------------------------->Contrasenyas	
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
//--------------------------------------->Paneles	
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
//--------------------------------------->Labels	
	private JLabel lblDeustodrive;
	private JLabel lblUsuario;
	private JLabel lblContrasenya;
	private JLabel lblContrasenya_1;
	private JLabel lblPreguntaSeguridad;
//--------------------------------------->Botones	
	private JButton btnCorregir;
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private String[] lista;
	
//--------------------------------------->Combo box	
	JComboBox<String> comboBox;

	public Registro( JFrame padre,ObjectOutputStream os,ObjectInputStream is) {
		
		
		lista = new String[4];
		this.padre=padre;
		this.os =os;
		this.is =is;
		

		this.setBounds(600, 300, 600, 440);
		
		//this.setBounds(padre.getX(),padre.getY(),padre.getWidth(),padre.getHeight()+40);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int registro=0;
				try {
				 registro = realizarRegistro();
				 
				} catch (ExisteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
					switch (registro){
					case 0:			
						
						System.out.println("usuario no encontrado procediendo a insertarlo");
						try {
							os.writeObject(new Mensaje(new Usuario( textField.getText(),passwordField.getText(), textField_1.getText(), String.valueOf(comboBox.getSelectedIndex())), "REG"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
								
					textPane.setText("Registro correcto");
					//------------------------------------>Crear la carpeta pertinente del usuario
											new File("./src/folders/"+textField.getText()).mkdir();
					//------------------------------------->
											dispose();	
											padre.setVisible(true);
					case 1:
						textPane.setText("ERROR:\n Usuario existente, pruebe otra vez.");
						try {
							os.writeObject(new Mensaje(new Usuario( textField.getText(),passwordField.getText(), textField_1.getText(), String.valueOf(comboBox.getSelectedIndex())), "ERR"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case 2: 
						textPane.setText("ERROR:\n Usuario existente, pruebe otra vez.");
						try {
							os.writeObject(new Mensaje(new Usuario( textField.getText(),passwordField.getText(), textField_1.getText(), String.valueOf(comboBox.getSelectedIndex())), "ERR"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case 3:
						textPane.setText("Las contraseñas no son iguales");
						
						break;
					case 4:
						textPane.setText("Rellena todos los campos!!!");
						
						break;
					default:
						textPane.setText("");
						try {
							os.writeObject(new Mensaje(new Usuario( textField.getText(),passwordField.getText(), textField_1.getText(), String.valueOf(comboBox.getSelectedIndex())), "ERR"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
				
			}
		});
		

		btnCorregir = new JButton("Corregir");

		btnCorregir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				passwordField.setText("");
				passwordField_1.setText("");
				comboBox.setSelectedIndex(0);
				

			}
		});

		

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					os.writeObject(new Mensaje(new Usuario(), "CLOSE"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				padre.setVisible(true);
			}
		});
		
		//-------------------------------->Anyadimos botones al panel 1
		panel.add(btnAceptar);
		panel.add(btnCorregir);
		panel.add(btnCancelar);

		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);

		lblDeustodrive = new JLabel("DDrive");
		panel_1.add(lblDeustodrive);

		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);

		lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Verdana", Font.PLAIN, 18));

		textField = new JTextField();
		textField.setColumns(10);

		lblContrasenya = new JLabel("Contrase\u00F1a");
		lblContrasenya.setFont(new Font("Verdana", Font.PLAIN, 18));

		passwordField = new JPasswordField();

		lblContrasenya_1 = new JLabel("Contrase\u00F1a");
		lblContrasenya_1.setFont(new Font("Verdana", Font.PLAIN, 18));

		passwordField_1 = new JPasswordField();

		textPane = new JTextPane();
		textPane.setEditable(false);
		
		lblPreguntaSeguridad = new JLabel("Pregunta Seguridad");
		lblPreguntaSeguridad.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		comboBox = new JComboBox<String>();
		
		cargarSeleccion(lista);
		comboBox.setModel(new DefaultComboBoxModel<String>(lista));
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(156)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblContrasenya_1)
							.addContainerGap())
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblContrasenya))
									.addGap(28)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
										.addComponent(textField, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
										.addComponent(passwordField_1, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))))
							.addGap(160))))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(180)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
						.addComponent(lblPreguntaSeguridad, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
						.addComponent(comboBox, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
					.addGap(203))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContrasenya)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContrasenya_1)
						.addComponent(passwordField_1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblPreguntaSeguridad, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
		);
		panel_2.setLayout(gl_panel_2);
	}

	
	private int realizarRegistro () throws ExisteException{

		int seleccion=4;
		
		if (this.textField.getText().length() !=0 && this.passwordField.getPassword().length!=0 && this.passwordField_1.getPassword().length!=0 && this.textField_1.getText().length()!=0) {
				if(Arrays.equals(this.passwordField.getPassword(), this.passwordField_1.getPassword())){
				
				 try {
				Usuario	 us = new Usuario(this.textField.getText(),this.passwordField.getText());
					 
					 os.writeObject(new Mensaje(us, "REG"));
					 
					 //Esperar a la respuesta del handler
					 Mensaje msg = (Mensaje) is.readObject();
					 System.out.println(msg.toString());
					 
					seleccion = Integer.parseInt(msg.getMess());
					System.out.println(seleccion);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Ioexception");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("No se encuentra clase");
				}
			
				

		} else {
			seleccion=3;
			
		}
		}
			return seleccion;

		

	}
	
	@Override
    public void dispose(){
        
		this.padre.setVisible(true);
        super.dispose();
    }
	
	static void cargarSeleccion(String[] lista){
		String nom ="./src/usuarios/Preguntas.txt";
		int i=0;
		
		try {
		File f = new File(nom);
		
		
		FileReader fr = new FileReader(f);
		BufferedReader bfr = new BufferedReader(fr);
		

		String linea="";
			while(linea!=null && i<4){				
				if(linea!=null ){
					linea = bfr.readLine();	
					lista[i]=linea;
					i++;
					
						 };
			}
			

			bfr.close();
			
			
				} catch (IOException e) {
					e.printStackTrace();
			}
	}
	
}
