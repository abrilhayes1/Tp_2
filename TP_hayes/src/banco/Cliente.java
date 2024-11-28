package banco;

import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Cliente extends Usuario {

	private String tipo;
	private Cuenta cuenta;

	public Cliente(String nombre, String dni, String contrasena, String tipo, Cuenta cuenta) {
		super(nombre, dni, contrasena);
		this.tipo = tipo;
		this.cuenta = cuenta;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	@Override
	public void Sesion() {
		OpcionCliente.MenuCliente[] opciones = OpcionCliente.MenuCliente.values();
		int opcionSeleccionada;

		do {
			String[] opcionesMenu = new String[opciones.length];
			for (int i = 0; i < opciones.length; i++) {
				opcionesMenu[i] = opciones[i].name().replace("_", " ");
			}
			opcionSeleccionada = JOptionPane.showOptionDialog(null, "Menú Cliente", "Seleccione una opción",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesMenu, opcionesMenu[0]);
			if (opcionSeleccionada == -1) {
				opcionSeleccionada = opciones.length - 1;
			}
			switch (opciones[opcionSeleccionada]) {
			case Registrarse:
				Cliente nuevoCliente = Cliente.registrarse();
				if (nuevoCliente == null) {
					JOptionPane.showMessageDialog(null, "Registro cancelado o el DNI ya está registrado.");
				}
				break;

			case Iniciar_sesion:
				String nombre = validarCaracteres("Ingrese su nombre de usuario: ");
				String contrasena = validarCaracteres("Ingrese su contraseña: ");
				Cliente cliente = Cliente.login(nombre, contrasena);
				if (cliente != null) {
					JOptionPane.showMessageDialog(null, "Bienvenido, " + cliente.getNombre() + "!");
				
                    OpcionCliente.ClienteLogIn[] opcionesLogIn = OpcionCliente.ClienteLogIn.values();
                    int opcionSeleccionadaLogIn;

                    do {
                
                        String[] opcionesMenuLogIn = new String[opcionesLogIn.length];
                        for (int i = 0; i < opcionesLogIn.length; i++) {
                            opcionesMenuLogIn[i] = opcionesLogIn[i].name().replace("_", " ");
                        }

                        opcionSeleccionadaLogIn = JOptionPane.showOptionDialog(
                                null,
                                "Seleccione una operación",
                                "Menú Cliente Logueado",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                opcionesMenuLogIn,
                                opcionesMenuLogIn[0]
                        );

                        if (opcionSeleccionadaLogIn == -1) {
                            JOptionPane.showMessageDialog(null, "Sesión finalizada.");
                            break;
                        }

                     
                        switch (opcionesLogIn[opcionSeleccionadaLogIn]) {
                        
                        	case ver_cuenta:
                        		cliente.mostrarMenuCuenta();
                        		break;
                            case Retirar:
                            	cliente.retirar();
                              
                                break;
                                
                            case Transferir:
                                cliente.transferir();
                             
                                break;

                            case Depositar:
                               cliente.depositar();
                            
                                break;
                                
                            case Salir:
                            	JOptionPane.showMessageDialog(null, "Adios!");
                        }
                    } while (opcionesLogIn[opcionSeleccionadaLogIn] != OpcionCliente.ClienteLogIn.Salir);
				} else {
					JOptionPane.showMessageDialog(null,
							"Nombre de usuario o contraseña incorrectos. Intente nuevamente.");
				}
				break;

			case Salir:
				JOptionPane.showMessageDialog(null, "Adiós!");
				break;
			}
		} while (opciones[opcionSeleccionada] != OpcionCliente.MenuCliente.Salir);
	}
	public static Cliente login(String nombre, String contrasena) {
		for (Cliente usuario : Usuario.getUsuarios()) {
			if (usuario.getNombre().equals(nombre) && usuario.getContrasena().equals(contrasena)) {
				return usuario;
			}
		}
		return null;
	}
	public static boolean verificarUsuario(String dni) {
		for (Cliente usuario : Usuario.getUsuarios()) {
			if (usuario.getDni() == dni) {
				return true;
			}
		}
		return false;
	}
	public static int NumeroDeCuenta() {
	    return (int) (Math.random() * 90000) + 10000; 
	}
	public static Cliente registrarse() {
		String dni = validarNumeros("Ingrese DNI: ");
		if (Cliente.verificarUsuario(dni)) {
			JOptionPane.showMessageDialog(null, "Usted ya tiene una cuenta. Por favor, inicie sesión.");
			return null;
		} else {
			String nombreCliente = validarCaracteres("Ingrese nombre de usuario: ");
			String pass = validarCaracteres("Ingrese contraseña");
			int nroCuenta = NumeroDeCuenta();
			double saldo = 0.0; 
		    Cuenta nuevaCuenta = new Cuenta(nroCuenta, saldo, null); 
		    
			Cliente nuevoCliente = new Cliente(nombreCliente, dni, pass, "Cliente", nuevaCuenta);
			Usuario.getUsuarios().add(nuevoCliente);
			JOptionPane.showMessageDialog(null, "Bienvenido! Nos alegra tenerte entre nuestros clientes. \nSu numero de cuenta es: " + nroCuenta);
			return nuevoCliente;
		}
	}
	public static Cliente buscarClienteDNI(String dni) {
	    for (Usuario usuario : Usuario.getUsuarios()) {
	        if (usuario instanceof Cliente && usuario.getDni().equals(dni)) {
	            return (Cliente) usuario;
	        }
	    }
	    return null; 
	}
	public static boolean validarMonto(String texto) {
	   
	        double numero = Double.parseDouble(texto);
	        return numero > 0; 
	    
	}
	public void retirar() {
	    String montoRetiro = validarCaracteres("Ingrese el monto a retirar: ");
	    if (validarMonto(montoRetiro)) {
	        double monto = Double.parseDouble(montoRetiro);
	        if (this.getCuenta().retirar(monto, this)) {
	            JOptionPane.showMessageDialog(null, "Retiro exitoso. Nuevo saldo: $" + this.getCuenta().getSaldo());
	        } else {
	            JOptionPane.showMessageDialog(null, "No se pudo realizar el retiro. Verifique su saldo o el monto ingresado.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Monto inválido. Por favor ingrese un número válido.");
	    }
	}

	public void transferir() {
	    String dniDestino = validarCaracteres("Ingrese el DNI del cliente destino: ");
	    Cliente clienteDestino = Cliente.buscarClienteDNI(dniDestino);
	    if (clienteDestino == null) {
	        JOptionPane.showMessageDialog(null, "No hay coincidencias con DNI. No es posible realizar la transferencia.");
	        return;
	    }

	    String montoTransferencia = validarCaracteres("Ingrese el monto a transferir: ");
	    if (validarMonto(montoTransferencia)) {
	        double monto = Double.parseDouble(montoTransferencia);
	        if (this.getCuenta().transferir(clienteDestino.getCuenta(), monto, this)) {
	            JOptionPane.showMessageDialog(null, "Transferencia exitosa de $" + monto + " al cliente con DNI: " + dniDestino);
	        } else {
	            JOptionPane.showMessageDialog(null, "No se pudo realizar la transferencia. Verifique su saldo o el monto ingresado.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Monto inválido. Por favor ingrese un número válido.");
	    }
	}

	public void depositar() {
	    String montoDeposito = validarCaracteres("Ingrese el monto a depositar: ");
	    if (validarMonto(montoDeposito)) {
	        double monto = Double.parseDouble(montoDeposito);
	        this.getCuenta().depositar(monto, this);
	        JOptionPane.showMessageDialog(null, "Depósito exitoso. Nuevo saldo: $" + this.getCuenta().getSaldo());
	    } else {
	        JOptionPane.showMessageDialog(null, "Monto inválido. Por favor ingrese un número válido.");
	    }
	}

	public void mostrarMenuCuenta() {
	    OpcionCliente.VerCuenta[] opcionesCuenta = OpcionCliente.VerCuenta.values();
	    int opcionSeleccionada;

	    do {
	       
	        String[] opcionesMenuCuenta = new String[opcionesCuenta.length];
	        for (int i = 0; i < opcionesCuenta.length; i++) {
	            opcionesMenuCuenta[i] = opcionesCuenta[i].name().replace("_", " ");
	        }

	        opcionSeleccionada = JOptionPane.showOptionDialog(
	                null,
	                "Seleccione una opción",
	                "Menú Ver Cuenta",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.INFORMATION_MESSAGE,
	                null,
	                opcionesMenuCuenta,
	                opcionesMenuCuenta[0]
	        );

	        if (opcionSeleccionada == -1) {
	            opcionSeleccionada = opcionesCuenta.length - 1; 
	        }

	        switch (opcionesCuenta[opcionSeleccionada]) {
	            case Saldo:
	                JOptionPane.showMessageDialog(null, "Saldo actual: $" + this.getCuenta().getSaldo());
	                break;

	            case Transacciones:
	                mostrarMovimientos(); 
	                break;

	            case Datos_de_cuenta:
	                datosCuenta(); 
	                break;

	            case Salir:
	                JOptionPane.showMessageDialog(null, "Volviendo al menú principal...");
	                break;
	        }
	    } while (opcionesCuenta[opcionSeleccionada] != OpcionCliente.VerCuenta.Salir);
	}

	private void mostrarMovimientos() {
	    LinkedList<Movimiento> movimientos = this.getCuenta().getMovimientos();
	    if (movimientos == null || movimientos.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay movimientos registrados.");
	        return;
	    }

	    String detalles = "Movimientos:\n";
	    for (Movimiento movimiento : movimientos) {
	        detalles += movimiento.getFechaHora() + " - " + movimiento.getDetalle() + "\n";
	    }

	    JOptionPane.showMessageDialog(null, detalles);
	}

	private void datosCuenta() {
	    Cuenta cuenta = this.getCuenta();
	    String detalles = "Número de cuenta: " + cuenta.getNroCuenta() +
	                      "\nSaldo: $" + cuenta.getSaldo() +
	                      "\nTarjeta: " + (cuenta.getTarjeta() != null ? cuenta.getTarjeta() : "No asignada");

	    JOptionPane.showMessageDialog(null, detalles);
	}


	public static String validarNumeros(String mensaje) {
		boolean flag;
		String num = "";
		do {
			flag = true;
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
