package ge.zgharbi.books
package http

object Handlers {
//  def excheptionHandler[F[_]]: ExceptionHandler[F] = ExceptionHandler.pure[F] {
//    ctx =>
//      logAndRespond(JsonDecodeFailureError())
//      None
//  }

//  inline def logAndRespond[E <: ControlError: ClassTag](e: E) = {
//    def code = ControlError.httpCodeFor[E]
//    println(">>>>>>> " + code)
//  }

//  def handler = DecodeFailureHandler.pure[_] { ctx =>
//    ctx match {
//      case _ =>
//        println(ctx)
//        None
//    }
//
//  }
//  def defaultErrorHandler(e: String): ValuedEndpointOutput[?] =
//    ValuedEndpointOutput[ControlError](
//      statusCode(StatusCode.UnprocessableEntity).and(jsonBody[ControlError]),
//      JsonDecodeFailureError(List(e)),
//    )
}
