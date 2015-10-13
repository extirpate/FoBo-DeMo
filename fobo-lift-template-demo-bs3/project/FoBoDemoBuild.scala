import sbt._
import sbt.Keys._

object FoBoDemoBuild extends Build {

  val liftVersion = SettingKey[String]("liftVersion", "Version number of the Lift Web Framework")
  
  val liftEdition = SettingKey[String]("liftEdition", "Lift Edition (short version number to append to artifact name)") 
                               
 
}
