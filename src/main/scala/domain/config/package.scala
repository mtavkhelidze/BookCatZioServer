package ge.zgharbi.books
package domain

import com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig
import sttp.tapir.generic.Configuration

package object config {
  given schemaConfig: Configuration = Configuration.default
    .withDiscriminator(Defs.JSON_ENTITY_DISCRIMINATOR)
    .withFullSnakeCaseDiscriminatorValues

  inline def jsonIterConfig: CodecMakerConfig = CodecMakerConfig
    .withAllowRecursiveTypes(false)
    .withAlwaysEmitDiscriminator(true)
    .withDiscriminatorFieldName(Some(Defs.JSON_ENTITY_DISCRIMINATOR))
    .withRequireDefaultFields(true)
    .withTransientDefault(false)
    .withTransientEmpty(false)
    .withTransientNone(true)
}
