//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import de.surfice.smacrotools.createJS
import rxjs.ValOrObs

@createJS
trait CanActivate {
  def canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): ValOrObs[Boolean]
}

@createJS
trait CanActivateChild {
  def canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): ValOrObs[Boolean]
}

@createJS
trait CanDeactivate[T] {
  def canDeactivate(component: T, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): ValOrObs[Boolean]
}

@createJS
trait CanLoad {
  def canLoad(route: Route): ValOrObs[Boolean]
}
