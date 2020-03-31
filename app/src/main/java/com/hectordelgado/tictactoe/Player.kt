package com.hectordelgado.tictactoe

/**
 *  Tic Tac Toe
 *
 *  @author Hector Delgado
 *
 *  Created on March 21, 2020.
 *  Copyright © 2020 Hector Delgado. All rights reserved.
 */
class Player(var playerName: String, var playerGamePiece: String, var gamePieceImage: Int) {
    fun copy(player: Player) {
        playerName = player.playerName
        playerGamePiece = player.playerGamePiece
        gamePieceImage = player.gamePieceImage
    }
}