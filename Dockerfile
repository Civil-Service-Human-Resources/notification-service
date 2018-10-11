FROM java:8

ENV SPRING_PROFILES_ACTIVE production

EXPOSE 9005

CMD java -jar /data/app.jar

ADD build/libs/notification-service-1.0.0.jar /data/app.jar

ADD https://github.com/Civil-Service-Human-Resources/lpg-terraform-paas/releases/download/hammer-0.1/hammer /bin/hammer
RUN chmod +x /bin/hammer && echo "Hammer v0.1 Added"
