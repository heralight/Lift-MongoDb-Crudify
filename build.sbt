name := "Lift-MongoDb-Crudify"

organization := "mycompany"

version := "0.1-SNAPSHOT"

scalaVersion := "2.9.1"

resolvers ++= Seq(
    "Liftmodules repo"       at "https://repository-liftmodules.forge.cloudbees.com/release",
	"Scala Tools Releases" at "http://scala-tools.org/repo-releases/",
	"Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
    "Typesafe Repository"    at "http://repo.typesafe.com/typesafe/releases/"
)


{
  val liftVersion = "2.4"
  libraryDependencies ++= Seq(
    "net.liftweb" 				%% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb"               %% "lift-mongodb-record"      % liftVersion,
    "ch.qos.logback"            % "logback-classic"           % "1.0.3",
    "org.eclipse.jetty"          % "jetty-webapp"             % "7.1.0.RC1"              % "container",
    "com.foursquare"            %% "rogue"                    % "1.1.6"                  % "compile->default" intransitive(),
    "org.mortbay.jetty"          % "jetty"                    % "6.1.22"                 % "test",                   // For Jetty 6, add scope test to make jetty avl. for tests
    "junit"                      % "junit"                    % "4.8"                    % "test",          // For JUnit 4 testing
    "org.scalatest"             %% "scalatest"                % "1.6.1"                  % "test"
    )
}

scalacOptions += "-deprecation"


seq(webSettings :_*)

// If using JRebel with 0.1.0 of the sbt web plugin
//jettyScanDirs := Nil

// using 0.2.4+ of the sbt web plugin
scanDirectories in Compile := Nil

// by default, it listens on port 8080; use the following to override
port in container.Configuration := 8081