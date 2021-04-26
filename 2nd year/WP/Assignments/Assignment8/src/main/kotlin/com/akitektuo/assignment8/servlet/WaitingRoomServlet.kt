package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.database.waitingQueue
import com.akitektuo.assignment8.util.InputType
import com.akitektuo.assignment8.util.SECONDS_10
import com.akitektuo.assignment8.util.html
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

    private fun HttpServletResponse.renderHtml(username: String, position: Int) = html {
        head {
            title("Assignment8 - Waiting room")
            link("css/styles.css")
            link("js/reload.js", SECONDS_10)
        }
        body {
            form("container", "sign-out") {
                div("form-header") {
                    h1("Hello $username, you have the queue position of $position")
                    div("form-actions") {
                        input(type = InputType.SUBMIT, value = "Sign out")
                    }
                }
            }
        }
    }
}