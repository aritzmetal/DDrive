package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import datos.Mensaje;
import usuarios.Usuario;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.awt.event.ActionEvent;


public class Navegador extends JFrame {

	//Paneles  -------------------------------
	private JPanel panel_Titulo;
	private JPanel panel_Central;
	private JPanel panel_tree;
	private JPanel panel_Botonera;
	private JPanel panel_BotonesTree;
	//Etiquetas ------------------------------
	private JLabel lblTitulo;
	private JLabel lblNombreArchivo;
	//Botones --------------------------------
	private JButton btnSubirArchivo;
	private JButton btnDescargar;
	private JButton btnEliminar;
	private JButton btnRenombrar;
	private JButton btnGuardar ;
	private JButton btnEditar;
	//Demas componentes ----------------------
	private JTextArea textArea;

	private JScrollPane scrollPane;
	
	private JEditorPane editorPane;
	private JScrollPane scrollPane_Tree;
	private JTree arbol;
	DefaultTreeModel dftm;
	
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private Usuario us;
	
	public Navegador(Usuario us,ObjectOutputStream os, ObjectInputStream is) {
		this.os=os;
		this.is=is;
		this.us=us;
		
		
		this.setBounds(500, 200, 600, 680);
		this.setResizable(false);
		
		
		try {
			 dftm = (DefaultTreeModel) is.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panel_Titulo = new JPanel();
		panel_Titulo.setBackground(new Color(255, 165, 0));
		getContentPane().add(panel_Titulo, BorderLayout.NORTH);
		
		lblTitulo = new JLabel("DDrive");
		lblTitulo.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
		panel_Titulo.add(lblTitulo);
		
		panel_Central = new JPanel();
		panel_Central.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 99, 71)));
		panel_Central.setBackground(new Color(255, 165, 0));
		getContentPane().add(panel_Central, BorderLayout.CENTER);
		panel_Central.setLayout(null);
		
		panel_tree = new JPanel();
		panel_tree.setBackground(new Color(255, 250, 240));
		panel_tree.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 140, 0)));
		panel_tree.setBounds(12, 13, 282, 470);
		panel_Central.add(panel_tree);
		panel_tree.setLayout(new BorderLayout(0, 0));
		
		arbol = new JTree(dftm);
		
		scrollPane_Tree = new JScrollPane(arbol);
		panel_tree.add(scrollPane_Tree);
		
		panel_Botonera = new JPanel();
		panel_Botonera.setBackground(new Color(255, 248, 220));
		panel_Botonera.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 140, 0)));
		panel_Botonera.setBounds(306, 13, 264, 225);
		panel_Central.add(panel_Botonera);
		panel_Botonera.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBackground(new Color(255, 248, 220));
		textArea.setBounds(12, 149, 240, 63);
		panel_Botonera.add(textArea);
		textArea.setEditable(false);
		btnEditar = new JButton("Crear Carpeta");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String seleccion = JOptionPane.showInputDialog("Nombre carpeta");
					DefaultTreeModel model = (DefaultTreeModel) arbol.getModel();
	            
	            DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
	            DefaultMutableTreeNode padre =(DefaultMutableTreeNode) hijo.getParent();
	            DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(seleccion);
				
	            if(seleccion!=null && seleccion.length()<2) {
	            	System.out.println("Mandando carpeta");
	            
				  String	pathEnviar = "";
		            Object[] paths = arbol.getSelectionPath().getPath();
		            
		            if( hijo.getUserObject().toString().contains(".")) {
		            	model.insertNodeInto(nuevo, padre, nuevo.getChildCount());

					    for (int i=1; i<paths.length; i++) {
					    	pathEnviar += paths[i];
					        if (i+1 <paths.length-1 ) {
					        	pathEnviar += File.separator;
					        }
					    }
		            }else {
		            	System.out.println("Es directorio");
		            	model.insertNodeInto(nuevo, hijo, nuevo.getChildCount());

					    for (int i=1; i<paths.length; i++) {
					    	pathEnviar += paths[i];
					        if (i+1 <paths.length ) {
					        	pathEnviar += File.separator;
					        }
					    }
		            }
		            
		             pathEnviar += "/"+seleccion;
		            
		            //Mandamos primero el nodo padre
		         
		           
		            
		            Mensaje msg1 = new Mensaje(null, "carpeta");
	                try {
						os.writeObject(msg1);
						
					
					   
					    
					    os.writeObject(pathEnviar);
					    
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                
	                textArea.setText("Carpeta "+ seleccion+" creada");
	            }else {
	            	textArea.setText("Nombre muy corto");
	            }
			}
		});
		btnEditar.setFont(new Font("Segoe UI Black", Font.PLAIN, 10));
		btnEditar.setBounds(12, 77, 105, 31);
		panel_Botonera.add(btnEditar);
		
		btnSubirArchivo = new JButton("Subir Archivo");
		btnSubirArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				  int returnVal = chooser.showOpenDialog(Navegador.this);
				  File seleccion = null;
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                seleccion = chooser.getSelectedFile();
		                System.out.println(seleccion.getName());
		            } 
		            
		            if(seleccion!=null && seleccion.isFile()) {
		            	
		     
		            DefaultTreeModel model = (DefaultTreeModel) arbol.getModel();
		            System.out.println("Mandando archivo");
		            DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
		            DefaultMutableTreeNode padre =(DefaultMutableTreeNode) hijo.getParent();
		            DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(seleccion.getName());
		           
		            String	pathEnviar = "";
		            Object[] paths = arbol.getSelectionPath().getPath();
		            
		            if( hijo.getUserObject().toString().contains(".")) {
		            	model.insertNodeInto(nuevo, padre, nuevo.getChildCount());

					    for (int i=1; i<paths.length; i++) {
					    	pathEnviar += paths[i];
					        if (i+1 <paths.length-1 ) {
					        	pathEnviar += File.separator;
					        }
					    }
		            }else {
		            	System.out.println("Es directorio");
		            	model.insertNodeInto(nuevo, hijo, nuevo.getChildCount());

					    for (int i=1; i<paths.length; i++) {
					    	pathEnviar += paths[i];
					        if (i+1 <paths.length ) {
					        	pathEnviar += File.separator;
					        }
					    }
		            }
		            
		             pathEnviar += "/"+seleccion.getName();
		            
		            //Mandamos primero el nodo padre
		         
		           
		            
		            Mensaje msg1 = new Mensaje(null, "subir");
	                try {
						os.writeObject(msg1);
						
					
					   
					    
					    os.writeObject(pathEnviar);
					    
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		            try {
						byte[] contenidoSelccion = Files.readAllBytes(seleccion.toPath());
						os.writeObject(contenidoSelccion);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		            	textArea.setText("Archivo subido");
		              } else {
		            	  textArea.setText("Ningun archivo seleccionado");
		              }
		            
		            
		                
			}
		});
		btnSubirArchivo.setFont(new Font("Segoe UI Black", Font.PLAIN, 10));
		btnSubirArchivo.setBounds(12, 33, 105, 31);
		panel_Botonera.add(btnSubirArchivo);
		
		btnDescargar = new JButton("Descargar");
		btnDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDescargar.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
		btnDescargar.setBounds(147, 33, 105, 31);
		panel_Botonera.add(btnDescargar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mensaje msg = new Mensaje(us,"SAVE");
				
				try {
					
					System.out.println("Mandando peticion de Guardar");
					os.writeObject(msg);
					
					msg = (Mensaje) is.readObject();
					
					textArea.setText(msg.getMess());
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				} 
				
				
			}
		});
		btnGuardar.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
		btnGuardar.setBounds(147, 77, 105, 31);
		panel_Botonera.add(btnGuardar);
		
		lblNombreArchivo = new JLabel("Nombre Archivo");
		lblNombreArchivo.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		lblNombreArchivo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreArchivo.setBounds(306, 244, 264, 63);
		panel_Central.add(lblNombreArchivo);
		
		panel_BotonesTree = new JPanel();
		panel_BotonesTree.setBackground(new Color(255, 248, 220));
		panel_BotonesTree.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 140, 0)));
		panel_BotonesTree.setBounds(12, 496, 282, 81);
		panel_Central.add(panel_BotonesTree);
		panel_BotonesTree.setLayout(null);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
		btnEliminar.setBounds(12, 13, 110, 43);
		panel_BotonesTree.add(btnEliminar);
		
		btnRenombrar = new JButton("Renombrar");
		btnRenombrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre");
				DefaultTreeModel model = (DefaultTreeModel) arbol.getModel();
            
            DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();           
            String nombreViejo = hijo.getUserObject().toString();
            
           
			
            if(nuevoNombre!=null && nuevoNombre.length()>2) {
            	System.out.println("Cambiando nombre");
            	
            
			  String	pathEnviar = "";
	            Object[] paths = arbol.getSelectionPath().getPath();
			            	

				    for (int i=1; i<paths.length-1; i++) {
				    	pathEnviar += paths[i];
				        if (i+1 <paths.length ) {
				        	pathEnviar += File.separator;
				        }
				    }
	            
	            
	             pathEnviar += "/";
	            
	            
	         
	           
	            
	            Mensaje msg1 = new Mensaje(us, "RENOM");
                try {
					os.writeObject(msg1);								    
				    os.writeObject(pathEnviar);
				    os.writeObject(nombreViejo);
				    os.writeObject(nuevoNombre);
				    
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                textArea.setText("Nombre "+ nuevoNombre+" cambiado");
                hijo.setUserObject(nuevoNombre);
                model.nodeChanged(hijo);
            }else {
            	textArea.setText("Nombre muy corto");
            }
			}
		});
		btnRenombrar.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
		btnRenombrar.setBounds(160, 13, 110, 43);
		panel_BotonesTree.add(btnRenombrar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(306, 320, 264, 257);
		panel_Central.add(scrollPane);
		
		editorPane = new JEditorPane();
		editorPane.setBackground(new Color(255, 239, 213));
		scrollPane.setViewportView(editorPane);
		arbol.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
				if(nodo != null) {
					lblNombreArchivo.setText((String) nodo.getUserObject());
				}else {
					lblNombreArchivo.setText("");
				}
					
				
				
				
				
			}
		});
		

		
	}
	
	@Override
	public void dispose() {
		Mensaje msg = new Mensaje(us,"SHUT");
		
		try {
			
			System.out.println("Mandando peticion de cerrado");
			this.os.writeObject(msg);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		System.out.println("Peticion concedida, cerrando...");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Cerrando streams");
		try {
			this.is.close();
			this.os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
		
		
		
	}
}
