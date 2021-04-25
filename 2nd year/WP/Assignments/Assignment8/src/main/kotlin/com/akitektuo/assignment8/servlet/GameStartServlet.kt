package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.game.Player
import com.akitektuo.assignment8.game.gameHost
import com.akitektuo.assignment8.resource.boardStyle
import com.akitektuo.assignment8.resource.generalScript
import com.akitektuo.assignment8.resource.generalStyle
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

    private fun HttpServletResponse.renderHtml(username: String, board: String) {
        contentType = "text/html"
        writer.print(
            """
            <html>
                <head>
                    <title>Assignment8 - Game start</title>
                    $generalStyle
                    $boardStyle
                    $generalScript
                </head>
                <body>
                    <form class="container" method="get" action="game-start">
                        <div class="form-header">
                            <h1>Your board, $username</h1>
                        </div>
                        <div class="board-container">
                            $board
                        </div>
                        <div class="form-actions">
                            <button type="button" style="margin-right: 8px" onclick="navigateTo('sign-out')">
                                Sign out
                            </button>
                            <input type="submit" value="Regenerate" style="margin-right: 8px" />
                            <button type="button" onclick="navigateTo('game')">Ready</button>
                        </div>
                    </form>
                </body>
            </html>
        """.trimIndent()
        )
    }

    private fun renderBoard(player: Player) = player.mapBoard({ """<div class="board-row">${it}</div>""" }) {
        """<div class="board-cell ${it.cssClass}"></div>"""
    }
}