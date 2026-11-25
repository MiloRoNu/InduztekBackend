package com.example.Induztek.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.Induztek.dto.ApiErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

        
    // Errores de validación @Valid en el @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        ApiErrorResponseDTO body = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Uno o más campos tienen errores de validación",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.badRequest().body(body);
    }

    // Errores de validación en @RequestParam o @PathVariable
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(v ->
                fieldErrors.put(v.getPropertyPath().toString(), v.getMessage())
        );

        ApiErrorResponseDTO body = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Constraint Violation",
                "Parámetros de solicitud inválidos",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.badRequest().body(body);
    }

    // Errores de negocio lanzados desde los servicios (GPS > 100m, login fallido, no encontrado, etc.)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ApiErrorResponseDTO body = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getReason() != null ? ex.getReason() : "Error en la solicitud",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(status).body(body);
    }

    // JSON mal formado o enum inválido (ej. RolUsuario, EstadoComponente, VeredictoPrueba)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleUnreadableMessage(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ApiErrorResponseDTO body = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON",
                "El cuerpo de la solicitud JSON tiene un formato o valor no válido",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.badRequest().body(body);
    }

    // Parámetro obligatorio faltante (?clientId=, ?facilityId=, etc.)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleMissingParam(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        ApiErrorResponseDTO body = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Missing Parameter",
                "Falta el parámetro obligatorio: " + ex.getParameterName(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.badRequest().body(body);
    }

    // Conflictos de base de datos (claves únicas o referencias activas al eliminar)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ApiErrorResponseDTO body = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Data Integrity Violation",
                "No se pudo completar la operación por un conflicto de integridad o registros asociados",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // Fallback para cualquier excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseDTO> handleGenericException(
                Exception ex,
                HttpServletRequest request) {

        log.error("Error no controlado en {} {}", request.getMethod(), request.getRequestURI(), ex);

        ApiErrorResponseDTO body = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocurrió un error interno inesperado en el servidor",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
}