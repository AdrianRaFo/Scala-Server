/**
 * https://github.com/AdrianRaFo
 */
package client

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.{ByteString, Timeout}
import client.actors.Client
import akka.pattern.ask

import scala.concurrent.{Await, duration}
import scala.concurrent.duration.Duration
import scala.io.StdIn

object MainClient extends App {
  val system: ActorSystem = ActorSystem("Akka-Client")
  implicit val timeOut: Timeout = Timeout(1, duration.MINUTES)

  try {
    val client: ActorRef = system.actorOf(Props[Client])
    println(">>> Press ENTER to send <<<")
    var data = StdIn.readLine()
    val future = client ? ByteString.fromString(data)
    val result = Await.result(future, Duration.Inf)
    println("Result "+result)
    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
    client ! "close"
  } finally {
    system.terminate()
  }
}
