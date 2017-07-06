/**
 * https://github.com/AdrianRaFo
 */
package server.actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.io.{IO, Tcp}
import java.net.InetSocketAddress

class Server extends Actor with ActorLogging {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 16753))

  def receive = {
    case b @ Bound(localAddress) =>
      log.info(s"Bound received $localAddress")
      context.parent ! b

    case CommandFailed(bin: Bind) =>
      log.info(s"CommandFailed $bin")
      context stop self

    case c @ Connected(remote, local) =>
      log.info(s"Connected to $remote  $local")
      val handler    = context.actorOf(Props[SimplisticHandler])
      val connection = sender()
      connection ! Register(handler)
  }

}
