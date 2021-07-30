FROM openjdk:8u181

ENV SPRING_PROFILES_ACTIVE production

EXPOSE 9006

ADD lib/AI-Agent.xml /opt/appinsights/AI-Agent.xml
ADD https://github.com/microsoft/ApplicationInsights-Java/releases/download/3.0.3/applicationinsights-agent-3.0.3.jar /opt/appinsights/applicationinsights-agent-3.0.3.jar

ADD build/libs/notification-service-1.0.0.jar /data/app.jar
CMD java -javaagent:/opt/appinsights/applicationinsights-agent-3.0.3.jar -jar /data/app.jar
