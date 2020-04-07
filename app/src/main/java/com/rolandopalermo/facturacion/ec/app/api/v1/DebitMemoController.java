package com.rolandopalermo.facturacion.ec.app.api.v1;

import com.rolandopalermo.facturacion.ec.domain.DebitMemo;
import com.rolandopalermo.facturacion.ec.dto.v1.dm.NotaDebitoDTO;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.NotaDebito;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rolandopalermo.facturacion.ec.app.common.Constants.URI_API_V1_DM;
import static com.rolandopalermo.facturacion.ec.common.Constants.API_DOC_ANEXO_1;

@RestController
@RequestMapping(value = {URI_API_V1_DM})
@Api(value="Notas de Débito",description = "Gestiona el ciclo de vida de una nota de débito electrónica")
public class DebitMemoController extends GenericSRIController<NotaDebitoDTO, NotaDebito, DebitMemo> {

    @ApiOperation(value = "Crea una nota de débito electrónica y la almacena en base de datos")
    public ResponseEntity<Object> createCm(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody NotaDebitoDTO notaDebitoDTO) {
        return super.create(notaDebitoDTO);
    }

    @ApiOperation(value = "Envía una nota de débito electrónica al SRI y actualiza su estado en base de datos")
    public ResponseEntity<Object> postDm(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.post(claveAcceso);
    }

    @ApiOperation(value = "Autoriza una nota de débito electrónica y actualiza su estado en base de datos")
    public ResponseEntity<Object> applyDm(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.apply(claveAcceso);
    }

    @ApiOperation(value = "Elimina una nota de débito de la base de datos")
    public ResponseEntity<Object> deleteInvoice(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.delete(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación PDF de una nota de débito electrónica")
    public ResponseEntity<Object> generateRIDE(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.generateRIDE(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación XML de una nota de débito electrónica")
    public ResponseEntity<Object> getXML(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.getXML(claveAcceso);
    }

}