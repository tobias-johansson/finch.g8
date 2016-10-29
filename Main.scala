
import io.finch._
import io.finch.circe._

import com.twitter.finagle.Http
import com.twitter.util.Await

import io.circe.generic.auto._


object Main extends App {

  case class Greeting(message: String)

  var greeting = Greeting("Hello world!")

  val greetingGet: Endpoint[Greeting] =
    get(/) { Ok(greeting) }

  val greetingPost: Endpoint[Greeting] =
    post(/ :: body.as[Greeting]) { gr: Greeting =>
      greeting = gr
      Ok(greeting)
    }

  Await.ready(
    Http.server.serve(":9999", (greetingGet :+: greetingPost).toServiceAs[Application.Json])
  )

}
