package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.authentication.CredentialsManager
import com.akitektuo.assignment8.util.FormMethod
import com.akitektuo.assignment8.util.InputType
import com.akitektuo.assignment8.util.html
import com.akitektuo.assignment8.util.renderError
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/register")
class RegisterServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.session.getAttribute("user") != null) {
            return resp.sendRedirect("/waiting-room")
        }

        resp.renderHtml()
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = req.getParameter("username").trim()
        val password = req.getParameter("password").trim()
        val confirmPassword = req.getParameter("confirmPassword").trim()

        if (password != confirmPassword) {
            return resp.renderHtml("Passwords do not match!")
        }

        if (!CredentialsManager.register(username, password)) {
            return resp.renderHtml("The username is already in use!")
        }

        resp.sendRedirect("/")
    }

    private fun HttpServletResponse.renderHtml(error: String? = null) = html {
        head {
            title("Assignment8 - Create account")
            link("css/styles.css")
        }
        body {
            form("container", "register", FormMethod.POST) {
                div("form-header") {
                    h2("Create account")
                }
                label("Enter username:", "label-with-input") {
                    input("medium-input", true, InputType.TEXT, "username")
                }
                label("Enter password:", "label-with-input") {
                    input("medium-input", true, InputType.PASSWORD, "password")
                }
                label("Enter confirm password:", "label-with-input") {
                    input("medium-input", true, InputType.PASSWORD, "confirmPassword")
                }
                renderError(error)
                div("form-actions") {
                    input(type = InputType.SUBMIT, value = "Create account")
                }
            }
        }
    }
}