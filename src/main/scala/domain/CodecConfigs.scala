/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package domain

import com.github.plokhotnyuk.jsoniter_scala.macros.*
import sttp.tapir.generic.Configuration

object CodecConfigs {

  given schemaConfig: Configuration = Configuration.default
    .withDiscriminator("$type")

  inline def jsonIter: CodecMakerConfig = CodecMakerConfig
    .withAlwaysEmitDiscriminator(true)
    .withDiscriminatorFieldName(Some("$type"))
    .withRequireDiscriminatorFirst(false)
    .withTransientDefault(false)
    .withRequireDefaultFields(true)
}
