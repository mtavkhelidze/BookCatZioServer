package ge.zgharbi.books
package domain

import com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig
import sttp.tapir.generic.Configuration

package object config {
  given schemaConfig: Configuration = Configuration.default
    .withDiscriminator(Defs.JSON_ENTITY_DISCRIMINATOR)
    
  inline def jsonIterConfig: CodecMakerConfig = CodecMakerConfig
    .withAlwaysEmitDiscriminator(true)
    .withDiscriminatorFieldName(Some(Defs.JSON_ENTITY_DISCRIMINATOR))
    .withTransientEmpty(true)
    .withTransientDefault(false)
    .withTransientNone(false)
    .withRequireDefaultFields(true)
}
