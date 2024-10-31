/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books

object ErrorMessages {
  final val INVALID_CREDENTIALS = "Invalid credentials."
  final val USER_ALREADY_EXISTS = "User already exists."
  final val SERVICE_UNAVAILABLE = "Service unavailable."
  final val USER_NOT_FOUND = "User not found."
  final val DUPLICATE_EMAIL = "Duplicate email."
  final val JSON_DECODE_FAILURE = "Failed to decode JSON."
}
object Defs {
  final val Banner = """
                       |                        |\__/,|   (`\
                       |                      _.|o o  |_   ) )
                       |                   ---(((---(((-----------
                       |                   Book Cat ¯\_(ツ)_/¯ Zio
                       |
                       |""".stripMargin
  final val JSON_ENTITY_DISCRIMINATOR = "$type"
  final val SERVER_PORT = 8080
  final val EMAIL_REGEX =
    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
  final val PASSWORD_MIN_LENGTH = 8
  final val PASSWORD_REGEX: String =
    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{" + PASSWORD_MIN_LENGTH + ",}$"
}
