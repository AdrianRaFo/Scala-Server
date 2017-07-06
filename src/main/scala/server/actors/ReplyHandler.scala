/**
 * https://github.com/AdrianRaFo
 */
package server.actors

import akka.actor.Actor
import akka.io.Tcp

class ReplyHandler extends Actor {
  import Tcp._
  def receive = {
    case Received(data) => sender() ! Write(data)
    case PeerClosed     => context stop self
  }
}
