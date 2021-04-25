package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.authentication.CredentialsManager
import com.akitektuo.assignment8.resource.generalScript
import com.akitektuo.assignment8.resource.generalStyle
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

    private fun HttpServletResponse.renderHtml(error: String? = null) {
        contentType = "text/html"
        writer.print(
            """
            <html>
                <head>
                    <title>Assignment8 - Login</title>
                    $generalStyle
                    $generalScript
                </head>
                <body>
                    <form class="container" method="post">
                        <div class="form-header">
                            <h2>Login</h2>
                        </div>
                        <label class="label-with-input">
                            Enter username:
                            <input class="medium-input" required type="text" name="username">
                        </label>
                        <label class="label-with-input">
                            Enter password:
                            <input class="medium-input" required type="password" name="password">
                        </label>
                        ${renderError(error)}
                        <div class="form-actions">
                            <button style="margin-right: 8px" onclick="navigateTo('register')">
                                Create account
                            </button>
                            <input type="submit" value="Login"/>
                        </div>
                    </form>
                </body>
            </html>
        """.trimIndent()
        )
    }
}