package edu.osucascades.cs492.fixme3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "cs492-fixme03";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CORRECT = "correct_count";
    private static final String KEY_TOTAL = "total_count";

    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private Button mFalseButton;
    private Button mTrueButton;
    private TextView mQuestionTextView;


    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_bend, false),
            new Question(R.string.question_corvallis, true),
            new Question(R.string.question_eugene, true),
            new Question(R.string.question_beer, false),
            new Question(R.string.question_canal, true),
            new Question(R.string.question_columbia, false),
            new Question(R.string.question_deschutes, true),
            new Question(R.string.question_lake, false),
            new Question(R.string.question_pilot, false),
            new Question(R.string.question_flag, false),
            new Question(R.string.question_nut, true),
    };

    private int mCurrentIndex = 0;
    private int mCorrectCount = 0;
    private int mTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCorrectCount = savedInstanceState.getInt(KEY_CORRECT, 0);
            mTotalCount = savedInstanceState.getInt(KEY_TOTAL, 0);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                mFalseButton.setEnabled(true);
                mTrueButton.setEnabled(true);
            }
        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = mCurrentIndex > 0 ? (mCurrentIndex - 1) % mQuestionBank.length : mQuestionBank.length - 1;
                updateQuestion();
                mFalseButton.setEnabled(true);
                mTrueButton.setEnabled(true);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_CORRECT, mCorrectCount);
        savedInstanceState.putInt(KEY_TOTAL, mTotalCount);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            Log.v(TAG, "correct answer given");
            mCorrectCount++;
        } else {
            messageResId = R.string.incorrect_toast;
            Log.v(TAG, "incorrect answer given");
        }
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);
        mTotalCount++;

        Toast toast = Toast.makeText(MainActivity.this
                , messageResId
                , Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
        toast.show();

        if (mTotalCount >= mQuestionBank.length) {

            Toast done_toast = Toast.makeText(MainActivity.this
                    , "You got " + mCorrectCount + " out of "
                        + mTotalCount + " correct."
                    , Toast.LENGTH_LONG);
            done_toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
            done_toast.show();

            mCorrectCount = 0;
            mTotalCount = 0;
        }
    }
}
