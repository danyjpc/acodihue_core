package com.peopleapps.util;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public class CsnConstants {

    public static final Boolean PRODUCTION = true;
    public static final String ONLY_DATE_EMPTY = "1900-01-01";
    public static final String DATE_EMPTY = "1900-01-01 00:00:00";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ONLY_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ONLY_TIME_FORMAT = "HH:mm:ss";
    public static final char SEPARATOR = ';';
    public static final char QUOTE = '"';

    public static final String FILES_DIRECTORY = "files";
    public static final String CSV_DIRECTORY = "csv";
    public static final String IMAGES_DIRECTORY = "images";
    public static final String PDF_DIRECTORY = "pdf";
    public static final String EXCEL_DIRECTORY = "excel";

    public static final Long TYPOLOGY_EMPTY = 160000L;
    public static final Long TYPOLOGY_GENERAL = 100L;

    public static final Long STATUS_ACTIVE = 160445L;
    public static final Long STATUS_INACTIVE = 160447L;

    //Constant used to represent default text value as 'S/D'
    public static final String DEFAULT_TEXT_SD = "S/D";
    //Constant used to represent default text value as '@'
    public static final String DEFAULT_TEXT_AT = "@";

    public static final String MEDIA_TYPE = "application/json";
    public static final Long ZERO = 0L;
    public static final Double ZERO_DOUBLE = 0.00;
    public static final Long DEFAULT_USER_ID = 1L;
    public static final Long DEFAULT_GUARANTEE_INITIAL = 1L;
    public static final Long DEFAULT_COUNTRY_ID = 160060L; //Guatemala country typology
    public static final Long DEFAULT_STATE_ID = 160073L; //Huehuetenango state typology
    public static final Long DEFAULT_CITY_ID = 160278L; //Huehuetenango city typology

    //Phone constants
    public static final Long CELL_PHONE_ID = 160538L;
    public static final Long HOME_PHONE_ID = 160540L;

    //Address type IDS
    public static final Long ADDRESS_TYPE_HOME = 160477L;
    public static final Long ADDRESS_TYPE_OFFICE = 160479L;
    public static final Long ADDRESS_TYPE_WORK = 160478L;

    //Preinscripcion status
    public static final Long PREINSCRIPTION_PENDING = 160561L;
    public static final Long PREINSCRIPTION_AUTHORIZED = 160562L;
    public static final Long PREINSCRIPTION_PROCESSING = 160563L;


    public static final UUID ACODIHUE_KEY = UUID.fromString("3438d2ba-a56a-484c-bc2e-ea5b5dfcd488");


    public static final String ROOT_DIRECTORY = "/";
    public static final String DIRECTORY_ASSOCIATE_DOC = "/associate";

    public static final String PREINSCRIPCION_PLANTILLA = "/plantilla_preinscripcion.docx";

    public static final String COTIZACION_PLANTILLA = "/plantilla_cotizacion.docx";

    public static final String CREDITO_REQUERIMIENTO_PLANTILLA = "/plantilla_credito_requerimientos_tabla.docx";
    public static final String SOLICITUD_CREDITO = "/plantilla_solicitud_credito.docx";
    public static final String EXPENDIENTE_CREDITO = "/plantilla_expediente_credito.docx";
    public static final String INGRESOS_EGRESOS_PLANTILLA = "/plantilla_ingresos_egresos.docx";
    public static final String ESTADO_PATRIMONIAL_PLANTILLA = "/plantilla_estado_patrimonial.docx";
    public static final String RESOLUCION_RL_PLANTILLA = "/plantilla_resolucion_rl.docx";
    public static final String RESOLUCION_COMITE_CREDITO = "/plantilla_resolucion_comitec.docx";
    public static final String AUTOAVALUO_PLANTILLA = "/plantilla_autoavaluo.docx";
    public static final String RECONOCIMIENTO_DEUDA_UNO_NO_FIRMA = "/plantilla_reconocimiento_deuda_uno_no_firma.docx";

    public static final Long DOCUMENT_TYPE = 160572l;


    //Account types
    //Account type typology
    public static final Long ACCOUNT_TYPE_TYPOLOGY= 160577L;
    //ahorro regular
    public static final Long REGULAR_SAVINGS_ACCOUNT = 160578L;
    //aportacion
    public static final Long CONTRIBUTION_ACCOUNT = 160579L;
    //plazo fijo
    public static final Long FIXED_RATE_SAVINGS_ACCOUNT = 160601L;

    //Account orders
    public static final Long SAVINGS_ORDER = 11L;
    public static final Long CONTRIBUTION_ORDER = 10L;

    //Debe types
    public static final Long WITHDRAW = 160581L;
    public static final Long CREDIT_NOTE = 160582L;

    //Haber types
    public static final Long INITIAL_FEE = 160584L;
    public static final Long INSCRIPTION_FEE = 160585L;
    public static final Long EXTRAORDINARY_CONTRIBUTION = 160586L;

    //Fee amounts
    public static final Double INSCRIPTION_AMOUNT = 50.00;
    public static final Double CONTRIBUTION_FEE = 250.00;

    //Transaction concepts (annotations)
    public static final String INSCRIPTION_FEE_ANNOTATION = "Cuota de inscripcion";
    public static final String INITIAL_FEE_ANNOTATION = "Cuota de aportacion";
    public static final String EXTRAORDINARY_CONTRIBUTION_ANNOTATION = "Aporte extraordinario";

    //Debit and Credit
    public static final Long DEBIT_TYPOLOGY = 160580L;
    public static final Long CREDIT_TYPOLOGY = 160583L;

    //Calculation Rounding mode
    public static final RoundingMode calculationRoundMode = RoundingMode.HALF_DOWN;

    //Calculation Rounding mode
    public static final RoundingMode resultRoundMode = RoundingMode.HALF_DOWN;

    //Calculation Rounding scale
    public static final int calculationScale = 10;

    //Display results Rounding scale
    public static final int displayScale = 2;

    //Excel reports
    public static final String SHEET_NAME = "Hoja1";

    //Default credit line ID
    public static final Long CREDIT_LINE_AGRICOLA = 1L;

    //For accounting
    //Account type (activo, pasivo)
    public static final Long ASSET_TYPE = 160684L; //ACTIVO
    public static final Long LIABILITY_TYPE = 160685L; //PASIVO

    //Account type (ingreso, gasto)
    public static final Long INCOME_TYPE = 160700L; //INGRESO
    public static final Long EXPENSE_TYPE = 160701L; //GASTO

}
