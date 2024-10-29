package ge.zgharbi.books
package auth

import auth.HelloWorld.*

import org.junit.runner.RunWith
import zio.*
import zio.test.*
import zio.test.junit.ZTestJUnitRunner

import java.io.IOException

object HelloWorld {
  def sayHello: ZIO[Any, IOException, Unit] =
    Console.printLine("Hello, World!")
}

@RunWith(classOf[ZTestJUnitRunner])
class AuthApiTest extends ZIOSpecDefault {
  def spec =
    suite("AuthApiTest") {
      suite("sayHello") {
        test("should return token") {
          for {
            _ <- sayHello
            output <- TestConsole.output
          } yield assertTrue(output == Vector("Hello, World!\n"))
        }
      }
    }
}
