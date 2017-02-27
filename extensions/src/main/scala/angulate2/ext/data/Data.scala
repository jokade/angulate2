//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angulate2 extension for definition of data objects/classes via an @Data annotation

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.data

import de.surfice.smacrotools.MacroAnnotationHandler

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

/**
 * Annotation for case classes to mark them as a pure JavaScript data object.
 *
 * @example
 * {{{
 * @Data
 * case class Foo(id: Int, var bar: String)
 * }}}
 * is expanded to
 * {{{
 * @js.native
 * trait Foo extends js.Object {
 *   val id: Int = js.native
 *   var bar: String = js.native
 * }
 *
 * object Foo {
 *   def apply(id: Int, bar: String): Foo =
 *     js.Dynamic.literal(id = id, bar = bar).asInstanceOf[Foo]
 * }
 * }}}
 */
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Data extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Data.Macro.impl
}


object Data {

  private[angulate2] class Macro(val c: whitebox.Context) extends MacroAnnotationHandler {
    import c.universe._

    override val annotationName: String = "Data"

    override val supportsClasses: Boolean = true

    override val supportsTraits: Boolean = false

    override val supportsObjects: Boolean = false

    override val createCompanion: Boolean = true

    private val jsObjectType = tq"${c.weakTypeOf[js.Object]}"
    private val jsNativeAnnot = q"new scalajs.js.native()"

    override def transform: Transformation = super.transform andThen {
      case cls: ClassTransformData =>
        import cls.modParts._
        val members = params map {
          case q"$mods val $name: $tpe = $rhs" => Assign("val",name,mapType(tpe),rhs)
          case q"$_ var $name: $tpe = $rhs" => Assign("var",name,mapType(tpe),rhs)
        }
        val bodyMembers = members map {
          case Assign("val",name,tpe,_) => q"val $name: $tpe = scalajs.js.native"
          case Assign("var",name,tpe,_) => q"var $name: $tpe = scalajs.js.native"
        }

        val args = members map ( p => if(p.rhs.isEmpty) q"${p.name}: ${p.tpe}" else q"val ${p.name}: ${p.tpe} = ${p.rhs.get}")
        val literalArgs = members map ( p => q"${p.name} = ${p.name}" )
        val updCompanion = companion.map{ obj =>
          val apply = q"""def apply(..$args) = scalajs.js.Dynamic.literal(..$literalArgs).asInstanceOf[${cls.modParts.name}]"""
          TransformData(obj).addStatements(apply).modParts
        }

        val traitParts = TraitParts(
          name,
          tparams,
          Nil,
          Seq(jsObjectType),
          self,
          bodyMembers,
          fullName,
          Modifiers(NoFlags,modifiers.privateWithin,modifiers.annotations:+jsNativeAnnot),
          updCompanion)
        TraitTransformData(null,traitParts,cls.data)
      case x => x
    }

    private val optionType = weakTypeOf[Option[_]]
    private val iterableType = weakTypeOf[Iterable[_]]
    private val mapType = weakTypeOf[Map[_,_]]

    private def mapType(tpe: Tree): Tree = {
      val t = c.typecheck(tpe,c.TYPEmode,withMacrosDisabled = true).tpe
      if(t <:< optionType)
        tq"scalajs.js.UndefOr[${t.typeArgs.head}]"
      else if(t <:< mapType)
        tq"scalajs.js.Dictionary[${t.typeArgs.last}]"
      else if(t <:< iterableType)
        tq"scalajs.js.Array[${t.typeArgs.head}]"
      else
        tpe
    }

//      c.typecheck(tpe,c.TYPEmode) match {
//        case q"Option[$T]" => tq"scalajs.js.UndefOr[$T]"
//        case x => x
//      }
    case class Assign(stype: String, name: TermName, tpe: Tree, rhs: Option[Tree])
    object Assign {
      def apply(stype: String, name: TermName, tpe: Tree, rhs: Tree): Assign =
        apply(stype,name,tpe,if(rhs.isEmpty) None else Some(rhs))
    }
  }
}
