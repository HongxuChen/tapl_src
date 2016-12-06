package tapl.utils

import com.typesafe.config.ConfigFactory

object Configs {
  private val conf = ConfigFactory.load()
  val folder = conf.getString("filepath.tapl.directory")

}
