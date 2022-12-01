package com.aasif.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.aasif.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var gameActive = true

    /* Player representation
     0 - X
     1 - O
     */
    private var activePlayer = 0
    private var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    /* State meanings:
     0 - X
     1 - O
     2 - Null*/
    private var winPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() = binding.apply {
        buttonReset.setOnClickListener { gameReset() }
        imageView0.setOnClickListener { playerTap(imageView0) }
        imageView1.setOnClickListener { playerTap(imageView1) }
        imageView2.setOnClickListener { playerTap(imageView2) }
        imageView3.setOnClickListener { playerTap(imageView3) }
        imageView4.setOnClickListener { playerTap(imageView4) }
        imageView5.setOnClickListener { playerTap(imageView5) }
        imageView6.setOnClickListener { playerTap(imageView6) }
        imageView7.setOnClickListener { playerTap(imageView7) }
        imageView8.setOnClickListener { playerTap(imageView8) }
    }

    private fun playerTap(view: View) {
        val img = view as ImageView
        val tappedImage = img.tag.toString().toInt()
        if (!gameActive) {
            gameReset()
        }
        if (gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer
            img.translationY = -1000f
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.cancel)
                activePlayer = 1
                binding.status.text = getString(R.string.o_turn)
            } else {
                img.setImageResource(R.drawable.ic_circle)
                activePlayer = 0
                binding.status.text = getString(R.string.x_turn)
            }
            img.animate().translationYBy(1000f).duration = 200
        }
        // Check if any player has won
        for (winPosition in winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]]
                && gameState[winPosition[1]] == gameState[winPosition[2]]
                && gameState[winPosition[0]] != 2
            ) {
                // Somebody has won! - Find out who!
                gameActive = false
                val winnerStr =
                    if (gameState[winPosition[0]] == 0) getString(R.string.x_won)
                    else getString(R.string.o_won)
                // Update the status bar for winner announcement
                winnerStr.also { binding.status.text = it }
            }
        }
    }

    private fun gameReset() = binding.apply {
        gameActive = true
        activePlayer = 0
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        imageView0.setImageResource(0)
        imageView1.setImageResource(0)
        imageView2.setImageResource(0)
        imageView3.setImageResource(0)
        imageView4.setImageResource(0)
        imageView5.setImageResource(0)
        imageView6.setImageResource(0)
        imageView7.setImageResource(0)
        imageView8.setImageResource(0)
        status.text = getString(R.string.status)
    }
}