//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: 

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2.test.component

import angulate2._
import angulate2.test.TestBase
import utest._

import scala.scalajs.js

object ComponentTests extends TestBase {

  angular.register[Component1]
  angular.register[Component2]

  val tests = TestSuite {

    'simple-{
      withHtml("""<comp1 id="test1"></comp1>"""){
        assert( jq("#test").children.length == 0 )
        angular.bootstrapWith[Component1]
        laterWithChildren("#test1"){ c =>
          assert( c.first().text() == "Hello, world!" )
        }
      }
    }

    'withDirectives-{
      withHtml("""<comp2 id="test2"></comp2>"""){
        assert( jq("#test2").children.length == 0 )
        angular.bootstrapWith[Component2]
        laterWithChildren("#test2"){ c =>
          assert( c.length == 4 ) // the first element is the template
        }
      }
    }
  }

}


@Component(
  selector = "comp1",
  template = "<span>Hello, world!</span>"
)
class Component1


@Component(
  selector = "comp2",
  template = """<span *ng-for="#name of names">{{name}}</span>""",
  directives = js.Array(angular.NgFor)
)
class Component2 {
  val names = js.Array("Luke","Lea","Han")
}

@Injectable
class Service1 {
  val names = js.Array("Luke","Lea","Han")
}
