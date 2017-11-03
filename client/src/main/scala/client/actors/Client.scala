/**
 * https://github.com/AdrianRaFo
 */
package client.actors

import akka.actor.{Actor,ActorLogging}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import java.net.InetSocketAddress

class Client extends Actor with ActorLogging {

  import Tcp._
  import context.system

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
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          log.error(s"write failed $w")
        case Received(data) =>
          log.info(s"Received ${data.utf8String}")
        case "close" =>
          log.info("closing connection")
          connection ! Close
        case _: ConnectionClosed =>
          log.info("connection closed")
          context stop self
      }
  }
}
