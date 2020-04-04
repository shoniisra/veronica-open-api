package com.rolandopalermo.facturacion.ec.app.common;

public class Constants {

    private Constants() {
    }

    public static final long CREATED = 1;
    public static final long POSTED = 2;
    public static final long APPLIED = 3;
    public static final long REJECTED = 4;
    public static final long INVALID = 5;

    public static final String SRI_REJECTED = "DEVUELTA";
    public static final String SRI_RECEIVED = "RECIBIDA";

    public static final String SRI_APPLIED = "AUTORIZADO";
    public static final String SRI_INVALID = "NO AUTORIZADO";

    public static final String XNML_DEBUG_DELIMETER = "--------------------------------[ XML ]---------------------------------";

    public static final String URI_API_V1 = "/api/v1.0/";
    public static final String URI_API_V1_INVOICE = URI_API_V1 + "facturas";
    public static final String URI_API_V1_BOL = URI_API_V1 + "guias-remision";
    public static final String URI_API_V1_CM = URI_API_V1 + "notas-credito";
    public static final String URI_API_V1_DM = URI_API_V1 + "notas-debito";
    public static final String URI_API_V1_WH = URI_API_V1 + "retenciones";
    public static final String URI_API_V1_PC = URI_API_V1 + "liquidaciones-compra";
    public static final String URI_OPERATIONS = "/operaciones/";
    public static final String URI_PUBLIC = "/publico/";

}