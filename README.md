### Setting Up Tomcat ###
Download Tomcat 7 and Install It in the PATH /usr/local/tomcat7

```shell
tar -zxf apache-tomcat-7.0.47.tar.gz

mv apache-tomcat-7.0.47 /usr/local/tomcat7
```

From now on, this will be our $CATALINA_HOME

Copy the following log4j.properties file into $CATALINA_HOME/lib

```
log4j.rootLogger=INFO,CATALINA

# Define all the appenders
log4j.appender.CATALINA=org.apache.log4j.DailyRollingFileAppender
log4j.appender.CATALINA.File=${catalina.base}/logs/catalina.log
log4j.appender.CATALINA.Append=true
log4j.appender.CATALINA.Encoding=UTF-8

# Roll-over the log once per day
log4j.appender.CATALINA.DatePattern='.'yyyy-MM-dd'.log'
log4j.appender.CATALINA.layout = org.apache.log4j.PatternLayout
log4j.appender.CATALINA.layout.ConversionPattern =%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%t] (%C.%M:%L) %x - %m%n

```

Set up your JAVA_HOME variable

```shell

export JAVA_HOME=/opt/java/64/jdk1.7.0_25

```
### Setting up the Project for Eclipse ###

Check out the project from GitHub and then create the project for Eclipse

Run the following command to set up the project for Eclipse

```shell

git clone https://github.com/israelekpo/graph-service.git

cd graph-service

mvn eclipse:clean eclipse:eclipse -DdownloadSources=true

```

### Configuring the Graph Service ###

Lets open up the WEB-INF/beans.xml file and specify a folder for the Graph DB

```xml
  <bean id="graphDBDAO" class="com.israelekpo.strata.service.graph.dao.GraphDBDAOImpl" >
    <property name="dbPath" value="/home/iekpo/Labs/neo4j-db"/>
  </bean>
```



Compile the Application

```

# Compile the Service
mvn clean package

# Copy it over to the Tomcat 7 WebApps folder
cp target/graph-service.war $CATALINA_HOME/webapps

```

### Starting the Graph Service ###

```

# Go to the bin folder for Tomcat

cd $CATALINA_HOME/bin

# Start the service

./catalina.sh start

```
