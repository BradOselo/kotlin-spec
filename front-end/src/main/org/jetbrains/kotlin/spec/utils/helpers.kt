package org.jetbrains.kotlin.spec.utils

import org.w3c.dom.Location

fun String.format(vararg args: Any): String {
    return this.replace(Regex("""\{(\d+)}""", RegexOption.MULTILINE)) {
        val number = it.groupValues[1].toInt()

        if (args.size >= number) args[number - 1].toString() else ""
    }
}

fun String.escapeHtml(): String {
    return this.replace(Regex("&", RegexOption.MULTILINE), "&amp;")
        .replace(Regex("<", RegexOption.MULTILINE), "&lt;")
        .replace(Regex(">"), "&gt;")
        .replace(Regex("\"", RegexOption.MULTILINE), "&quot;")
        .replace(Regex("'", RegexOption.MULTILINE), "&#039;")
}

fun setValueByObjectPath(target: MutableMap<String, Any>, value: Any, path: String) {
    val pathComponents = path.split(".")
    var soughtForObj = target

    pathComponents.slice(0 until pathComponents.size - 1).forEach {
        soughtForObj = soughtForObj.getOrPut(it) { mutableMapOf<String, Any>() }.unsafeCast<MutableMap<String, Any>>()
    }

    soughtForObj[pathComponents.last()] = value
}

fun <T> getValueByObjectPath(obj: Map<String, Any>, path: String): T? = getValueByObjectPath(obj, path.split("."))

fun <T> getValueByObjectPath(obj: Map<String, Any>, path: List<String>): T? {
    var soughtForObj = obj

    path.forEach {
        if (it !in soughtForObj) return null
        soughtForObj = soughtForObj[it].unsafeCast<MutableMap<String, Any>>()
    }

    return soughtForObj.unsafeCast<T>()
}

val Location.searchMap: MutableMap<String, String>
    get() {
        val rawSearch = search.substring(1).split("&")
        val objURL = mutableMapOf<String, String>()

        rawSearch.forEach { param ->
            val paramComponents = param.split("=")
            objURL[paramComponents[0]] = paramComponents[1]
        }

        return objURL
    }
