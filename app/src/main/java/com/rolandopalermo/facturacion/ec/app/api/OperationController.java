package com.rolandopalermo.facturacion.ec.app.api;

import com.rolandopalermo.facturacion.ec.domain.PaymentMethod;
import com.rolandopalermo.facturacion.ec.domain.ReceiptType;
import com.rolandopalermo.facturacion.ec.domain.Supplier;
import com.rolandopalermo.facturacion.ec.domain.TaxType;
import com.rolandopalermo.facturacion.ec.domain.User;
import com.rolandopalermo.facturacion.ec.dto.CertificadoDigitalDTO;
import com.rolandopalermo.facturacion.ec.dto.EmpresaDTO;
import com.rolandopalermo.facturacion.ec.dto.MetodoPagoDTO;
import com.rolandopalermo.facturacion.ec.dto.PasswordDTO;
import com.rolandopalermo.facturacion.ec.dto.TipoDocumentoDTO;
import com.rolandopalermo.facturacion.ec.dto.TipoImpuestoDTO;
import com.rolandopalermo.facturacion.ec.dto.UsuarioDTO;
import com.rolandopalermo.facturacion.ec.dto.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.service.DigitalCertService;
import com.rolandopalermo.facturacion.ec.service.crud.GenericCRUDService;
import com.rolandopalermo.facturacion.ec.service.crud.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.rolandopalermo.facturacion.ec.app.common.Constants.URI_OPERATIONS;
import static com.rolandopalermo.facturacion.ec.common.Constants.API_DOC_ANEXO_1;
import static com.rolandopalermo.facturacion.ec.common.Constants.SWAGGER_EMISOR;

@RestController
@RequestMapping(value = {URI_OPERATIONS})
@Api(tags="Operaciones",value="Operaciones",description = "Realiza operaciones generales sobre Verónica")
public class OperationController {

    @Autowired
    @Qualifier("digitalCertServiceImpl")
    private DigitalCertService digitalCertService;

    @Autowired
    @Qualifier("paymentMethodServiceImpl")
    private GenericCRUDService<PaymentMethod, MetodoPagoDTO> paymentMethodService;

    @Autowired
    @Qualifier("taxTypeServiceImpl")
    private GenericCRUDService<TaxType, TipoImpuestoDTO> taxTypeService;

    @Autowired
    @Qualifier("receiptTypeServiceImpl")
    private GenericCRUDService<ReceiptType, TipoDocumentoDTO> receiptTypeService;

    @Autowired
    @Qualifier("userServiceImpl")
    private GenericCRUDService<User, UsuarioDTO> userService;

    @Autowired
    @Qualifier("supplierServiceImpl")
    private GenericCRUDService<Supplier, EmpresaDTO> supplierService;

    @ApiOperation(value = "Almacena un certificado digital asociado a número de RUC")
    @PostMapping(value = "certificados-digitales/empresas/{numeroRuc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertDigitalCert(
            @ApiParam(value = SWAGGER_EMISOR, required = true) @PathVariable("numeroRuc") String numeroRuc,
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody CertificadoDigitalDTO certificadoDigital) {
        digitalCertService.save(numeroRuc, certificadoDigital);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Crea un nuevo tipo de impuesto")
    @PostMapping(value = "tipos-impuesto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertTaxType(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody TipoImpuestoDTO tipoImpuestoDTO) {
        taxTypeService.saveOrUpdate(tipoImpuestoDTO);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Crea un nuevo tipo de documento")
    @PostMapping(value = "tipos-documento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertReceiptType(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody TipoDocumentoDTO tipoDocumentoRetenidoDTO) {
        receiptTypeService.saveOrUpdate(tipoDocumentoRetenidoDTO);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Crea un nuevo método de pago")
    @PostMapping(value = "metodos-pago", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertPaymentMethod(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody MetodoPagoDTO metodoPagoDTO) {
        paymentMethodService.saveOrUpdate(metodoPagoDTO);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Crea un nuevo usuario de la aplicación")
    @PostMapping(value = "usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertUser(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody UsuarioDTO usuarioDTO) {
        userService.saveOrUpdate(usuarioDTO);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualiza la contraseña de un usuario de la aplicación")
    @PutMapping(value = "usuarios/{usuario}/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePassword(
            @Valid @ApiParam(value = "Nombre de usuario", required = true) @PathVariable("usuario") String usuario,
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody PasswordDTO passwordDTO) {
        ((UserServiceImpl) userService).updatePassword(usuario, passwordDTO);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Registra datos de una nueva empresa")
    @PostMapping(value = "empresa", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertSupplier(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody EmpresaDTO empresaDTO) {
        supplierService.saveOrUpdate(empresaDTO);
        return new ResponseEntity<>(new VeronicaResponseDTO<>(true, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retorna la información asociada a una empresa")
    @GetMapping(value = "empresa/{numeroIdentificacion}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getBolsBySupplier(
            @Valid @ApiParam(value = "Número de identificación de la empresa", required = true) @PathVariable("numeroIdentificacion") String numeroIdentificacion) {
        EmpresaDTO empresa = new EmpresaDTO();
        empresa.setRucPropietario(numeroIdentificacion);
        List<EmpresaDTO> listSuppliers = supplierService.findAll(empresa);
        if (!StringUtils.isEmpty(listSuppliers)) {
            VeronicaResponseDTO<List<EmpresaDTO>> response = new VeronicaResponseDTO<>(true, listSuppliers);
            return (new ResponseEntity<Object>(response, HttpStatus.OK));
        } else {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

}