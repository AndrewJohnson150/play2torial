This project's purpose is for me to become familiar with the Play framework. The project itself is nothing special; it is a simple web app that allows you to type something in, and when you click a button the text will be displayed. The text is also stored in a MySQL database by the name of 'testdb'. This project uses Spring and Hibernate, and its main purpose is to help me understand how Play, Spring, MySQL, and Hibernate all work together. In addition, the application uses slf4j logging to keep track of the sequence of events in an organized fashion. 

The default username and password for the database are found in application.conf.

The text field must be between 3-20 characters, not containing leading or trailing whitespace. There can also be no duplicates.

This project was started with the following tutorial:

[Learn how to use Play 2.3 with Java](https://github.com/YogoGit/play2torial/blob/master/JAVA.md)


The frameworks involved in the application include Hibernate, Spring, JUnit, and slf4j. 

Hibernate handles the transition from a Java Object to an entry in the MySQL database. When an Object has the '@Entity' tag, Hibernate knows to create a table for it. The DataConfig.java file dictates whether or not Hibernate may create tables with the 'vendorAdapter.setGenerateDdl(true);' line.

Spring handles dependency injection, for example when the phrase '@inject' is in a class that has the keyword '@Named', the necessary object is injected. The files Spring scans for those keywords in can be changed in AppConfig.java.

JUnit handles the testing for the application. In the test file, every method with an @Test tag is considered a test. When 'sbt test' is run, these tests are run and the outcome is reported.

slf4j is the logging framework for the application, and is configured in the Logger.xml file.