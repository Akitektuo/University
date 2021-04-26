package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.game.Player
import com.akitektuo.assignment8.game.gameHost
import com.akitektuo.assignment8.resource.boardStyle
import com.akitektuo.assignment8.resource.generalScript
import com.akitektuo.assignment8.resource.generalStyle
import com.akitektuo.assignment8.util.InputType
import com.akitektuo.assignment8.util.html
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/game-start")
class GameStartServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = (req.session.getAttribute("user") as String?) ?: return resp.sendRedirect("/")

        val player = gameHost.getPlayer(username) ?: gameHost.registerPlayer(username)
        ?: return resp.sendRedirect("/waiting-room")

        player.newBoard()

        resp.renderHtml(username, renderBoard(player))
    }

    private fun HttpServletResponse.renderHtml(username: String, board: String) = html {
        head {
            title("Assignment8 - Game start")
            link("css/styles.css")
            link("css/board.css")
            link("js/script.js")
        }
        body {
            form("container", "game-start") {
                div("form-header") {
                    h1("Your board, $username")
                }
                div("board-container") {
                    content { board }
                }
                div("form-actions") {
                    button(
                        "Sign out",
                        "navigateTo('sign-out')",
                        type = InputType.BUTTON,
                        style = "margin-right: 8px"
                    )
                    input(type = InputType.SUBMIT, value = "Regenerate", style = "margin-right: 8px")
                    button("Ready", "navigateTo('game')", type = InputType.BUTTON)
                }
            }
        }
    }

    private fun renderBoard(player: Player) = player.mapBoard({ """<div class="board-row">${it}</div>""" }) {
        """<div class="board-cell ${it.cssClass}"></div>"""
    }
}