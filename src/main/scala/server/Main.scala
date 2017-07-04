
package server


object Main extends App {

  import akka.actor.{ Address, AddressFromURIString }
  import akka.remote.routing.RemoteRouterConfig
  val addresses = Seq(
    Address("akka.tcp", "remotesys", "otherhost", 1234),
    AddressFromURIString("akka.tcp://othersys@anotherhost:1234"))
  val routerRemote = system.actorOf(
    RemoteRouterConfig(RoundRobinPool(5), addresses).props(Props[Echo]))
}
