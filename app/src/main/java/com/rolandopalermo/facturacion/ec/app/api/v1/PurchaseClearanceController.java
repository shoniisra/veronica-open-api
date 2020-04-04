package com.rolandopalermo.facturacion.ec.app.api.v1;

import com.rolandopalermo.facturacion.ec.domain.PurchaseClearance;
import com.rolandopalermo.facturacion.ec.dto.v1.pc.LiquidacionCompraDTO;
import com.rolandopalermo.facturacion.ec.modelo.liquidacion.LiquidacionCompra;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rolandopalermo.facturacion.ec.app.common.Constants.URI_API_V1_PC;
import static com.rolandopalermo.facturacion.ec.common.Constants.API_DOC_ANEXO_1;

@RestController
@RequestMapping(value = {URI_API_V1_PC})
@Api(description = "Gestiona el ciclo de vida de una Liquidación de Compra")
public class PurchaseClearanceController extends GenericSRIController<LiquidacionCompraDTO, LiquidacionCompra, PurchaseClearance> {

    @ApiOperation(value = "Crea una Liquidación de Compra electrónica y la almacena en base de datos")
    public ResponseEntity<Object> createPc(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody LiquidacionCompraDTO liquidacionCompraDTO) {
        return super.create(liquidacionCompraDTO);
    }

    @ApiOperation(value = "Envía una Liquidación de Compra electrónica al SRI y actualiza su estado en base de datos")
    public ResponseEntity<Object> postPc(@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.post(claveAcceso);
    }

    @ApiOperation(value = "Autoriza una Liquidación de Compra electrónica y actualiza su estado en base de datos")
    public ResponseEntity<Object> applyPc(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.apply(claveAcceso);
    }

    @ApiOperation(value = "Elimina una Liquidación de Compra de la base de datos")
    public ResponseEntity<Object> deletePc(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.delete(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación PDF de una Liquidación de Compra electrónica")
    public ResponseEntity<Object> generateRIDE(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.generateRIDE(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación XML de una Liquidación de Compra electrónica")
    public ResponseEntity<Object> getXML(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.getXML(claveAcceso);
    }

}