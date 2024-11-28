package banco;

import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Admin extends Usuario {
	private int NroAdmin;
	private Movimiento movimeinto;
	public Admin(String nombre, String dni, String contrasena, int nroAdmin) {
		super(nombre, dni, contrasena);
		NroAdmin = nroAdmin;
	}
	public int getNroAdmin() {
		return NroAdmin;
	}
	public void setNroAdmin(int nroAdmin) {
		NroAdmin = nroAdmin;
	}
	public Movimiento getMovimeinto() {
		return movimeinto;
	}
	public void setMovimeinto(Movimiento movimeinto) {
		this.movimeinto = movimeinto;
	}
	
	public void menuAdmin() {
	    OpcionAdmin.Admin[] opciones = OpcionAdmin.Admin.values();
	    int opcionSeleccionada;

	    do {
	     
	        String[] opcionesMenu = new String[opciones.length];
	        for (int i = 0; i < opciones.length; i++) {
	            opcionesMenu[i] = opciones[i].name().replace("_", " ");
	        }

	    
	        opcionSeleccionada = JOptionPane.showOptionDialog(
	            null,
	            "Menú Administrador",
	            "Seleccione una opción",
	            JOptionPane.DEFAULT_OPTION,
	            JOptionPane.INFORMATION_MESSAGE,
	            null,
	            opcionesMenu,
	            opcionesMenu[0]
	        );

	        if (opcionSeleccionada == -1) {
	            opcionSeleccionada = opciones.length - 1; 
	        }

	        
	        switch (opciones[opcionSeleccionada]) {
	            case Ver_movimientos:
	                String dniMovimientos = JOptionPane.showInputDialog("Ingrese el DNI del cliente para ver movimientos:");
	                verMovimientosCliente(dniMovimientos);
	                break;

	            case Ver_cliente:
	                verCuentas(); 
	                break;

	            case Editar_cliente:
	                String dniModificar = JOptionPane.showInputDialog("Ingrese el DNI del cliente a modificar:");
	                modificarCliente(dniModificar);
	                break;

	            case Eliminar:
	                String dniEliminar = JOptionPane.showInputDialog("Ingrese el DNI del cliente a eliminar:");
	                eliminarCliente(dniEliminar);
	                break;

	            case Salir:
	                JOptionPane.showMessageDialog(null, "Sesión de administrador finalizada.");
	                break;
	        }
	    } while (opciones[opcionSeleccionada] != OpcionAdmin.Admin.Salir);
	}

	
	public void verMovimientosCliente(String dni) {
	    Cliente cliente = Cliente.buscarClienteDNI(dni); 
	    if (cliente == null) {
	        JOptionPane.showMessageDialog(null, "No se encontró un cliente con el DNI: " + dni);
	        return;
	    }

	    LinkedList<Movimiento> movimientos = cliente.getCuenta().getMovimientos();
	    if (movimientos == null || movimientos.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El cliente no tiene movimientos registrados.");
	        return;
	    }

	    String detalles = "Movimientos del cliente " + cliente.getNombre() + ":\n";
	    for (Movimiento movimiento : movimientos) {
	        detalles += movimiento.getFechaHora() + " - " + movimiento.getDetalle() + "\n";
	    }

	    JOptionPane.showMessageDialog(null, detalles);
	}

	public void modificarCliente(String dni) {
	    Cliente cliente = Cliente.buscarClienteDNI(dni); 
	    if (cliente == null) {
	        JOptionPane.showMessageDialog(null, "No se encontró un cliente con el DNI: " + dni);
	        return;
	    }

	    String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del cliente:");
	    if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
	        cliente.setNombre(nuevoNombre);
	    }

	    String nuevaContrasena = JOptionPane.showInputDialog("Ingrese la nueva contraseña del cliente:");
	    if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
	        cliente.setContrasena(nuevaContrasena);
	    }

	    JOptionPane.showMessageDialog(null, "Datos del cliente actualizados exitosamente.");
	}

	public void eliminarCliente(String dni) {
	    Cliente cliente = Cliente.buscarClienteDNI(dni); 
	    if (cliente == null) {
	        JOptionPane.showMessageDialog(null, "No se encontró un cliente con el DNI: " + dni);
	        return;
	    }

	    Usuario.getUsuarios().remove(cliente); 
	    JOptionPane.showMessageDialog(null, "El cliente " + cliente.getNombre() + " ha sido eliminado del sistema.");
	}

	public void verCuentas() {
	    LinkedList<Cliente> usuarios = Cliente.getUsuarios();
	    String detalles = "Auditoría de Cuentas:\n";

	    for (Cliente usuario : usuarios) {
	        if (usuario instanceof Cliente) {
	            Cliente cliente = (Cliente) usuario;
	            Cuenta cuenta = cliente.getCuenta();
	            detalles += "Cliente: " + cliente.getNombre() + " (DNI: " + cliente.getDni() + ")\n";
	            detalles += "  Número de cuenta: " + cuenta.getNroCuenta() + "\n";
	            detalles += "  Saldo: $" + cuenta.getSaldo() + "\n";
	            detalles += "  Tarjeta: " + (cuenta.getTarjeta() != null ? cuenta.getTarjeta() : "No asignada") + "\n";
	        }
	    }

	    JOptionPane.showMessageDialog(null, detalles);
	}



}
