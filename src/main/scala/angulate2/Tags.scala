package angulate2

sealed trait ~~[A, B <: ComponentParamGenerator]

sealed trait ComponentParamGenerator
trait Provider extends ComponentParamGenerator