//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","AnimationEntryMetadata")
class AnimationEntryMetadata(val name: String, definitions: js.Array[AnimationStateMetadata]) extends js.Object

@js.native
@JSImport("@angular/core","AnimationMetadata")
class AnimationMetadata extends js.Object

@js.native
@JSImport("@angular/core","AnimationKeyframesSequenceMetadata")
class AnimationKeyframesSequenceMetadata(val steps: js.Array[AnimationStyleMetadata]) extends AnimationMetadata

@js.native
@JSImport("@angular/core","AnimationStyleMetadata")
class AnimationStyleMetadata extends AnimationMetadata

@js.native
@JSImport("@angular/core","AnimationAnimateMetadata")
class AnimationAnimateMetadata(val styles: js.Array[js.Any], val offset: js.Any = null) extends AnimationMetadata

@js.native
@JSImport("@angular/core","AnimationWithStepsMetadata")
abstract class AnimationWithStepsMetadata extends AnimationMetadata {
  def steps(): js.Array[AnimationMetadata] = js.native
}

@js.native
@JSImport("@angular/core","AnimationSequenceMetadata")
class AnimationSequenceMetadata(_steps: js.Array[AnimationMetadata]) extends AnimationWithStepsMetadata

@js.native
@JSImport("@angular/core","AnimationGroupMetadata")
class AnimationGroupMetadata(_steps: js.Array[AnimationMetadata]) extends AnimationWithStepsMetadata

@js.native
@JSImport("@angular/core","AnimationStateMetadata")
class AnimationStateMetadata extends js.Object

@js.native
@JSImport("@angular/core","AnimationStateDeclarationMetadata")
class AnimationStateDeclarationMetadata(val stateNameExpr: String, styles: AnimationStyleMetadata) extends AnimationStateMetadata

@js.native
@JSImport("@angular/core","AnimationStateTransitionMetadata")
class AnimationStateTransitionMetadata(val stateChangeExpr: String, steps: AnimationMetadata) extends AnimationStateMetadata


@js.native
@JSImport("@angular/core","trigger")
object trigger extends js.Object {
  def apply(name: String, animation: js.Array[AnimationStateMetadata]): AnimationEntryMetadata = js.native
}

@js.native
@JSImport("@angular/core","state")
object state extends js.Object {
  def apply(stateNameExpr: String, styles: AnimationStyleMetadata): AnimationStateDeclarationMetadata = js.native
}

@js.native
@JSImport("@angular/core","style")
object style extends js.Object {
  def apply(tokens: String): AnimationStyleMetadata = js.native
  def apply(tokens: js.Dictionary[String]): AnimationStyleMetadata = js.native
  def apply(tokens: js.Dynamic): AnimationStyleMetadata = js.native
  def apply(tokens: js.Array[js.Any]): AnimationStyleMetadata = js.native
}

@js.native
@JSImport("@angular/core","transition")
object transition extends js.Object {
  def apply(stateChangeExpr: String, steps: AnimationMetadata): AnimationStateTransitionMetadata = js.native
  def apply[T<:AnimationMetadata](stateChangeExpr: String, steps: js.Array[T]): AnimationStateTransitionMetadata = js.native
}

@js.native
@JSImport("@angular/core","animate")
object animate extends js.Object {
  def apply(timing: Int): AnimationAnimateMetadata = js.native
  def apply(timing: Int, styles: AnimationStyleMetadata): AnimationAnimateMetadata = js.native
  def apply(timing: Int, styles: AnimationKeyframesSequenceMetadata): AnimationAnimateMetadata = js.native
  def apply(timing: String): AnimationAnimateMetadata = js.native
  def apply(timing: String, styles: AnimationStyleMetadata): AnimationAnimateMetadata = js.native
  def apply(timing: String, styles: AnimationKeyframesSequenceMetadata): AnimationAnimateMetadata = js.native
}

@js.native
@JSImport("@angular/core","group")
object group extends js.Object {
  def apply[T<:AnimationMetadata](steps: js.Array[T]): AnimationGroupMetadata = js.native
}

@js.native
@JSImport("@angular/core","sequence")
object sequence extends js.Object {
  def apply[T<:AnimationMetadata](steps: js.Array[T]): AnimationSequenceMetadata = js.native
}

@js.native
@JSImport("@angular/core","keyframes")
object keyframes extends js.Object {
  def apply(steps: js.Array[AnimationStyleMetadata]): AnimationKeyframesSequenceMetadata = js.native
}

object animations {
  @inline
  val animate = angulate2.core.animate
  @inline
  val group = angulate2.core.group
  @inline
  val sequence = angulate2.core.sequence
  @inline
  val style = angulate2.core.style
  @inline
  val state = angulate2.core.state
  @inline
  val keyframes = angulate2.core.keyframes
  @inline
  val transition = angulate2.core.transition
  @inline
  val trigger = angulate2.core.trigger
}