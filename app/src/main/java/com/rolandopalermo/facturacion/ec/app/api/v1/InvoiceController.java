package com.rolandopalermo.facturacion.ec.app.api.v1;

import com.rolandopalermo.facturacion.ec.domain.Invoice;
import com.rolandopalermo.facturacion.ec.dto.v1.invoice.FacturaDTO;
import com.rolandopalermo.facturacion.ec.modelo.factura.Factura;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rolandopalermo.facturacion.ec.app.common.Constants.URI_API_V1_INVOICE;
import static com.rolandopalermo.facturacion.ec.common.Constants.API_DOC_ANEXO_1;

@RestController
@RequestMapping(value = {URI_API_V1_INVOICE})
@Api(value="Facturas",description = "Gestiona el ciclo de vida de una factura electrónica")
public class InvoiceController extends GenericSRIController<FacturaDTO, Factura, Invoice> {

    @ApiOperation(value = "Crea una factura electrónica y la almacena en base de datos")
    public ResponseEntity<Object> createInvoice(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody FacturaDTO facturaDTO) {
        return super.create(facturaDTO);
    }

    @ApiOperation(value = "Envía una factura electrónica al SRI y actualiza su estado en base de datos")
    public ResponseEntity<Object> postInvoice(@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.post(claveAcceso);
    }

    @ApiOperation(value = "Autoriza una factura electrónica y actualiza su estado en base de datos")
    public ResponseEntity<Object> applyInvoice(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.apply(claveAcceso);
    }

    @ApiOperation(value = "Elimina una factura de la base de datos")
    public ResponseEntity<Object> deleteInvoice(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.delete(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación PDF de una factura electrónica")
    public ResponseEntity<Object> generateRIDE(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.generateRIDE(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación XML de una factura electrónica")
    public ResponseEntity<Object> getXML(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.getXML(claveAcceso);
    }

}