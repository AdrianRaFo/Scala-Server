/**
 * https://github.com/AdrianRaFo
 */
package client

import akka.actor.{ActorRef, ActorSystem, Props}
import scala.io.StdIn
import akka.util.ByteString

import actors.Client

object MainClient extends App {
  val system: ActorSystem = ActorSystem("Akka-Client")

  try {
    val client: ActorRef = system.actorOf(Props[Client], "client-Actor")
    println(">>> Press ENTER to send <<<")
    var data = StdIn.readLine()
    client ! ByteString.fromString(data)
    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
    client ! "close"
  } finally {
    system.terminate()
  }
}
