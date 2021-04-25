package com.akitektuo.assignment8.util

import com.akitektuo.assignment8.resource.generalScript
import com.akitektuo.assignment8.resource.generalStyle
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

    fun head(content: String = "", block: HtmlBuilder.() -> Unit = {}) {
        builder.append("<head>")

        builder.append(content)
        block(this)

        builder.append("</head>")
    }

    fun head(vararg links: String, block: HtmlBuilder.() -> Unit = {}) {
        builder.append("<head>")

        block(this)
        links.forEach { builder.append(it) }

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

    fun h2(content: String = "") {
        builder.append("<h2>$content</h2>")
    }

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

    private fun build() = builder.toString()
}

//fun HttpServletResponse.html(block: HtmlBuilder.() -> Unit = {}) {
//    contentType = "text/html"
//    writer.print(HtmlBuilder.html(block))
//}

fun main() {
    println(HtmlBuilder.html {
        head(generalStyle, generalScript) {
            title("Assignment8 - Login")
        }
        body {
            form("container", method = FormMethod.POST) {
                div("form-header") {
                    h2("Login")
                }
                label("Enter username:", "label-with-input") {
                    input("medium-input", true, InputType.TEXT, "username")
                }
                label("Enter password:", "label-with-input") {
                    input("medium-input", true, InputType.PASSWORD, "password")
                }
                content { renderError(null) }
                div("form-actions") {
                    button("Create account", style = "margin-right: 8px", onClick = "navigateTo('register')")
                    input(type = InputType.SUBMIT, value = "Login")
                }
            }
        }
    })
}