package com.akitektuo.assignment8.servlet

import com.akitektuo.assignment8.game.Player
import com.akitektuo.assignment8.game.State
import com.akitektuo.assignment8.game.gameHost
import com.akitektuo.assignment8.util.*
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

    private fun HttpServletResponse.renderWait() = html {
        head {
            title("Assignment8 - Matchmaking")
            link("css/styles.css")
            link("js/reload.js", SECONDS_10)
        }
        body {
            form("container", "sign-out") {
                div("form-header") {
                    h2("Matchmaking for a game")
                }
                div("form-actions") {
                    input(type = InputType.SUBMIT, value = "Quit")
                }
            }
        }
    }

    private fun HttpServletResponse.renderGame(player: Player, otherPlayer: Player) = html {
        head {
            title("Assignment8 - Game with ${otherPlayer.username}")
            link("css/styles.css")
            link("css/board.css")
            link("js/script.js")
            link("js/reload.js", SECONDS_3)
        }
        body {
            form("container", "sign-out") {
                div("form-header") {
                    h2("Game with ${otherPlayer.username} - ${if (player.isTurn) "Your" else "Their"} turn")
                }
                renderBoards(player, otherPlayer)
                h4("Your health: ${player.health}")
                div("form-actions") {
                    input(type = InputType.SUBMIT, value = "Quit")
                }
            }
        }
    }

    private fun HtmlBuilder.renderBoards(player: Player, otherPlayer: Player) = div("boards-container") {
        div("board-container") {
            content { renderBoard(player) }
        }
        div("board-container") {
            content { renderEnemyBoard(otherPlayer) }
        }
    }

    private fun renderBoard(player: Player) = player.mapBoard({ """<div class="board-row">${it}</div>""" }) {
        """<div class="board-cell ${it.cssClass}"></div>"""
    }

    private fun renderEnemyBoard(player: Player) =
        player.mapEnemyBoard({ """<div class="board-row">${it}</div>""" }) { state, i, j ->
            """
                <div class="board-cell ${state.cssClass}${if (state == State.EMPTY) " clickable" else ""}"
                    ${if (state == State.EMPTY) "onClick=\"navigateTo('game?i=$i&j=$j')\"" else ""}></div>
            """.trimMargin()
        }
}