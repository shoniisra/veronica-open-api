package com.rolandopalermo.facturacion.ec.app.api.v1;

import com.rolandopalermo.facturacion.ec.domain.BaseSRIEntity;
import com.rolandopalermo.facturacion.ec.dto.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.dto.v1.ComprobanteDTO;
import com.rolandopalermo.facturacion.ec.modelo.Comprobante;
import com.rolandopalermo.facturacion.ec.service.sri.GenericSRIService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;

public class GenericSRIController<DTO extends ComprobanteDTO, MODEL extends Comprobante, DOMAIN extends BaseSRIEntity> {

    @Autowired(required = true)
    private GenericSRIService<DTO, MODEL, DOMAIN> service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Llamada exitosa"),
            @ApiResponse(code = 201, message = "Creada"),
            @ApiResponse(code = 401, message = "No estas autorizado"),
            @ApiResponse(code = 403, message = "Acción No permitida"),
            @ApiResponse(code = 404, message = "Acción No encontrada"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody DTO dto) {
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, service.create(dto)), HttpStatus.CREATED);
    }

    @PutMapping(value = "{claveAcceso}/enviar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@Valid @PathVariable String claveAcceso) {
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, service.post(claveAcceso)), HttpStatus.OK);
    }

    @PutMapping(value = "{claveAcceso}/autorizar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> apply(@Valid @PathVariable String claveAcceso) {
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, service.apply(claveAcceso)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{claveAcceso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@Valid @PathVariable String claveAcceso) {
        service.delete(claveAcceso);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.OK);
    }

    @GetMapping(value = "{claveAcceso}/archivos/pdf")
    public ResponseEntity<Object> generateRIDE(@Valid @PathVariable("claveAcceso") String claveAcceso) {
        byte[] pdfContent = service.getPDF(claveAcceso);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline; filename=" + claveAcceso + ".pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(pdfContent.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));
    }

    @GetMapping(value = "{claveAcceso}/archivos/xml", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> getXML(@Valid @PathVariable("claveAcceso") String claveAcceso) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml; charset=UTF-8");
        return (new ResponseEntity<Object>(service.getXML(claveAcceso), headers, HttpStatus.OK));
    }

}