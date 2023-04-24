
FROM payara/server-full:6.2022.2-jdk17

COPY target/*.war $DEPLOY_DIR
COPY create-jms-queue.asadmin $CONFIG_DIR
ENV POSTBOOT_COMMANDS $CONFIG_DIR/create-jms-queue.asadmin
