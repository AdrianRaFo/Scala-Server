/**
 * https://github.com/AdrianRaFo
 */
package server

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import server.actors.Server

import scala.concurrent.duration
import scala.io.StdIn

object MainServer extends App {

  implicit val timeOut: Timeout = Timeout(1, duration.MINUTES)

  val system: ActorSystem = ActorSystem("Akka-Server")

  try {
    val server: ActorRef = system.actorOf(Props[Server])
    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}
