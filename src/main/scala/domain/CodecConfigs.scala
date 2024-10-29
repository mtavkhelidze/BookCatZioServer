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

  inline def jsonIterConfig: CodecMakerConfig = CodecMakerConfig
    .withAlwaysEmitDiscriminator(true)
    .withDiscriminatorFieldName(Some("$type"))
    .withRequireDefaultFields(true)
    .withRequireDiscriminatorFirst(false)
    .withTransientDefault(false)
}
