package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.authentication.CredentialsManager
import com.akitektuo.assignment8.resource.generalStyle
import com.akitektuo.assignment8.util.renderError
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/register-servlet")
class RegisterServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse) {
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

    private fun HttpServletResponse.renderHtml(error: String? = null) {
        contentType = "text/html"
        writer.print(
            """
            <html>
                <head>
                    <title>Assignment8 - Create account</title>
                    $generalStyle
                </head>
                <body>
                    <form class="container" action="register-servlet" method="post">
                        <div class="form-header">
                            <h2>Create account</h2>
                        </div>
                        <label class="label-with-input">
                            Enter username:
                            <input class="medium-input" required type="text" name="username">
                        </label>
                        <label class="label-with-input">
                            Enter password:
                            <input class="medium-input" required type="password" name="password">
                        </label>
                        <label class="label-with-input">
                            Enter confirm password:
                            <input class="medium-input" required type="password" name="confirmPassword">
                        </label>
                        ${renderError(error)}
                        <div class="form-actions">
                            <input type="submit" value="Create account"/>
                        </div>
                    </form>
                </body>
            </html>
        """.trimIndent()
        )
    }
}