package com.peopleapps.util;
import java.util.ArrayList;
import java.util.List;
import com.peopleapps.dto.credit.AdmPathDocsCredit;

public class PathDocsCredit {
    public static List<AdmPathDocsCredit> getPaths(){

        List<AdmPathDocsCredit> listP = new ArrayList<>();

        listP.add(new AdmPathDocsCredit("EXPEDIENTE DE CREDITO", "expediente_credito"));
        listP.add(new AdmPathDocsCredit("SOLICITUD DE CREDITO", "solicitud_credito"));
        listP.add(new AdmPathDocsCredit("ESTADO PATRIMONIAL", "estado_patrimonial"));
        listP.add(new AdmPathDocsCredit("RESOLUCION COMITE DE CREDITO", "resolucion_cc"));
        listP.add(new AdmPathDocsCredit("RESOLUCION CONSEJO DE ADMINISTRACION", "resolucion_consejo_adm"));
        listP.add(new AdmPathDocsCredit("AUTOAVALUO", "autoavaluo"));

        return listP;
    }
}

