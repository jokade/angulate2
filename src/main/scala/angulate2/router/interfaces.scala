//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import de.surfice.smacrotools.createJS
import rxjs.ValOrObs

@createJS
trait Resolve[T] {
  def resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): ValOrObs[T]
}
