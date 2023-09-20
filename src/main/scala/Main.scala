import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.scaladsl.Source

import scala.concurrent.Future
import scala.util.Random

case class Record(id: Int, seqNum: Int)

object Main extends App {

  private implicit val system = ActorSystem()

  val src = Random.shuffle(
    for {
      roomId <- 1 to 10
      seqNum <- 1 to 10
    } yield Record(roomId, seqNum)
  )
  println(s"src: $src")
  println(s"src.size: ${src.length}")

  Source(src).grouped(src.length).map {
    _.groupBy(_.id)
      .map { case (_, xs) =>
        xs.sortBy(_.seqNum)
      }
  }
    .mapConcat(identity)
    .zipWithIndex
    .mapAsyncUnordered(3) { case (xs, idx) =>
      println(s"idx:$idx records:${xs.mkString(",")}")
      Future.successful(())
    }.run()

}