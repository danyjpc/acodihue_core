## STAGE 1: Build ###
FROM maven:3.6.3-jdk-11 AS compile-image
LABEL maintainer="People Apps"
#
#add entrycode
COPY . /webapp/
WORKDIR /webapp
#
RUN mvn clean package

 #STAGE 2: Setup ###
##
FROM payara/micro

WORKDIR /opt/payara/documents

#copying template files
ADD ./template/plantilla_preinscripcion.docx $PAYARA_HOME/plantilla_preinscripcion.docx
ADD ./template/plantilla_cotizacion.docx $PAYARA_HOME/plantilla_cotizacion.docx
ADD ./template/plantilla_credito_requerimientos_tabla.docx $PAYARA_HOME/plantilla_credito_requerimientos_tabla.docx
ADD ./template/plantilla_solicitud_credito.docx $PAYARA_HOME/plantilla_solicitud_credito.docx
ADD ./template/plantilla_expediente_credito.docx $PAYARA_HOME/plantilla_expediente_credito.docx
ADD ./template/plantilla_ingresos_egresos.docx $PAYARA_HOME/plantilla_ingresos_egresos.docx
ADD ./template/plantilla_estado_patrimonial.docx $PAYARA_HOME/plantilla_estado_patrimonial.docx
ADD ./template/plantilla_resolucion_rl.docx $PAYARA_HOME/plantilla_resolucion_rl.docx
ADD ./template/plantilla_resolucion_comitec.docx $PAYARA_HOME/plantilla_resolucion_comitec.docx
ADD ./template/plantilla_autoavaluo.docx $PAYARA_HOME/plantilla_autoavaluo.docx
ADD ./template/plantilla_reconocimiento_deuda_uno_no_firma.docx $PAYARA_HOME/plantilla_reconocimiento_deuda_uno_no_firma.docx

COPY --from=compile-image /webapp/target/acodihue-core.war $DEPLOY_DIR
#COPY ./target/acodihue-core.war $DEPLOY_DIR
ENTRYPOINT [  "java", "-jar", "/opt/payara/payara-micro.jar","--nocluster","--logo", "--deploy","/opt/payara/deployments/acodihue-core.war"]

EXPOSE 8080