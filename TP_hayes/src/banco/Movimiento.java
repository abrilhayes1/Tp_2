package banco;

import java.time.LocalDate;


public class Movimiento {
	private LocalDate fechaHora;
	private Cliente cliente;
	private String detalle;
	public Movimiento(LocalDate fechaHora, Cliente cliente) {
		super();
		this.fechaHora = fechaHora;
		this.cliente = cliente;
	}

	public LocalDate getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	


}
