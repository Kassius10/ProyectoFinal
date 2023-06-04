FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11
EXPOSE 8083:8083
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/ProyectoFinal-all.jar /app/ProyectoFinal-all.jar
ENTRYPOINT ["java","-jar","/app/ProyectoFinal-all.jar"]
