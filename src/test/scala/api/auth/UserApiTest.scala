package ge.zgharbi.books
package api.auth

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import sttp.client3.testing.SttpBackendStub
import sttp.tapir.server.stub.TapirStubInterpreter
import sttp.tapir.testing.{EndpointVerificationError, EndpointVerifier}
import sttp.tapir.ztapir.RIOMonadError

import scala.concurrent.Future

class UserApiTest extends AsyncFlatSpec with Matchers {
//  val backendStub =
//    TapirStubInterpreter(SttpBackendStub.apply(new RIOMonadError[Any]))
//      .whenServerEndpoint(UserRoute.login)
//      .thenRunLogic()
//      .backend()
//
//  it must "work" in {
//    val response = basicRequest
//      .get(uri"http://test.com/api/")
//      .send(backendStub)
//
//    println(response.map(_.body).map(_.toString))
//  }

}
