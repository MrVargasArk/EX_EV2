package entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Proveedor {
    private int idProveedor_;
    private String ruc;
    private String empresa;
    private String contacto;
    private String telefono;
    private String correo;
    private String direccion;
    private int idCategoria_;
    private String categoriaDescripcion;
    private int estado;
}
