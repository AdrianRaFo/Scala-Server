/**
 * https://github.com/AdrianRaFo
 */
package server.actors

import akka.actor.Actor
import akka.io.Tcp

class Reply extends Actor {
  import Tcp._
  def receive = {
    case Received(data) =>
      println("Reply " + data.utf8String)
      sender ! Write(data)
    case PeerClosed => context stop self
  }
}
