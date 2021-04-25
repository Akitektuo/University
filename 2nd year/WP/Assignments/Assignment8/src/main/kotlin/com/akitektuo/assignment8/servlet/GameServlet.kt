package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.game.Player
import com.akitektuo.assignment8.game.gameHost
import com.akitektuo.assignment8.resource.boardStyle
import com.akitektuo.assignment8.resource.generalScript
import com.akitektuo.assignment8.resource.generalStyle
import com.akitektuo.assignment8.resource.reloadAfter
import com.akitektuo.assignment8.util.SECONDS_10
import com.akitektuo.assignment8.util.SECONDS_3
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/game")
class GameServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, response: HttpServletResponse) {
        val username = (req.session.getAttribute("user") as String?) ?: return response.sendRedirect("/")

        val player = gameHost.getPlayer(username) ?: return response.sendRedirect("/waiting-room")

        player.isReady = true

        if (!gameHost.isReady) {
            return response.renderWait()
        }

        val otherPlayer = gameHost.getOtherPlayer(player) ?: return

        if (player.isLoss) {
            return response.sendRedirect("/lost")
        }

        if (otherPlayer.isLoss) {
            return response.sendRedirect("/won")
        }

        val i = req.getParameter("i")?.toIntOrNull()
        val j = req.getParameter("j")?.toIntOrNull()

        if (i != null && j != null) {
            player.fireAt(otherPlayer, i, j)
            return response.sendRedirect("/game")
        }

        response.renderGame(player, otherPlayer)
    }

    private fun HttpServletResponse.renderWait() {
        contentType = "text/html"
        writer.print(
            """
            <html>
                <head>
                    <title>Assignment8 - Matchmaking</title>
                    $generalStyle
                    ${reloadAfter(SECONDS_10)}
                </head>
                <body>
                    <form class="container" action="sign-out">
                        <div class="form-header">
                            <h2>Matchmaking for a game</h2>
                        </div>
                        <div class="form-actions">
                            <input type="submit" value="Quit" />
                        </div>
                    </form>
                </body>
            </html>
        """.trimIndent()
        )
    }

    private fun HttpServletResponse.renderGame(player: Player, otherPlayer: Player) {
        contentType = "text/html"
        writer.print(
            """
            <html>
                <head>
                    <title>Assignment8 - Game with ${otherPlayer.username}</title>
                    $generalStyle
                    $boardStyle
                    $generalScript
                    ${reloadAfter(SECONDS_3)}
                </head>
                <body>
                    <form class="container" action="sign-out">
                        <div class="form-header">
                            <h2>Game with ${otherPlayer.username} - ${if (player.isTurn) "Your" else "Their"} turn</h2>
                        </div>
                        ${renderBoards(player, otherPlayer)}
                        <h4>Your health: ${player.health}<h4>
                        <div class="form-actions">
                            <input type="submit" value="Quit" />
                        </div>
                    </form>
                </body>
            </html>
        """.trimIndent()
        )
    }

    private fun renderBoards(player: Player, otherPlayer: Player) = """
        <div class="boards-container">
            <div class="board-container">
                ${renderBoard(player)}
            </div>
            <div class="board-container">
                ${renderEnemyBoard(otherPlayer)}
            </div>
        </div>
    """.trimIndent()

    private fun renderBoard(player: Player) = player.mapBoard({ """<div class="board-row">${it}</div>""" }) {
        """<div class="board-cell ${it.cssClass}"></div>"""
    }

    private fun renderEnemyBoard(player: Player) =
        player.mapEnemyBoard({ """<div class="board-row">${it}</div>""" }) { state, i, j ->
            """<div class="board-cell ${state.cssClass} clickable" onClick="navigateTo('game?i=$i&j=$j')"></div>"""
        }
}