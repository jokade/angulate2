//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.dynamic

import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.std._
import rxjs.Observable
import slogging.LazyLogging

import scala.scalajs.js

@Injectable
@classModeScala
class TemplateLoader(http: Http) extends LazyLogging {

  def loadTemplate(url: String): Observable[Template] = {
    logger.debug("loading template '{}'",url)
    http
      .get(url)
      .map( (tpl,_) => Template(url,tpl.text()) )
      .onError(Template("__error__",s"ERROR: could not load template '$url'",cache = false)){ err =>
        logger.error("Could not load template from URL '{}': {}",url,err)
      }
  }

}


case class Template(id: String,
                    template: String,
                    cache: Boolean = true,
                    requiredModules: js.Array[js.Any] = js.Array())

