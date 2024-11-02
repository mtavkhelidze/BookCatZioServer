package ge.zgharbi.books
package api.auth

import control.AuthControl
import http.routes.auth.UserRoute

import sttp.client3.*
import sttp.client3.testing.SttpBackendStub
import sttp.model.{StatusCode, Uri}
import sttp.tapir.server.stub.TapirStubInterpreter
import sttp.tapir.ztapir.*
import zio.*
import zio.test.*

object UserApiTest extends ZIOSpecDefault {

  val path = uri"${UserApi.login.showShort.toString.replace("* ", "")}"

  def spec = suite("UserApiTest") {
    test("simple test") {
      val backendStub = TapirStubInterpreter(
        SttpBackendStub.apply(new RIOMonadError[AuthControl]),
      )
        .whenServerEndpoint(UserRoute.login)
        .thenRunLogic()
        .backend()

      for {
        response <- basicRequest
          .get(path)
          .send(backendStub)
        _ <- Console.printLine(response.code)
      } yield assertTrue(response.code == StatusCode.NotFound)
    }
  }.provide(AuthControl.live)
}
