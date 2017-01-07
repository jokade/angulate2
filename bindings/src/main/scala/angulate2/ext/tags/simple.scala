//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.tags

import scalatags.Text.all._
import scalatags.Text.TypedTag

object simple extends NgSimpleScalatags


private[angulate2] trait NgSimpleScalatags {

  def tpl(tags: TypedTag[_]*): String = tags map (_.toString) mkString "\n"

  val ngIf = attr("*ngIf", raw = true)
  val ngFor = attr("*ngFor", raw = true)

  /** One-way binding from data source to view target.
   *
   * Use `ngBind("target") := "expression"`
   * in place of `[target]="expression"`.
   *
   * @param target data source
   */
  @inline
  def ngBind(target: String) = attr(s"bind-$target")

  /** Two-way data binding.
   *
   * Use `ngBindOn("target") := "expression"`
   * in place of `[(target)]="expression"`
   *
   * @param target binding target
   */
  @inline
  def ngBindOn(target: String) = attr(s"bindon-$target")

  /** Binding to an event listener.
   *
   * Use `ngOn("event"):="expression`
   * in place of `(event)="expression"`
   *
   * @param event event name
   */
  @inline
  def ngOn(event: String) = attr(s"on-$target")

  /** Shortcut for `ngOn("click")`
   */
  val ngClick = attr("on-click")

  /** Shortcut for `ngBind("disabled")`
   */
  val ngDisabled = attr("bind-disabled")

  /** Shortcut for `ngBindOn("ngModel")`
   */
  val ngModel = attr("[(ngModel)]", raw = true)

  /** Class binding.
   *
   * Use `ngBindClass("cls") := "expression"`
   * in place of `[class.cls] := "expression"`
   *
   * @param cls name of the class
   */
  @inline
  def ngBindClass(cls: String) = attr(s"[class.$cls]", raw = true)

  /** Style binding.
   *
   * Use `ngBindStyle("prop"):="expression"`
   * in place of `[style.prop]="expression"`.
   *
   * @param prop name of the CSS style property (e.g. `background`)
   */
  @inline
  def ngBindStyle(prop: String) = attr(s"[style.$prop]", raw = true)

  val ngSwitch = ngBind("ngSwitch")
  val ngSwitchCase = attr("*ngSwitchCase", raw = true)
  val ngSwitchDefault = attr("*ngSwitchDefault", raw = true)

  @inline
  def routerLink(url: String, active: String = "", activeOptions: String = "", queryParams: String = "")(xs: Modifier*) =
    tag("a")(
      attr("routerLink"):=url,
      attr("routerLinkActive"):=active,
      attr("queryParams") := queryParams,
      attr("bind-routerLinkActiveOptions"):=activeOptions)(xs)

  @inline
  def bindRouterLink(expr: String, active: String = "", queryParams: String = "")(xs: Modifier*) =
    tag("a")(attr("bind-routerLink"):=expr, attr("routerLinkActive"):=active, attr("queryParams") := queryParams)(xs)


  val routerLink = attr("routerLink")

  val routerOutlet = tag("router-outlet")
  @inline
  def routerOutlet(name: String) = tag("router-outlet")(attr("name"):=name)
}


