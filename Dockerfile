# IBM Java SDK UBI is not available on public docker yet. Use regular
# base as builder until this is ready. For reference:
# https://github.com/ibmruntimes/ci.docker/tree/master/ibmjava/8/sdk/ubi-min

FROM ibmjava:8-sdk AS builder
LABEL maintainer="IBM Java Engineering at IBM Cloud"

# Port to expose
EXPOSE 8082
EXPOSE 9082

# Volume containing the H2 data
VOLUME /usr/lib/h2

# H2 version
ENV H2_VERSION "1.4.197"

# Download
COPY h2-1.4.197.jar /var/lib/h2/h2.jar
#ADD "https://repo1.maven.org/maven2/com/h2database/h2/${H2_VERSION}/h2-${H2_VERSION}.jar" /var/lib/h2/h2.jar

# Startup script
COPY h2.sh /var/lib/h2/

# Rights
RUN chmod u+x /var/lib/h2/h2.sh

# Java options
ENV JAVA_OPTIONS ""

# Additional H2 options
ENV H2_OPTIONS ""

RUN /var/lib/h2/h2.sh

#VOLUME /tmp
WORKDIR /app
RUN apt-get update && apt-get install -y maven

COPY pom.xml .
RUN mvn -N io.takari:maven:wrapper -Dmaven=3.5.0

COPY . /app
RUN ./mvnw install

ARG bx_dev_user=root
ARG bx_dev_userid=1000
RUN BX_DEV_USER=$bx_dev_user
RUN BX_DEV_USERID=$bx_dev_userid
RUN if [ $bx_dev_user != "root" ]; then useradd -ms /bin/bash -u $bx_dev_userid $bx_dev_user; fi

FROM adoptopenjdk/openjdk8:ubi-jre
#ADD orderdb.h2.db  /tmp/orderdb.h2.db

# Copy over app from builder image into the runtime image.
RUN mkdir /opt/app
COPY --from=builder /app/target/order-service-1.0-SNAPSHOT.jar /opt/app/app.jar

ENTRYPOINT [ "sh", "-c", "java -jar /opt/app/app.jar" ]
