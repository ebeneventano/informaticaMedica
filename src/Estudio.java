import java.awt.image.BufferedImage;

public class Estudio {

	private BufferedImage imagen;
	private String nombre;
	private String descripcion;
	private String notas;
	
	public Estudio(BufferedImage imagen, String nombre, String descripcion,
			String notas) {
		super();
		this.imagen = imagen;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.notas = notas;
	}

	public BufferedImage getImagen() {
		return imagen;
	}

	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

}
