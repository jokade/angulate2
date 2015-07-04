//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: 

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2.test

import utest._
import org.querki.jquery._
import scalajs.js
import org.scalajs.dom

abstract class TestBase extends TestSuite {
  lazy val body = $(org.scalajs.dom.document.body)

  /**
   * Appends the provided HTML to the `<body>` tag and then runs the specified `testBody`.
   *
   * @param html
   * @param testBody
   * @tparam T
   * @return
   */
  def withHtml[T](html: String)(testBody: =>T) : T = {
    body.append(html)
    testBody
  }

  def invokeLater(body: =>Any): Unit = dom.window.setTimeout(()=>body,0)

  def jq(selector: String): JQuery = $(selector)

  def laterWithJQ(selector: String)(body: JQuery=>Any): Unit = invokeLater(body($(selector)))

  def laterWithChildren(selector: String)(body: JQuery=>Any): Unit = laterWithJQ(selector)( e => body(e.children) )

  def log(msg: js.Any): Unit = js.Dynamic.global.console.log(msg)
}
