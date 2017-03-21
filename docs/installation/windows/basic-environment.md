Java:
 - Download java last version from http://www.oracle.com/technetwork/java/javase/downloads/index.html
 - Unzip it into some folder
 - Go to Environment Variables and add `JAVA_HOME=unziped_folder`
 - Also in Environment Variables change variable PATH, add `;JAVA_HOME=%JAVA_HOME%\bin;` to the end of it.

MySQL:
 - [Install latest version](http://dev.mysql.com/downloads/mysql)
 - Log into MySQL terminal, it may look like `mysql -uroot -proot`
 - Create database: `create database jtalks character set utf8mb4`
 - NB: some functionality (like posting extended unicode symbols e.g. smiles) is not going to work with default MySQL configuration, read details in the [Advanced configuration of MySQL Server](https://github.com/jtalks-org/jcommune/blob/master/docs/installation/general-installation-guide.md#advanced-configuration-of-mysql-server)


Tomcat:
 - Download it: `wget http://apache.softded.ru/tomcat/tomcat-7/v7.0.35/bin/apache-tomcat-7.0.35.zip`
 - Unzip to some folder
 - When you want to start it, use `%TOMCAT_HOME%/bin/startup.bat`
 - When you want to shut it down, use `%TOMCAT_HOME%/bin/shutdown.bat`
