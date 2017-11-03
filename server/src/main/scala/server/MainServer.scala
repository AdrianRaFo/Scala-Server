/**
 * https://github.com/AdrianRaFo
 */
package main.scala.server

import akka.actor.{ActorRef, ActorSystem, Props}
import scala.io.StdIn
import actors.Server

object MainServer extends App {
  val system: ActorSystem = ActorSystem("Akka-Server")

  try {
    val server: ActorRef = system.actorOf(Props[Server], "server-Actor")
    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}
