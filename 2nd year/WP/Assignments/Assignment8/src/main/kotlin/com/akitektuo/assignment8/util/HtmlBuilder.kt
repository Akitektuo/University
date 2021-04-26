package com.akitektuo.assignment8.util

import java.io.File
import java.nio.file.Files
import javax.servlet.http.HttpServletResponse

enum class FormMethod(val value: String) {
    GET("get"),
    POST("post")
}

enum class InputType(val value: String) {
    TEXT("text"),
    PASSWORD("password"),
    SUBMIT("submit")
}

class HtmlBuilder(private val builder: StringBuilder = StringBuilder()) {
    companion object {
        fun html(block: HtmlBuilder.() -> Unit = {}) = "<html>${HtmlBuilder().apply { block() }.build()}</html>"
    }

    private fun build() = builder.toString()

    private fun readContents(source: String) = javaClass.classLoader.getResource(source)?.let {
        Files.readString(File(it.file).toPath())
    }

    private fun hx(x: Int, content: String = "") {
        builder.append("<h$x>$content</h$x>")
    }

    fun head(block: HtmlBuilder.() -> Unit = {}) {
        builder.append("<head>")

        block(this)

        builder.append("</head>")
    }

    fun body(block: HtmlBuilder.() -> Unit = {}) {
        builder.append("<body>")

        block(this)

        builder.append("</body>")
    }

    fun title(content: String = "") {
        builder.append("<title>$content</title>")
    }

    fun form(
        cssClass: String? = null,
        action: String? = null,
        method: FormMethod? = null,
        block: HtmlBuilder.() -> Unit = {}
    ) {
        builder.append("<form")
        cssClass?.let { builder.append(" class=\"$cssClass\"") }
        action?.let { builder.append(" action=\"$action\"") }
        method?.let { builder.append(" method=\"${method.value}\"") }
        builder.append(">")

        block(this)

        builder.append("</form>")
    }

    fun div(cssClass: String? = null, block: HtmlBuilder.() -> Unit = {}) {
        builder.append("<div")
        cssClass?.let { builder.append(" class=\"$cssClass\"") }
        builder.append(">")

        block(this)

        builder.append("</div>")
    }

    fun h1(content: String = "") = hx(1, content)

    fun h2(content: String = "") = hx(2, content)

    fun label(content: String = "", cssClass: String? = null, block: HtmlBuilder.() -> Unit = {}) {
        builder.append("<label")
        cssClass?.let { builder.append(" class=\"$cssClass\"") }
        builder.append(">")

        builder.append(content)
        block(this)

        builder.append("</label>")
    }

    fun input(
        cssClass: String? = null,
        required: Boolean = false,
        type: InputType? = null,
        name: String? = null,
        value: String? = null
    ) {
        builder.append("<input")
        cssClass?.let { builder.append(" class=\"$cssClass\"") }
        if (required) builder.append(" required")
        type?.let { builder.append(" type=\"${type.value}\"") }
        name?.let { builder.append(" name=\"$name\"") }
        value?.let { builder.append(" value=\"$value\"") }
        builder.append(">")
    }

    fun content(block: HtmlBuilder.() -> String) {
        builder.append(block(this))
    }

    fun button(
        content: String = "",
        cssClass: String? = null,
        style: String? = null,
        onClick: String? = null
    ) {
        builder.append("<button")
        cssClass?.let { builder.append(" class=\"$cssClass\"") }
        style?.let { builder.append(" style=\"$style\"") }
        onClick?.let { builder.append(" onclick=\"$onClick\"") }
        builder.append(">")

        builder.append(content)

        builder.append("</button>")
    }

    fun link(source: String, vararg parameters: Any) {
        var content = readContents(source) ?: ""

        parameters.forEachIndexed { i, parameter -> content = content.replace("$$i", parameter.toString()) }

        if (source.endsWith(".css")) {
            builder.appendLine("<style>")
            builder.appendLine(content)
            builder.append("</style>")
            return
        }
        if (source.endsWith(".js")) {
            builder.appendLine("<script>")
            builder.appendLine(content)
            builder.append("</script>")
            return
        }
    }

    fun span(content: String = "", cssClass: String? = null, block: HtmlBuilder.() -> Unit = {}) {
        builder.append("<span")
        cssClass?.let { builder.append(" class=\"$cssClass\"") }
        builder.append(">")

        builder.append(content)
        block(this)

        builder.append("</span>")
    }
}

fun HttpServletResponse.html(block: HtmlBuilder.() -> Unit = {}) {
    contentType = "text/html"
    writer.print(HtmlBuilder.html(block))
}