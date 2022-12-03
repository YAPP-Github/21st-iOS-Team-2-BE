#openjdk:11 image
FROM openjdk:11-jdk

#jar파일 생성 후 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

#컨테이너가 실행될 때 명령어 수행
CMD ["java","-jar","/app.jar"]
