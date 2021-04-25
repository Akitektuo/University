package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.game.gameHost
import com.akitektuo.assignment8.resource.generalScript
import com.akitektuo.assignment8.resource.generalStyle
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/won")
class WonServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, response: HttpServletResponse) {
        val username = (req.session.getAttribute("user") as String?) ?: return response.sendRedirect("/")

        gameHost.quit(username)

        response.renderHtml()
    }

    private fun HttpServletResponse.renderHtml() {
        contentType = "text/html"
        writer.print(
            """
            <html>
                <head>
                    <title>Assignment8 - Won</title>
                    $generalStyle
                    $generalScript
                </head>
                <body>
                    <form class="container" action="sign-out">
                        <div class="form-header">
                            <h2>You won! :)</h2>
                        </div>
                        <div class="form-actions">
                            <input type="submit" style="margin-right: 8px" value="Quit" />
                            <button type="button" onclick="navigateTo('waiting-room')">Play another game</button>
                        </div>
                    </form>
                </body>
            </html>
        """.trimIndent()
        )
    }
}