# Use Ubuntu as the base image
FROM ubuntu:latest

# Set working directory
WORKDIR /app

# Install dependencies required by WebLogic
RUN apt-get update && apt-get install -y \
    libxrender1 libxtst6 libxi6 libxext6 unzip \
    && rm -rf /var/lib/apt/lists/*

# Create a non-root user for WebLogic
RUN useradd -ms /bin/bash weblogic && \
    mkdir -p /opt/java /opt/weblogic /app/oracle_inventory && \
    chown -R weblogic:weblogic /app /opt/java /opt/weblogic /app/oracle_inventory

# Switch to non-root user
USER weblogic

# Copy installation files
COPY jdk-8u441-linux-x64.tar.gz fmw_12.2.1.3.0_infrastructure.jar response.rsp create_domain.py /app/

# Create the oraInst.loc file manually
RUN echo "inventory_loc=/app/oracle_inventory" > /app/oraInst.loc && \
    echo "inst_group=weblogic" >> /app/oraInst.loc

# Extract and set up Java (keeping Java 7)
RUN tar -xzf jdk-8u441-linux-x64.tar.gz && \
rm jdk-8u441-linux-x64.tar.gz && \
mv jdk1.8.0_441 /opt/java

# Set environment variables
ENV JAVA_HOME=/opt/java/jdk1.8.0_441
ENV PATH="$JAVA_HOME/bin:$PATH"

# Verify Java installation
RUN java -version

# Install WebLogic in Silent Mode (using oraInst.loc)
RUN java -jar /app/fmw_12.2.1.3.0_infrastructure.jar -silent -responseFile /app/response.rsp -invPtrLoc /app/oraInst.loc
 #   || \cat /app/installLogs.log

# Set WebLogic environment variables
ENV WL_HOME=/opt/weblogic/wlserver
ENV DOMAIN_HOME=/opt/weblogic/user_projects/domains/base_domain
ENV PATH="$WL_HOME/server/bin:$PATH"

# Create WebLogic Domain using WLST
RUN . $WL_HOME/server/bin/setWLSEnv.sh && \
    java weblogic.WLST /app/create_domain.py

# Expose WebLogic ports
EXPOSE 7001 9002

# Default command to start WebLogic Admin Server
CMD ["sh", "-c", "$DOMAIN_HOME/bin/startWebLogic.sh && tail -f $DOMAIN_HOME/servers/AdminServer/logs/AdminServer.log"]
