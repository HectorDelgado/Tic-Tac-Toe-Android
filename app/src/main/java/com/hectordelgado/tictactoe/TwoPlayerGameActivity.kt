package com.hectordelgado.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_two_player_game.*
import java.util.*
import kotlin.random.Random

class TwoPlayerGameActivity : AppCompatActivity() {

    private var player1Turn = Random.nextInt(0, 100) > 50
    private var gameIsActive = true
    private lateinit var player1: Player
    private lateinit var player2: Player
    private val currentPlayer = Player("", "", R.drawable.emptylocation)
    private val EMPTY_GAME_PIECE = "#"
    private val gameBoard = mutableListOf("#", "#", "#", "#", "#", "#", "#", "#", "#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player_game)

        player1 = Player(playerName = getString(R.string.twoPlayerGame_player1Name), playerGamePiece = "X", gamePieceImage = R.drawable.crosslocation)
        player2 = Player(playerName = getString(R.string.twoPlayerGame_player2Name), playerGamePiece = "O", gamePieceImage = R.drawable.circlelocation)

        switchPlayer()
    }

    fun gameBtnPressed(view: View) {
        if (gameIsActive) {
            val boardLocation = view.tag.toString().toInt() - 1

            if (positionIsAvailable(boardLocation)) {
                gameBoard[boardLocation] = currentPlayer.playerGamePiece

                val img = view as ImageView
                img.setImageResource(currentPlayer.gamePieceImage)

                if (!gameStillActive(currentPlayer.playerGamePiece)) {
                    gameOver(String.format(Locale.getDefault(), getString(R.string.twoPlayerGame_gameOver_win_format), currentPlayer.playerName))
                } else if (gameIsDraw()) {
                    gameOver(String.format(Locale.getDefault(), getString(R.string.twoPlayerGame_gameOver_draw)))
                } else {
                    switchPlayer()
                }


            } else {
                Toast.makeText(this, getString(R.string.twoPlayerGame_locationTaken), Toast.LENGTH_LONG).show()
            }
        } else {
            restartGame()
        }
    }

    /**
     * Switches between player1/player2 and vice-versa.
     * Updates the title and subtitle to display the current player.
     */
    private fun switchPlayer() {
        player1Turn = !player1Turn
        currentPlayer.copy(if (player1Turn) player1 else player2)
        val title = String.format(Locale.getDefault(), getString(R.string.twoPlayerGame_title_format), currentPlayer.playerName)
        val subtitle = String.format(Locale.getDefault(), getString(R.string.twoPlayerGame_subtitle_format), currentPlayer.playerGamePiece)
        titleTextView.text = title
        subtitleTextView.text = subtitle
    }

    /**
     * Checks if a game board position is available (empty).
     */
    private fun positionIsAvailable(position: Int) =
        gameBoard[position] == EMPTY_GAME_PIECE

    /**
     * Checks if a player has won the game.
     * Uses the common '3 in a row' to check horizontally, vertically, and diagonally.
     */
    private fun gameStillActive(gamePiece: String): Boolean {
        val horizontalStrategy1 = gameBoard[0] == gamePiece && gameBoard[1] == gamePiece && gameBoard[2] == gamePiece
        val horizontalStrategy2 = gameBoard[3] == gamePiece && gameBoard[4] == gamePiece && gameBoard[5] == gamePiece
        val horizontalStrategy3 = gameBoard[6] == gamePiece && gameBoard[7] == gamePiece && gameBoard[8] == gamePiece

        val verticalStrategy1 = gameBoard[0] == gamePiece && gameBoard[3] == gamePiece && gameBoard[6] == gamePiece
        val verticalStrategy2 = gameBoard[1] == gamePiece && gameBoard[4] == gamePiece && gameBoard[7] == gamePiece
        val verticalStrategy3 = gameBoard[2] == gamePiece && gameBoard[5] == gamePiece && gameBoard[8] == gamePiece

        val diagonalStrategy1 = gameBoard[0] == gamePiece &&  gameBoard[4] == gamePiece && gameBoard[8] == gamePiece
        val diagonalStrategy2 = gameBoard[2] == gamePiece &&  gameBoard[4] == gamePiece && gameBoard[6] == gamePiece

        return !horizontalStrategy1 && !horizontalStrategy2 && !horizontalStrategy3 &&
                !verticalStrategy1 && !verticalStrategy2 && !verticalStrategy3 &&
                !diagonalStrategy1 && !diagonalStrategy2
    }

    /**
     * Checks if the game is a draw (all locations are filled).
     */
    private fun gameIsDraw(): Boolean {
        gameBoard.forEach { location ->
            if (location == EMPTY_GAME_PIECE)
                return false
        }
        return true
    }

    /**
     * Displays a final message and sets the game to being inactive.
     */
    private fun gameOver(title: String) {

        val subtitle = String.format(Locale.getDefault(), getString(R.string.twoPlayerGame_finalMessageSubtitle_format))
        titleTextView.text = title
        subtitleTextView.text = subtitle
        gameIsActive = false
    }

    /**
     * Restarts the game and resets the game board.
     */
    private fun restartGame() {
        gameBoard.fill(EMPTY_GAME_PIECE)

        btn1.setImageResource(R.drawable.emptylocation)
        btn2.setImageResource(R.drawable.emptylocation)
        btn3.setImageResource(R.drawable.emptylocation)
        btn4.setImageResource(R.drawable.emptylocation)
        btn5.setImageResource(R.drawable.emptylocation)
        btn6.setImageResource(R.drawable.emptylocation)
        btn7.setImageResource(R.drawable.emptylocation)
        btn8.setImageResource(R.drawable.emptylocation)
        btn9.setImageResource(R.drawable.emptylocation)

        gameIsActive = true

        switchPlayer()
    }
}
