package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.authentication.CredentialsManager
import com.akitektuo.assignment8.resource.generalScript
import com.akitektuo.assignment8.resource.generalStyle
import com.akitektuo.assignment8.util.FormMethod
import com.akitektuo.assignment8.util.InputType
import com.akitektuo.assignment8.util.html
import com.akitektuo.assignment8.util.renderError
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/")
class IndexServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, response: HttpServletResponse) {
        if (req.session.getAttribute("user") != null) {
            return response.sendRedirect("/waiting-room")
        }

        response.renderHtml()
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = req.getParameter("username").trim()
        val password = req.getParameter("password").trim()

        if (!CredentialsManager.authenticate(username, password)) {
            return resp.renderHtml("Wrong credentials, try again!")
        }

        req.session.setAttribute("user", username)
        resp.sendRedirect("/waiting-room")
    }

    private fun HttpServletResponse.renderHtml(error: String? = null) = html {
        head {
            title("Assignment8 - Login")
            link("css/styles.css")
            link("js/script.js")
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
                content { renderError(error) }
                div("form-actions") {
                    button("Create account", style = "margin-right: 8px", onClick = "navigateTo('register')")
                    input(type = InputType.SUBMIT, value = "Login")
                }
            }
        }
    }
}