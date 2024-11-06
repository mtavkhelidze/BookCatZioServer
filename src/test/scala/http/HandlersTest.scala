//package ge.zgharbi.books
//package http
//
//import api.errorVariant
//import http.Command.Success
//import http.OldHttpError.{InvalidCredentialsError, JsonDecodeFailureError}
//
//import com.github.plokhotnyuk.jsoniter_scala.core.*
//import com.github.plokhotnyuk.jsoniter_scala.macros.*
//import org.scalatest.matchers.must.Matchers
//import org.scalatest.wordspec.AnyWordSpec
//import sttp.client3.{SttpApi, UriContext}
//import sttp.client3.testing.*
//import sttp.tapir.{oneOf as oneOfVariants, *}
//import sttp.tapir.json.jsoniter.jsonBody
//import sttp.tapir.server.interceptor.CustomiseInterceptors
//import sttp.tapir.server.stub.TapirStubInterpreter
//import sttp.tapir.server.ziohttp.ZioHttpServerOptions
//
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.concurrent.Future
//enum Command {
//  case Success
//  case JsonDecodeFailure
//}
//case class TestRequest(command: Command)
//// @note: needs to be sealed so we get discriminator back
//sealed case class TestResponse(value: String)
//
//object DataCodecs {
//
//  import domain.config.{jsonIterConfig, given}
//
//  given commandSchem: Schema[Command] = Schema.derived[Command]
//  given schemaReq: Schema[TestRequest] = Schema.derived[TestRequest]
//  given codecReq: JsonValueCodec[TestRequest] =
//    JsonCodecMaker.make(jsonIterConfig.withAllowRecursiveTypes(true))
//  given schemaRes: Schema[TestResponse] = Schema.derived[TestResponse]
//  given codecRes: JsonValueCodec[TestResponse] =
//    JsonCodecMaker.make(jsonIterConfig)
//}
////class HandlersTest extends AnyWordSpec with Matchers with SttpApi {
////  import DataCodecs.given
////
////  val customOptions: CustomiseInterceptors[Future, SttpServerOptions] = {
////    import scala.concurrent.ExecutionContext.Implicits.global
////    ZioHttpServerOptions.customiseInterceptors
////      .exceptionHandler(Handlers.excheptionHandler)
////  }
////  def backendStub =
////    TapirStubInterpreter(customOptions, SttpBackendStub.asynchronousFuture)
////      .whenServerEndpoint(postServerLogic)
////      .thenRunLogic()
////      .backend()
////
////  def postServerLogic = epPost
////    .serverLogic(req =>
////      Future.successful {
////        req.command match {
////          case Command.Success           => Right(TestResponse("Success"))
////          case Command.JsonDecodeFailure => Left(JsonDecodeFailureError())
////        }
////      },
////    )
////
////  def epPost = endpoint.post
////    .in("test")
////    .in(jsonBody[TestRequest])
////    .out(jsonBody[TestResponse])
////    .errorOut(
////      oneOfVariants[HttpError](
////        errorVariant[InvalidCredentialsError],
////        errorVariant[JsonDecodeFailureError],
////      ),
////    )
////
////  "excheptionHandler" must {
////    "work" in {
////      def prog = basicRequest
////        .post(uri"/test")
////        .body(writeToString(TestRequest(Success)))
////        .send(backendStub)
////
////      prog.collect { r =>
////        println(r)
////        r.body.isLeft must be(true)
////      }
////    }
////  }
////}
