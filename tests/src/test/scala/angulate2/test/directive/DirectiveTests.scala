//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Test cases for @Directive

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.test.directive

import angulate2._
import angulate2.core.Component
import angulate2.test.TestBase

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

object DirectiveTests extends TestBase {

  var data: Any = null

  ng.register[DirTest1]
  ng.register[Directive1]
  ng.register[DirTest2]
  ng.register[Directive2]

  val tests = TestSuite {
    data = null

    'simple-{
      withHtml("""<dirtest1 id="dir1"></dirtest1>""") {
        ng.bootstrapWith[DirTest1]
        invokeLater( assert( data == "dir1" ) )
      }
    }
/*
    'DI-{
      'componentLevel-{
        withHtml("""<dirtest2 id="dir2"></dirtest2>""") {
          angular.bootstrapWith[DirTest2]
          invokeLater( assert( data.isInstanceOf[TestService] ) )
        }
      }
    }
    */
  }
}

@JSExport
class TestService {

}

@Component(
  selector = "dirtest1",
  template = "<div directive1></div>",
  directives = js.Array( @@[Directive1] )
)
class DirTest1

@Directive(
  selector = "[directive1]"
)
class Directive1 {
  DirectiveTests.data = "dir1"
}


@Component(
  selector = "dirtest2",
  template = "<div directive2></div>",
  appInjector = js.Array( @@[TestService] ),
  directives = js.Array( @@[Directive2] )
)
class DirTest2

@Directive(
  selector = "[directive2]"
)
class Directive2(ts: TestService) {
  DirectiveTests.data = ts
}
