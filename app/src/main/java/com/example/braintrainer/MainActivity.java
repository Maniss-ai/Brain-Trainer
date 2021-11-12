package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button startButton, button0, button1, button2, button3, playAgainButton;
    private TextView questionTextView, scoreTextView, resultTextView, timerTextView, finalResultTextView;
    private View constraintLayout, gridLayout;

    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", };

    ArrayList<Integer> options = new ArrayList<>();
    int locationOfCorrectAnswer, score = 0, totalQuestion = 0;

    int generateColor() {
        Random random = new Random();
        String colorName = mColorArray[random.nextInt(mColorArray.length)];
        int colorResourceName = getResources().getIdentifier(colorName, "color", getApplicationContext().getPackageName());
        int colorRes = ContextCompat.getColor(this, colorResourceName);

        return colorRes;
    }

//    Button Color Changer ....
    void ButtonColorChanger() {
        button0.setBackgroundColor(generateColor());
        button1.setBackgroundColor(generateColor());
        button2.setBackgroundColor(generateColor());
        button2.setBackgroundColor(generateColor());
    }

//    Start The Game ....
    public void Start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
    }

//    Choose Options .... and Show Result ....
    public void chooseOption(View view) {
        ButtonColorChanger();
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong!");
        }
        totalQuestion++;
        scoreTextView.setText(score + "/" + totalQuestion);
        generateQuestion();
    }

//    Generate Questions ....
    public void generateQuestion() {
        int a, b;
        Random random = new Random();
        a = random.nextInt(21);
        b = random.nextInt(21);
        String question = a + " + " + b;

        questionTextView.setText(question);

        locationOfCorrectAnswer = random.nextInt(4);

        options.clear();

        int incorrectAns, correctAns = a+b;
        for(int i = 0; i < 4; i++) {
            if(locationOfCorrectAnswer == i) {
                options.add(correctAns);
            } else {
                incorrectAns = random.nextInt(51);
                while(incorrectAns == correctAns) {
                    incorrectAns = random.nextInt(51);
                }
                options.add(incorrectAns);
            }
        }

        button0.setText(Integer.toString(options.get(0)));
        button1.setText(Integer.toString(options.get(1)));
        button2.setText(Integer.toString(options.get(2)));
        button3.setText(Integer.toString(options.get(3)));
    }

//    Update Timer ....
    public void updateTimer() {
        CountDownTimer timer = new CountDownTimer(6100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                int randomColor = generateColor();
                finalResultTextView.setBackgroundColor(randomColor);

                resultTextView.setVisibility(View.INVISIBLE);
                gridLayout.setVisibility(View.INVISIBLE);
                finalResultTextView.setVisibility(View.VISIBLE);

                playAgainButton.setVisibility(View.VISIBLE);
                finalResultTextView.setText("Your Score: " + score + "/" + totalQuestion);
                finalResultTextView.animate().rotationBy(1800f).translationYBy(2000).setDuration(1000);

            }
        }.start();
    }

//    Play Again ....
    public void playAgain(View view) {
        score = 0;
        totalQuestion = 0;
        resultTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        finalResultTextView.setVisibility(View.INVISIBLE);
        finalResultTextView.animate().translationY(-2000);

        gridLayout.setVisibility(View.VISIBLE);

        scoreTextView.setText("0/0");
        timerTextView.setText("60s");
        resultTextView.setText("");
        updateTimer();
        generateQuestion();
    }

//    On Create Method ....
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        startButton.setBackgroundColor(generateColor());

        questionTextView = findViewById(R.id.questionTextView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);

        constraintLayout = findViewById(R.id.constraintLayout);
        gridLayout = findViewById(R.id.gridLayout);

        scoreTextView = findViewById(R.id.scoreTextView);
        resultTextView = findViewById(R.id.resultTextView);
        timerTextView = findViewById(R.id.timerTextView);
        finalResultTextView = findViewById(R.id.finalResultTextView);

    }


}