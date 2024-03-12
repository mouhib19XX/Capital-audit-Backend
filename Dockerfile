
# Use the official Tomcat image as the base image
FROM tomcat:latest

# Set the working directory inside the Tomcat container
WORKDIR /usr/local/tomcat/webapps

# Copy the WAR file built by your Gradle project to the webapps directory of Tomcat
COPY /Capital-audit-Backend/build/libs/*.jar .

# Expose port 8080, which is the default port used by Tomcat
EXPOSE 8080

# Tomcat automatically runs when the container starts, so no need for a CMD directive
