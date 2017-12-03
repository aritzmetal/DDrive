package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
	
	public Navegador() {
		this.setBounds(300, 400, 600, 680);
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
		
		arbol = new JTree();
		
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
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditar.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
		btnEditar.setBounds(12, 77, 105, 31);
		panel_Botonera.add(btnEditar);
		
		btnSubirArchivo = new JButton("Subir Archivo");
		btnSubirArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSubirArchivo.setFont(new Font("Segoe UI Black", Font.PLAIN, 11));
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
		
	}
}
