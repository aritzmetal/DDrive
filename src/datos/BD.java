package datos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;

import excepciones.BaneadoException;
import seguridad.Encriptado;
import usuarios.Usuario;


public class BD {
	private static String key = "92AE31A79FEEB2A3"; // llave
	private static String iv = "0123456789ABCDEF";// Vector de encriptación
	
	
	private Connection con;
	private static Statement stmt;
	
	
	public BD(){
		conectar();
	}
	/**
	 * Metodo que crea una sentencia para acceder a la base de datos 
	 */
	public void crearSentencia()
	{
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que permite conectarse a la base de datos
	 */

	public void conectar()
	{
		try {
		//	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con= DriverManager.getConnection("jdbc:ucanaccess://C:/Users/aritz/workspace/DDrive/src/datos/Usuarios.accdb");
			crearSentencia();
		}catch(Exception e)
		{
			System.out.println("No se ha podido conectar a la base de datos");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que cierra una sentencia 
	 */
	public void cerrarSentencia()
	{
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que permite desconectarse de la base de datos
	 */
	public void desconectar()
	{
		try {
			cerrarSentencia();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void crearUsuario (String dni, String nom, String pass, String preg_seg, int sel_seg){
		try {
		//	pass = Encriptado.encriptar(this.key, this.iv, pass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = "INSERT INTO usuarios(DNI,nombre,password,pregunta_seguridad,seleccion_seguridad,baneado) VALUES('"+dni+"','"+nom+"','"+pass+"','"+preg_seg+"',"+sel_seg+",0)";
		
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * Metodo que realiza el login dentro de la base de datos
	 * @param int seleccion 
	 * 
	 */
	
	public Object obtenerSeleccion (String nom,String colum) {
		Object seleccion = 0;
		String query = "SELECT * FROM usuarios WHERE nombre='"+nom+"'";
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				seleccion = rs.getObject(colum);
				}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  seleccion;
		
	}
	
	
	public int seleccionarUsuario (String nom, String pass) throws BaneadoException{
		
		//Crear un usuario para enviarlo por server??
		Usuario us = null;
		
		int comp= 0;
		String query = "SELECT * FROM usuarios WHERE nombre='"+nom+"'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(rs.next()) {
				try {
					String nombre = rs.getString("nombre");
				//	String contrasenya = Encriptado.desencriptar(this.key, this.iv, rs.getString("password"));
					String contrasenya = rs.getString("password");
					
					int baneado = rs.getInt("baneado");
					
					if(baneado==0) {
						
					
					if(nombre.equals(nom)) {
							comp= 1;
							
						if(contrasenya.equals(pass)) {
							comp= 2;
						}
					}
					}else {
						rs.close();
						throw new BaneadoException(us);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comp;
	}
	
	public void cambiarPass(String user,String password) {
		try {
		//	password = Encriptado.encriptar(key, iv, password);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String query = "UPDATE usuarios SET password='"+password+"' WHERE nombre='"+user+"'";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
