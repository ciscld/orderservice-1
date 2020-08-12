# IBM Java SDK UBI is not available on public docker yet. Use regular
# base as builder until this is ready. For reference:
# https://github.com/ibmruntimes/ci.docker/tree/master/ibmjava/8/sdk/ubi-min

FROM ibmjava:8-sdk AS builder
LABEL maintainer="IBM Java Engineering at IBM Cloud"


#
# H2 Dockerfile
#

# Pull base image.
#FROM dockerfile/java

# Install H2
RUN \
  cd /tmp && \
  wget http://www.h2database.com/h2-2014-04-05.zip && \
  unzip h2-2014-04-05.zip && \
  rm -f h2-2014-04-05.zip && \
  mkdir -p /opt/h2 && \
  mv /tmp/h2 /opt && \
  mkdir -p /opt/h2-data

# Expose ports.
#   - 1521: H2 Server
#   -   81: H2 Console
EXPOSE 1521 81

# Define default command
CMD java -cp /opt/h2/bin/h2*.jar org.h2.tools.Server -web -webAllowOthers -webPort 81 -tcp -tcpAllowOthers -tcpPort 1521 -baseDir /opt/h2-data



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
