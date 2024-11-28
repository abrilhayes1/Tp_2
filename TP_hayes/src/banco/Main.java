package banco;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 menu[] opciones = menu.values(); 
	        int opcionSeleccionada;

	        do {

	            String[] opcionesMenu = new String[opciones.length];
	            for (int i = 0; i < opciones.length; i++) {
	                opcionesMenu[i] = opciones[i].name().replace("_", " "); //PREGUNTAR GAMA NO QUIERO DESAPROBAR
	            }
	            opcionSeleccionada = JOptionPane.showOptionDialog(
	                null,
	                "Seleccione una opción",
	                "Menú Principal",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.INFORMATION_MESSAGE,
	                null,
	                opcionesMenu,
	                opcionesMenu[0]
	            );
	            if (opcionSeleccionada == -1) {
	                JOptionPane.showMessageDialog(null, "Gracias por usar el sistema!");
	                break;
	            }
	            switch (opciones[opcionSeleccionada]) {
	                case Menú_Cliente:
	                    Cliente cliente = new Cliente("Generico", "0", "1234", "Cliente", null);
	                    cliente.Sesion();
	                    break;
	                case Menú_Administrador:
	                    Admin admin = new Admin("Abril","12","a",34);
	                    admin.menuAdmin();
	                    break;

	                case Salir:
	                    JOptionPane.showMessageDialog(null, "Gracias por elegirnos!");
	                    break;
	            }
	        } while (opciones[opcionSeleccionada] != menu.Salir);

		
	}
	public enum menu {
		Menú_Cliente, Menú_Administrador, Salir
	}
}
