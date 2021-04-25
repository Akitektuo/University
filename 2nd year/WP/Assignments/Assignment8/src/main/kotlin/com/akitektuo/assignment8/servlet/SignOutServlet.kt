package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.game.gameHost
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/sign-out")
class SignOutServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        (req.session.getAttribute("user") as String?)?.let {
            gameHost.quit(it)
            req.session.setAttribute("user", null)
        }

        resp.sendRedirect("/")
    }
}