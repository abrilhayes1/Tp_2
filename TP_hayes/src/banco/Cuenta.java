package banco;

import java.time.LocalDate;

import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Cuenta {
	private int nroCuenta;
	private double saldo;
	private String tarjeta;
	private LinkedList<Movimiento> movimientos = new LinkedList<>();
	public Cuenta(int nroCuenta, double saldo, String tarjeta) {
		super();
		this.nroCuenta = nroCuenta;
		this.saldo = saldo;
		this.tarjeta = tarjeta;
		this.movimientos = new LinkedList<>();
	}
	public int getNroCuenta() {
		return nroCuenta;
	}
	public void setNroCuenta(int nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public String getTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}
	public LinkedList<Movimiento> getMovimientos() {
		return movimientos;
	}
	public void setMovimientos(LinkedList<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	public void depositar(double monto, Cliente cliente) {
        if (monto > 0) {
            this.saldo += monto;
            Movimiento movimiento = new Movimiento(LocalDate.now(), cliente);
            movimiento.setDetalle("Depósito: $" + monto);
            this.movimientos.add(movimiento);
        } else {
        	JOptionPane.showMessageDialog(null, "El monto debe ser mayor a 0.");
           
        }
    }
	public boolean transferir(Cuenta cuentaDestino, double monto, Cliente cliente) {
	    if (monto > 0 && monto <= this.saldo) {
	        this.saldo -= monto;
	        cuentaDestino.depositar(monto, cliente);
	        Movimiento movimiento = new Movimiento(LocalDate.now(), cliente);
	        movimiento.setDetalle("Transferencia de $" + monto + " a la cuenta " + cuentaDestino.getNroCuenta());
	        this.movimientos.add(movimiento);

	        Movimiento movimientoDestino = new Movimiento(LocalDate.now(), cliente);
	        movimientoDestino.setDetalle("Transferencia recibida de $" + monto + " desde la cuenta " + this.nroCuenta);
	        cuentaDestino.getMovimientos().add(movimientoDestino);
	        return true; 
	    } else {
	        return false; 
	    }
	}
	
	public boolean retirar(double monto, Cliente cliente) {
	    if (monto > 0 && monto <= this.saldo) {
	     
	        this.saldo -= monto;
	        Movimiento movimiento = new Movimiento(LocalDate.now(), cliente);
	        movimiento.setDetalle("Retiro: $" + monto);
	        this.movimientos.add(movimiento);

	        return true; 
	    } else if (monto > this.saldo) {
	        JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar el retiro.");
	    } else {
	        JOptionPane.showMessageDialog(null, "El monto debe ser mayor a 0.");
	    }
	    return false; 
	}



	
	

}
