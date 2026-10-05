package controller;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;

import entity.Proveedor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProveedorModel;

@WebServlet("/registraProveedor")
public class RegistraProveedorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String ruc = req.getParameter("ruc");
            String empresa = req.getParameter("empresa");
            String contacto = req.getParameter("contacto");
            String telefono = req.getParameter("telefono");
            String correo = req.getParameter("correo");
            String direccion = req.getParameter("direccion");
            String idCategoria = req.getParameter("idCategoria_");
            String estado = req.getParameter("estado");

            if (ruc == null || empresa == null || contacto == null || telefono == null
                    || correo == null || direccion == null || idCategoria == null || estado == null
                    || ruc.trim().isEmpty() || empresa.trim().isEmpty() || contacto.trim().isEmpty()
                    || telefono.trim().isEmpty() || correo.trim().isEmpty() || direccion.trim().isEmpty()) {
                responder(resp, HttpServletResponse.SC_BAD_REQUEST, "Complete todos los campos");
                return;
            }

            if (!ruc.trim().matches("\\d{11}")) {
                responder(resp, HttpServletResponse.SC_BAD_REQUEST, "El RUC debe tener 11 dígitos");
                return;
            }

            Proveedor proveedor = new Proveedor();
            proveedor.setRuc(ruc.trim());
            proveedor.setEmpresa(empresa.trim());
            proveedor.setContacto(contacto.trim());
            proveedor.setTelefono(telefono.trim());
            proveedor.setCorreo(correo.trim());
            proveedor.setDireccion(direccion.trim());
            proveedor.setIdCategoria_(Integer.parseInt(idCategoria));
            proveedor.setEstado(Integer.parseInt(estado));

            if (proveedor.getEstado() != 0 && proveedor.getEstado() != 1) {
                responder(resp, HttpServletResponse.SC_BAD_REQUEST, "El estado no es válido");
                return;
            }

            int salida = new ProveedorModel().insertaProveedor(proveedor);
            if (salida > 0) {
                responder(resp, HttpServletResponse.SC_OK, "Proveedor registrado correctamente");
            } else {
                responder(resp, HttpServletResponse.SC_BAD_REQUEST, "No se pudo registrar el proveedor");
            }

        } catch (NumberFormatException e) {
            responder(resp, HttpServletResponse.SC_BAD_REQUEST, "Seleccione una categoría y un estado válidos");
        }
    }

    private void responder(HttpServletResponse resp, int status, String mensaje) throws IOException {
        resp.setStatus(status);
        resp.getWriter().write(new Gson().toJson(Map.of("mensajeSalida", mensaje)));
    }
}
