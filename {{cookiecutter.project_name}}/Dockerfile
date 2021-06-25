FROM adoptopenjdk/openjdk15:alpine
RUN apk --no-cache add curl
COPY target/{{cookiecutter.project_name}}-*.jar {{cookiecutter.project_name}}.jar
COPY target/classes/logback*.xml /conf/
EXPOSE 8080
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-Dcom.sun.management.jmxremote", "-Dmicronaut.bootstrap.context=true", "-jar", "{{cookiecutter.project_name}}.jar"]
