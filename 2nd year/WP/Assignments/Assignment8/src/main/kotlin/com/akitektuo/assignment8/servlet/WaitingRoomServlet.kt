package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.database.waitingQueue
import com.akitektuo.assignment8.resource.generalStyle
import com.akitektuo.assignment8.resource.reloadAfter
import com.akitektuo.assignment8.util.SECONDS_10
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/waiting-room")
class WaitingRoomServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = (req.session.getAttribute("user") as String?) ?: return resp.sendRedirect("/")
        val position = waitingQueue.addToQueue(username)

        if (position == 0) {
            return resp.sendRedirect("/game-start")
        }

        resp.renderHtml(username, position)
    }

    private fun HttpServletResponse.renderHtml(username: String, position: Int) {
        contentType = "text/html"
        writer.print(
            """
            <html>
                <head>
                    <title>Assignment8 - Waiting room</title>
                    $generalStyle
                    ${reloadAfter(SECONDS_10)}
                </head>
                <body>
                    <form class="container" action="sign-out" method="get">
                        <div class="form-header">
                            <h1>Hello $username, you have the queue position of $position</h1>
                            <div class="form-actions">
                                <input type="submit" value="Sign out" />
                            </div>
                        </div>
                    </form>
                </body>
            </html>
        """.trimIndent()
        )
    }
}