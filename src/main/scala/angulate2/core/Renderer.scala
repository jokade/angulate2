//     Project: angulate2 (https://github.com/jokade/angulate2)
//      Module: @angular/core/Renderer

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","Renderer")
class Renderer extends js.Any {
  import Renderer._
  def selectRootElement(selectorOrNode: js.Any, debugInfo: OptRenderDebugInfo = js.undefined) : js.Any = js.native
  def createElement(parentElement: js.Any, name: String, debugInfo: OptRenderDebugInfo = js.undefined) : js.Any = js.native
  def createViewRoot(hostElement: js.Any) : js.Any = js.native
  def createTemplateAnchor(parentElement: js.Any, debugInfo: OptRenderDebugInfo = js.undefined) : js.Any = js.native
  def createText(parentElement: js.Any, value: String, debugInfo: OptRenderDebugInfo = js.undefined) : js.Any = js.native
  def projectNodes(parentElement: js.Any, nodes: js.Array[js.Any]) : Unit = js.native
  def attachViewAfter(node: js.Any, viewRootNodes: js.Array[js.Any]) : Unit = js.native
  def detachView(viewRootNodes: js.Array[js.Any]) : Unit = js.native
  def destroyView(hostElement: js.Any, viewAllNodes: js.Array[js.Any]) : Unit = js.native
  def listen(renderElement: js.Any, name: String, callback: js.Function) : js.Function = js.native
  def listenGlobal(target: String, name: String, callback: js.Function) : js.Function = js.native
  def setElementProperty(renderElement: js.Any, propertyName: String, propertyValue: js.Any) : Unit = js.native
  def setElementAttribute(renderElement: js.Any, attributeName: String, attributeValue: String) : Unit = js.native
  def setBindingDebugInfo(renderElement: js.Any, propertyName: String, propertyValue: String) : Unit = js.native
  def setElementClass(renderElement: js.Any, className: String, isAdd: Boolean) : Unit = js.native
  def setElementStyle(renderElement: js.Any, styleName: String, styleValue: String) : Unit = js.native
  def invokeElementMethod(renderElement: js.Any, methodName: String, args: OptAnyArray = js.undefined) : Unit = js.native
  def setText(renderNode: js.Any, text: String) : Unit = js.native
  def animate(element: js.Any, startingStyles: AnimationStyles, keyframes: js.Array[AnimationKeyframe], duration: Int, delay: Int, easing: String, previousPlayers: js.UndefOr[js.Array[AnimationPlayer]] = js.undefined) : AnimationPlayer = js.native
}

object Renderer {
  type RenderDebugInfo = js.Dynamic
  type OptRenderDebugInfo = js.UndefOr[RenderDebugInfo]
  type AnimationStyles = js.Dynamic
  type AnimationKeyframe = js.Dynamic
  type AnimationPlayer = js.Dynamic
}
