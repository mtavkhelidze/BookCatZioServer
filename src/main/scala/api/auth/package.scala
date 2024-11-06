package ge.zgharbi.books
package api

import api.ApiError

import sttp.tapir.Endpoint

package object auth {
  val endpoints: List[Endpoint[Unit, ?, ApiError, ?, Any]] = List(
    UserApi.login,
  )
}
