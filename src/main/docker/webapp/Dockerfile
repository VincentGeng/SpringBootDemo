FROM java:8
COPY ./web.jar /app/web.jar
CMD ["java", "-Dspring.profiles.active=container", "-jar", "/app/web.jar"]