package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Categoria;
import util.MySqlDBConexion;

public class CategoriaModel {
    public List<Categoria> listaCategoria() {
        List<Categoria> salida = new ArrayList<>();
        String sql = "SELECT idCategoria_, descripcion FROM categoria_ ORDER BY descripcion";
        try (Connection conn = MySqlDBConexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria_(rs.getInt("idCategoria_"));
                categoria.setDescripcion(rs.getString("descripcion"));
                salida.add(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }
}
