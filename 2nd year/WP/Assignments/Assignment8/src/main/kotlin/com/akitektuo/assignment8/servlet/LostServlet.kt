package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.game.gameHost
import com.akitektuo.assignment8.util.InputType
import com.akitektuo.assignment8.util.html
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/lost")
class LostServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, response: HttpServletResponse) {
        val username = (req.session.getAttribute("user") as String?) ?: return response.sendRedirect("/")

        gameHost.quit(username)

        response.renderHtml()
    }

    private fun HttpServletResponse.renderHtml() = html {
        head {
            title("Assignment8 - Lost")
            link("css/styles.css")
            link("js/script.js")
        }
        body {
            form("container", "sign-out") {
                div("form-header") {
                    h2("You lost. :(")
                }
                div("form-actions") {
                    input(type = InputType.SUBMIT, style = "margin-right: 8px", value = "Quit")
                    button("Play another game", "navigateTo('waiting-room')", type = InputType.BUTTON)
                }
            }
        }
    }
}