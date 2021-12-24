package com.peopleapps.util;

import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.model.AdmTypology;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.mail.Part;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class CsnFunctions {

    public static Long getYear() {
        return (long) LocalDateTime.now().getYear();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now().plusHours(-6);

    }

    public static String dateForDirectories() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        return dateFormat.format(date);
    }

    /**
     * This method creates custom javax.ws.rs.core.Response objects
     * using the received parameters
     *
     * @param objectId Id from persisted object.
     * @param status   Desired Status result for constructing the Response.
     * @param message  Custom Message provided by the developer.
     * @return javax.ws.rs.core.Response This returns the custom Response object.
     */
    public static Response setResponse(Long objectId, Response.Status status, String message) {
        Jsonb jsonb = JsonbBuilder.create();
        ResponseJson responseJson = new ResponseJson();
        //Send null if Id not available
        if (!(objectId == null)) {
            responseJson.setId(objectId);
        }
        responseJson.setCode(String.valueOf(status.getStatusCode()));
        //Send null if not an error message
        if (message == null) {
            responseJson.setMsg(status.getReasonPhrase());
        } else {
            responseJson.setMsg(status.getReasonPhrase() + ", " + message);
        }
        return Response.status(status)
                .entity(jsonb.toJson(responseJson)).build();
    }

    public static Response setResponse(UUID key, Response.Status status, String message) {
        Jsonb jsonb = JsonbBuilder.create();
        ResponseJson responseJson = new ResponseJson();
        //Send null if Id not available
        if (!(key == null)) {
            responseJson.setKey(key);
        }
        responseJson.setCode(String.valueOf(status.getStatusCode()));
        //Send null if not an error message
        if (message == null) {
            responseJson.setMsg(status.getReasonPhrase());
        } else {
            responseJson.setMsg(status.getReasonPhrase() + ", " + message);
        }
        return Response.status(status)
                .entity(jsonb.toJson(responseJson)).build();
    }

    public static UUID generateKey() {

        return UUID.randomUUID();
    }

    /**
     * @param file             input stream file to save using streaming
     * @param fileName         name of file
     * @param subDirectoryName name of directory to save file  example :  /persons
     * @param subDirectoryKey  name additional of directory to save file example:  /persons/0000-000-000-00/
     * @return
     */
    public static String saveFile(
            InputStream file,
            String fileName,
            String subDirectoryName,
            String subDirectoryKey
    ) {

        try {

            File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

            System.out.println("D===========" + rootDirectory.getPath());

            /*This is the principal directory , normally is  ./ in the docker is payara-documents  */
            if (!rootDirectory.exists())
                rootDirectory.mkdir();

            /*This is the principal name directory for example  associate, organizations etc  */
            File subDirectory = new File(rootDirectory.getAbsolutePath() + "/" + subDirectoryName);
            if (!subDirectory.exists())
                subDirectory.mkdir();

            System.out.println("E===========" + subDirectory.getPath());

            /*This is de second directory for example the key  0000-00-00-0000 from a associate etc */
            File keyDirectory = new File(subDirectory.getAbsolutePath() + "/" + subDirectoryKey);
            if (!keyDirectory.exists())
                keyDirectory.mkdir();

            System.out.println("F===========" + keyDirectory.getPath());

            /*adn this is the actual date */
            File directoryDate = new File(keyDirectory.getAbsolutePath() + "/" + dateForDirectories());
            if (!directoryDate.exists())
                directoryDate.mkdir();

            String path = directoryDate.getAbsolutePath() + "/" + fileName;

            System.out.println("G===========" + path);

            System.out.println("H===========" + fileName);

            FileOutputStream outputStream = new FileOutputStream(path);


            byte[] buf = new byte[1024];
            int read = 0;

            while ((read = file.read(buf)) != -1) {
                outputStream.write(buf, 0, read);
            }

            file.close();
            outputStream.flush();
            outputStream.close();

            return path;

        } catch (IOException e) {

            System.out.println(e.getMessage());

            return null;
        }
    }

    public static String getAccountNumberOrder(Long accountNumber) {
        String account = accountNumber.toString();
        String[] tokens = account.split("");
        StringBuilder formato = new StringBuilder();
        //actualmente el numero de digitos para cuenta es 6
        Integer digitosCuenta = 6;
        for (int i = 0; i < (digitosCuenta - tokens.length); i++) {
            formato.append("0");
        }

        formato.append(accountNumber);

        return formato.toString();
    }

    public static String prefixZeroes(Long number, Long figureSize) {
        String formatedString = number.toString();
        String[] tokens = formatedString.split("");
        StringBuilder formato = new StringBuilder();
        //actualmente el numero de digitos para cuenta es 6
        for (int i = 0; i < (figureSize - tokens.length); i++) {
            formato.append("0");
        }

        formato.append(formatedString);

        return formato.toString();
    }

    public static Long removeLeadingZeroes(String value) {
        String[] tokens = value.split("");
        StringBuilder formato = new StringBuilder();
        //recorre el arreglo e ignora los 0
        for (String token : tokens) {
            if (!token.equals("0")) {
                formato.append(token);
            }
        }
        Long resultado = Long.parseLong(formato.toString());
        return resultado;
    }


    public static String appendOrder(String formato, Long accountOrder) {
        StringBuilder resultado = new StringBuilder(formato);
        resultado.append("-").append(accountOrder);

        return resultado.toString();
    }


    //Function to round decimals so it shows 2 decimals format
    public static BigDecimal redondearBigDecimal(BigDecimal numero, int decimales) {
        if (decimales < 0) throw new IllegalArgumentException();
        BigDecimal redondeado = new BigDecimal(numero.toString()).setScale(decimales, CsnConstants.resultRoundMode);
        return redondeado;
    }

    //Function to calculate credit internal number
    //Takes agency code and previous credit internal number with format C-0001-2021
    public static String generateCreditInternalCode(String agencyCode, String previousCode) {
        try {

            if (previousCode == null) {
                StringBuilder respuesta = new StringBuilder();
                respuesta.append(agencyCode).append("-").append("0001").append("-");
                respuesta.append(CsnFunctions.getYear());
                return respuesta.toString();
            }

            String[] tokens = previousCode.split("-");
            if (tokens.length < 3) {
                throw new Exception("Incorrect internal code received");
            }
            //index 0 agency code
            //index 1 last code
            //index 2 year digits

            Long number = Long.parseLong(tokens[1]);
            //check if year is diferent
            Long year = Long.parseLong(tokens[2]);
            System.out.println("year: " + year);

            Boolean restartNumber = false;
            if (!CsnFunctions.getYear().equals(year)) {
                restartNumber = true;
            }
            //if year is different, restart numeration, else add 1
            if (restartNumber) {
                number = 1L;
            } else {
                number += 1L;
            }

            System.out.println("restartNumber: " + restartNumber);
            //add leading zeroes
            StringBuilder respuesta = new StringBuilder();
            respuesta.append(agencyCode);
            respuesta.append("-");
            //currently max number for internal code is 9999 thus 4 digits
            Integer numberDigits = 4;
            for (int i = 0; i < (numberDigits - number.toString().length()); i++) {
                respuesta.append("0");
            }
            respuesta.append(number);
            respuesta.append("-");
            respuesta.append(CsnFunctions.getYear());

            return respuesta.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }


}
