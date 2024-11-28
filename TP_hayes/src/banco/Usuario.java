package banco;

import java.util.LinkedList;

import javax.swing.JOptionPane;

abstract class Usuario {
	private String nombre;
	private String dni;
	protected String contrasena;
	private static LinkedList<Cliente> usuarios = new LinkedList<Cliente>(); ;
	public Usuario(String nombre, String dni, String contrasena) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.contrasena = contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public static LinkedList<Cliente> getUsuarios() {
		return usuarios;
	}
	public static void setUsuarios(LinkedList<Cliente> usuarios) {
		Usuario.usuarios = usuarios;
	}
	public void Sesion() {	
	}
	public void registrarse(String nombreCliente, String pass) {	
	}
	public static String validarNumeros(String mensaje) { 
		boolean flag ;
		String num ="" ;
		do {
			flag =true;
			num = JOptionPane.showInputDialog(mensaje);
			while (num.isEmpty()) {
				num = JOptionPane.showInputDialog(mensaje);
			}
			for (int i = 0; i < num.length(); i++) {
				if (!Character.isDigit(num.charAt(i))) {
					flag = false;
					break;
				}
			}
		} while (!flag);
		return num;
	}
	public static String validarCaracteres(String mensaje) {
		String palabra = "";
		while (palabra.equals("")) {
			palabra = JOptionPane.showInputDialog(mensaje);
		}
		return palabra;
	}
}
