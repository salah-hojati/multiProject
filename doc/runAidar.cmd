set HTTP_PROXY=socks5://127.0.0.1:9090
set HTTPS_PROXY=socks5://127.0.0.1:9090
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
set MAVEN_OPTS=-Dmaven.repo.local=C:\your-maven-repo
set MAVEN_HOME=C:\apache-maven-3.9.9
set PATH=%MAVEN_HOME%\bin;%PATH%
setx   GEMINI_API_KEY AIzaSyBivCYOOBmrc8crI-Hb9vcbQ5xFQ4dg90o
aider --model gemini/gemini-1.5-pro-latest
cmd
