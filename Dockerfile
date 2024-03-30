# 빌드를 어떻게 하는가?

# From => 베이스가 되는 이미지를 지정한다. 뼈대를 일단 받아서 layer를 더하는 느낌 (리눅스OS, jdk, ubuntu)
FROM openjdk:17-jdk-slim as builder

WORKDIR /app
COPY gradlew build.gradle settings.gradle  ./
COPY gradle ./gradle
COPY src/main ./src/main
RUN ./gradlew bootJar


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT java -jar app.jar



# Run => 이미지를 빌드할때 사용하는 커맨드를 설정

# ADD => 파일이나 폴더를 추가하기 위해 사용한다.

# COPY => 호스트의 환경을 사용할때

# EXPOST => 8080포트처럼 지정할 수 있음

# ENV => 환경변수를 지정할 때 사용

# CMD => run과 달리 이미지를 빌드할 때 실행되는 것이 아닌, 컨테이너를 시작할때 실행된다.

# entrypoint => 도커 이미지가 실행될때 사용되는 커맨드를 지정한다.

# WORKDIR => 커맨드들이 실행하는 디렉토리를 지정합니다.

# VOLUME => 호스트의 디렉토리를 도커 컨테이너에 연결

#FROM ubuntu:latest
#LABEL authors="home"
#
#ENTRYPOINT ["top", "-b"]