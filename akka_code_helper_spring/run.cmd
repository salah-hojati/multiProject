 set MAVEN_HOME=C:\apache-maven-3.9.9
 set PATH=%MAVEN_HOME%\bin;%PATH%
 rem mvn spring-boot:run
@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
call mvn clean package
call  mvn dependency:build-classpath
call mvn spring-boot:run