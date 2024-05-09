package app.manuel.infrastructure.adapter.postgres.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "empresas")
public class Empresa {

    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;

    @Id
    @Column
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Column(name = "empresa_nombre", nullable = false)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "empresa_direccion", nullable = false)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "empresa_telefono", nullable = false)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
