FROM openjdk:8u181

ENV SPRING_PROFILES_ACTIVE production

EXPOSE 9006

ADD lib/AI-Agent.xml /opt/appinsights/AI-Agent.xml
ADD https://github.com/microsoft/ApplicationInsights-Java/releases/download/2.5.0/applicationinsights-agent-2.5.0.jar /opt/appinsights/applicationinsights-agent-2.5.0.jar

ADD build/libs/notification-service-1.0.0.jar /data/app.jar
CMD java -javaagent:/opt/appinsights/applicationinsights-agent-2.5.0.jar -jar /data/app.jar

ADD https://github.com/Civil-Service-Human-Resources/lpg-terraform-paas/releases/download/hammer-0.1/hammer /bin/hammer
RUN chmod +x /bin/hammer && echo "Hammer v0.1 Added"
