package ge.zgharbi.books
package api

import http.HttpError

import sttp.tapir.Endpoint

package object auth {
  val endpoints: List[Endpoint[Unit, ?, HttpError, ?, Any]] = List(
    UserApi.login,
  )
}
