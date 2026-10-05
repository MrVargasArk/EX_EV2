package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Proveedor;
import util.MySqlDBConexion;

public class ProveedorModel {

    public int insertaProveedor(Proveedor obj) {
        String sql = "INSERT INTO proveedor_ "
                + "(ruc, empresa, contacto, telefono, correo, direccion, idCategoria_, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection cn = MySqlDBConexion.getConexion();
        if (cn == null) {
            return -1;
        }

        try (cn;
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, obj.getRuc());
            ps.setString(2, obj.getEmpresa());
            ps.setString(3, obj.getContacto());
            ps.setString(4, obj.getTelefono());
            ps.setString(5, obj.getCorreo());
            ps.setString(6, obj.getDireccion());
            ps.setInt(7, obj.getIdCategoria_());
            ps.setInt(8, obj.getEstado());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Proveedor> listaProveedorPorEmpresa(String empresa) {
        List<Proveedor> salida = new ArrayList<>();

        String sql = "SELECT p.idProveedor_, p.ruc, p.empresa, p.contacto, p.telefono, "
                + "p.correo, p.direccion, p.idCategoria_, "
                + "c.descripcion AS categoriaDescripcion, p.estado "
                + "FROM proveedor_ p "
                + "INNER JOIN categoria_ c ON p.idCategoria_ = c.idCategoria_ "
                + "WHERE p.empresa LIKE ? "
                + "ORDER BY p.empresa";

        Connection conn = MySqlDBConexion.getConexion();
        if (conn == null) {
            return salida;
        }

        try (conn;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = empresa == null ? "" : empresa.trim();
            ps.setString(1, "%" + filtro + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proveedor obj = new Proveedor();
                    obj.setIdProveedor_(rs.getInt("idProveedor_"));
                    obj.setRuc(rs.getString("ruc"));
                    obj.setEmpresa(rs.getString("empresa"));
                    obj.setContacto(rs.getString("contacto"));
                    obj.setTelefono(rs.getString("telefono"));
                    obj.setCorreo(rs.getString("correo"));
                    obj.setDireccion(rs.getString("direccion"));
                    obj.setIdCategoria_(rs.getInt("idCategoria_"));
                    obj.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
                    obj.setEstado(rs.getInt("estado"));
                    salida.add(obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return salida;
    }
}
