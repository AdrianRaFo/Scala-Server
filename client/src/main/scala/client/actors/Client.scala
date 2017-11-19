/**
 * https://github.com/AdrianRaFo
 */
package client.actors

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.io.{IO, Tcp}
import akka.util.ByteString

class Client extends Actor with ActorLogging {

  import Tcp._
  import context.system

  IO(Tcp) ! Connect(new InetSocketAddress("localhost", 16753))
  var extsender: ActorRef = _
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
          extsender = sender()
          connection ! Write(data)
        case Received(data) =>
          println("Receive " + data.utf8String)
          extsender ! data.utf8String
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
