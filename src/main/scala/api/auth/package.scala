package ge.zgharbi.books
package api

import api.ControlError

import sttp.tapir.Endpoint

package object auth {
  val endpoints: List[Endpoint[Unit, ?, ControlError, ?, Any]] = List(
    UserApi.login,
  )
}
