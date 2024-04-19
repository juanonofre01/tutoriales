package servlets;

import com.umariana.conexion.gestionarTutoriales;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "svEditarTutorial", urlPatterns = {"/svEditarTutorial"})
public class svEditarTutorial extends HttpServlet {

    gestionarTutoriales gestionar = new gestionarTutoriales();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = gestionar.establecerConexion();

        if (conn != null) {
            try {
                // Obtener los datos del formulario de edición
                int idTutorial = Integer.parseInt(request.getParameter("idTutorial"));
                String nombre = request.getParameter("nombre");
                // Obtener los otros datos de edición aquí

                // Sentencia SQL para actualizar el tutorial
                String sql = "UPDATE tutorial SET nombre = ?, ... WHERE idTutorial = ?";

                // Preparar la declaración SQL
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nombre);
                // Establecer los otros parámetros de edición aquí
                pstmt.setInt(2, idTutorial);

                // Ejecutar la consulta
                pstmt.executeUpdate();

                // Cerrar la conexión
                conn.close();

                // Redireccionar al usuario de vuelta a la página index.jsp
                response.sendRedirect("index.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Error al editar tutorial: " + e.getMessage());
            }
        } else {
            response.getWriter().println("No se pudo establecer una conexión a la base de datos.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
