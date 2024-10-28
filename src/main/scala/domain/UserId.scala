/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package domain

opaque type UserId <: Int = Int
object UserId {
  inline def apply(i: Int): UserId = i
}
