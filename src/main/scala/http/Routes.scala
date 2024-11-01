package ge.zgharbi.books
package http

import http.routes.auth.UserRoute

object Routes {
  val apiRoutes = List(UserRoute.login)
}
