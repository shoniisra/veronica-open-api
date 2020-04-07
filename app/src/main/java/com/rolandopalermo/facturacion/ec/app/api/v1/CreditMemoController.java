package com.rolandopalermo.facturacion.ec.app.api.v1;

import com.rolandopalermo.facturacion.ec.domain.CreditMemo;
import com.rolandopalermo.facturacion.ec.dto.v1.cm.NotaCreditoDTO;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.NotaCredito;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rolandopalermo.facturacion.ec.app.common.Constants.URI_API_V1_CM;
import static com.rolandopalermo.facturacion.ec.common.Constants.API_DOC_ANEXO_1;

@RestController
@RequestMapping(value = {URI_API_V1_CM})
@Api(value="Notas de Crédito",description = "Gestiona el ciclo de vida de una nota de crédito electrónica")
public class CreditMemoController extends GenericSRIController<NotaCreditoDTO, NotaCredito, CreditMemo> {

    @ApiOperation(value = "Crea una nota de crédito electrónica y la almacena en base de datos")
    public ResponseEntity<Object> createCm(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody NotaCreditoDTO notaCreditoDTO) {
        return super.create(notaCreditoDTO);
    }

    @ApiOperation(value = "Envía una nota de crédito electrónica al SRI y actualiza su estado en base de datos")
    public ResponseEntity<Object> postCm(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.post(claveAcceso);
    }

    @ApiOperation(value = "Autoriza una nota de crédito electrónica y actualiza su estado en base de datos")
    public ResponseEntity<Object> applyCm(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.apply(claveAcceso);
    }

    @ApiOperation(value = "Elimina una nota de crédito de la base de datos")
    public ResponseEntity<Object> deleteInvoice(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.delete(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación PDF de una nota de crédito electrónica")
    public ResponseEntity<Object> generateRIDE(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.generateRIDE(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación XML de una nota de crédito electrónica")
    public ResponseEntity<Object> getXML(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.getXML(claveAcceso);
    }

}