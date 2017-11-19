/**
 * https://github.com/AdrianRaFo
 */
package client

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.ByteString
import client.actors.Client

import scala.io.StdIn

object MainClient extends App {
  val system: ActorSystem = ActorSystem("Akka-Client")

  try {
    val client: ActorRef = system.actorOf(Props[Client])
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
