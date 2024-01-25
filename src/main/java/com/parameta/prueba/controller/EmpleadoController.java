package com.parameta.prueba.controller;

import com.parameta.prueba.decorator.EdadActualDecorator;
import com.parameta.prueba.decorator.TiempoVinculacionDecorator;
import com.parameta.prueba.dto.EmpleadoResponseDTO;
import com.parameta.prueba.entity.Empleado;
import com.parameta.prueba.exception.ValidationException;
import com.parameta.prueba.mapper.EmpleadoMapper;
import com.parameta.prueba.util.Constant;
import com.parameta.prueba.util.DateUtil;
import com.parameta.prueba.validator.EmpleadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoController {

    private final EmpleadoMapper empleadoMapper;

    @Autowired
    public EmpleadoController(EmpleadoMapper empleadoMapper) {
        this.empleadoMapper = empleadoMapper;
    }

    @GetMapping("/api/empleado")
    public ResponseEntity<EmpleadoResponseDTO> obtenerEmpleado(@RequestParam String nombres, String apellidos,
                                                               String tipoDocumento, String numeroDocumento,
                                                               String fechaNacimiento,
                                                               String fechaVinculacion,
                                                               String cargo, Double salario) {

        Empleado empleado = empleadoMapper.mapToEmpleado(nombres, apellidos, tipoDocumento, numeroDocumento,
                DateUtil.stringToLocalDate(fechaNacimiento, Constant.FECHA_DE_NACIMIENTO),
                DateUtil.stringToLocalDate(fechaVinculacion, Constant.FECHA_DE_VINCULACION), cargo, salario);

        EmpleadoValidator empleadoValidator = new EmpleadoValidator();
        try {
            empleadoValidator.validar(empleado);
            //TODO llamada al SOAP
            EmpleadoResponseDTO responseDTO = empleadoMapper.mapToEmpleadoResponseDTO(empleado);

            responseDTO = new TiempoVinculacionDecorator().decorar(responseDTO);
            responseDTO = new EdadActualDecorator().decorar(responseDTO);
            responseDTO.setMessage(Constant.OK_200);
            return ResponseEntity.ok(responseDTO);
        } catch (ValidationException e) {
            EmpleadoResponseDTO errorResponse = new EmpleadoResponseDTO();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
