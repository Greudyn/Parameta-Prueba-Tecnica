package com.parameta.prueba.dao;

import com.parameta.prueba.entity.Empleado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpleadoDAO {

    public void insertarEmpleado(Empleado empleado) {
        try (Connection connection = soapConnection()) {
            String sql = "INSERT INTO empleado (nombres, apellidos,...) VALUES (?, ?,...)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, empleado.getNombres());
                //..
                preparedStatement.setDate(2, Date.valueOf(empleado.getFechaNacimiento()));
                // ...
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // ...
        }
    }

    private Connection soapConnection() throws SQLException {
        // TODO
        return null;
    }
}
