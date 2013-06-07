import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "SimpleForum"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
      javaCore
    , javaJdbc
    , javaEbean
    , "mysql" % "mysql-connector-java" % "5.1.22"
    , "postgresql" % "postgresql" % "9.1-901.jdbc4"
    , "com.typesafe" %% "play-plugins-mailer" % "2.1-RC2"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
