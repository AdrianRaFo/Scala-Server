/**
 * https://github.com/AdrianRaFo
 */
package client.actors

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging}
import akka.io.{IO, Tcp}
import akka.util.{ByteString, Timeout}
import akka.pattern.ask

import scala.concurrent.{duration, Await}
import scala.concurrent.duration.Duration

class Client extends Actor with ActorLogging {

  import Tcp._
  import context.system
  implicit val timeOut: Timeout = Timeout(1, duration.MINUTES)

  IO(Tcp) ! Connect(new InetSocketAddress("localhost", 16753))

  def receive = {
    case CommandFailed(_: Connect) =>
      log.error("connect failed")
      context stop self

    case c @ Connected(remote, local) =>
      log.info(s"Connected to $remote  $local")
      val connection = sender()
      connection ! Register(self)
      context become {
        case data: ByteString =>
          println("Sending " + data.utf8String)
          connection ! Write(data)
        case Received(data) =>
          println("Receive " + data.utf8String)
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          log.error(s"write failed $w")
        case "close" =>
          log.info("closing connection")
          connection ! Close
        case _: ConnectionClosed =>
          log.info("connection closed")
          context stop self
      }
  }
}
