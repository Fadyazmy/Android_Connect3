package io.github.fadyazmy.connect3;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow, 1 = red;

    int activePlayer = 0;

    boolean gameIsActive = true;
    // 2 = unplayed
    int[] arr = {2,2,2,2,2,2,2,2,2};
    public void dropIn (View v){
        ImageView counter = (ImageView)v;



        // Getting index of the coin
        int index = Integer.parseInt(counter.getTag().toString());

        if (arr[index] == 2 && gameIsActive) {
            // Record which player interacted with which index
            arr[index] = activePlayer;

            // Move the coin to the top
            counter.setTranslationY(-1000f);
            Boolean win = checkGame(arr, activePlayer);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                activePlayer = 0;
                counter.setImageResource(R.drawable.red);
            }

            // Move the coin again over 300ms
            counter.animate().translationYBy(1000f).setDuration(300);

            //Announces who the winner is!
            if (win){
                // Game no longer active
                gameIsActive = false;

                System.out.println("YOU WON!!: " + activePlayer);

                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessageID);
                winnerMessage.setText("Player " + activePlayer+ " has won!");
                // Player wins! Play agin?
                LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayoutID);
                layout.setVisibility(View.VISIBLE);

            }
        }
        else {
            boolean gameIsOver = true;
            for (int r : arr)
                if (r == 2) gameIsOver = false;
            if (gameIsOver){
                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessageID);
                winnerMessage.setText("Draw!");
                // Player wins! Play agin?
                LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayoutID);
                layout.setVisibility(View.VISIBLE);
            }

        }

    }

    public void playAgain(View v){

        //Remove the modal
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayoutID);
        layout.setVisibility(View.INVISIBLE);

        //Reset the variables
        gameIsActive = true;
        activePlayer = 0;
        for (int i = 0; i < arr.length; i++)
            arr[i] = 2;
        GridLayout gridLayoutID = (GridLayout) findViewById(R.id.gridID);
        for (int i = 0; i< gridLayoutID.getChildCount(); i++){

            // 0 inside setImageResource resets the coins back
            ((ImageView) gridLayoutID.getChildAt(i)).setImageResource(0);
        }

    }

    public Boolean checkGame(int[] arr, int player){
        if(isDiagonal(arr, player) || isHorizontal(arr, player) || isVertical(arr, player))
            return true;
        else
            return false;

    }

    public Boolean isHorizontal(int[] arr, int player){
        for(int i = 0; i <= arr.length - 2; i++){
            if(arr[i] == player)
                if (arr[i + 1] == player)
                    if(arr[i + 2] == player)
                        return true;

        }
        return false;
    }

    public Boolean isVertical(int[] arr, int player){
        for (int i= 0; i < (arr.length / 3); i++){
            if(arr[i] == player)
                if (arr[i+ 3] == player)
                    if (arr[i+6] == player)
                        return true;
        }
        return false;
    }

    public Boolean isDiagonal(int[] arr, int player){
        if (arr[0] == arr[4] && arr[4] == arr[8] && arr[8] == player
                || arr[2] == arr[4] && arr[4] == arr[6] && arr[6] == player)
            return true;
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
    }
}
