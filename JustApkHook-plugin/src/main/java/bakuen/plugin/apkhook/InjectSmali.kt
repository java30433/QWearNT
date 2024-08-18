package bakuen.plugin.apkhook

sealed class InjectType {
    class ReplaceStr(val ori: String, val new: String) : InjectType()
    class ReplaceLine(val line: Int, val value: String) : InjectType()
}
class InjectSmali(
    val path: String
) {
    val injects = mutableListOf<InjectType>()
}