package dev.sergiobelda.todometer.common.resources

import kotlin.reflect.typeOf

//import dev.sergiobelda.navigation.compose.extended.annotation.NavArgumentType


enum class NavArgumentType{
    String,
    Int,
    Float,
    Boolean,
    Long
}

/**
 * A type that can be used to send multiple data
 */
class NavBundle internal constructor( val dataMap: Map<String, Any>){

    constructor(): this(emptyMap())

    private val _mapper = mutableMapOf<String, Pair<NavArgumentType, Any>>()

    companion object{
        val empty: NavBundle = NavBundle()
    }

    fun <T> put(key: String, value: T){
        when(value){
            is String -> {
                _mapper[key] = NavArgumentType.String to  value
            }
            is Int -> {
                _mapper[key] = NavArgumentType.Int to  value
            }

            is Float -> {
                _mapper[key] = NavArgumentType.Float to  value
            }
            is Long -> {
                _mapper[key] = NavArgumentType.Long to  value
            }

            is Boolean -> {
                _mapper[key] = NavArgumentType.Boolean to  value
            }
            else -> {
                throw UnsupportedOperationException("Unsupported type! --> ${value!!::class.simpleName}")
            }
        }

        println("put_mapper: $_mapper")
    }

    val arguments = _mapper.values

    inline fun <reified T> get(key: String): T? {
        return dataMap[key] as? T
    }

    inline fun <reified  T> getOrDefault(key: String, default: T): T {
        return dataMap[key] as? T ?: default
    }


    /**
     * Must send packaged instead of NavBundle
     */
    val packaged: String get() = _mapper.toString()

}


/**
 * asNavBundle should be called when the packed data has been received
 * at the destination
 */
fun String.asNavBundle(): NavBundle{
    if (equals("{}")) return NavBundle.empty
    val argMap : Map<String, Any> = substring(1, length-1).split("),").associate{
        val (key, `val`) = it.split("=")
        key to `val`.let { strPair ->
            val (type, arg) = strPair
                .replace(")", "")
                .replace("(", "")
                .split(",")

            val trimmedArg = arg.trim()
            println("asNavBundle: $type __ $arg")
            val argType = NavArgumentType.valueOf(type)
            val argument = when(argType){
                NavArgumentType.Float -> {
                    trimmedArg.toFloat()
                }
                NavArgumentType.Boolean -> {
                    trimmedArg.toBoolean()
                }
                NavArgumentType.Int -> {
                    trimmedArg.toInt()
                }
                NavArgumentType.Long -> {
                    trimmedArg.toLong()
                }
                NavArgumentType.String -> {
                    trimmedArg
                }
            }
            argument
        }
    }
    return NavBundle(argMap)
}
