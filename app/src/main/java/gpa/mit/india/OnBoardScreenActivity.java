package gpa.mit.india;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoardScreenActivity extends AppCompatActivity
{
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private int mCurrentPage;
    private Button mNextButton;
    private Button mBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board_screen);

        mSlideViewPager = findViewById(R.id.slider);
        mDotLayout = findViewById(R.id.dots_id);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        mNextButton = findViewById(R.id.next_button);
        mBackButton = findViewById(R.id.previous_button);

        if (restorePrefData())
        {
            Intent mainActivity = new Intent(OnBoardScreenActivity.this,MainActivity.class );
            startActivity(mainActivity);
            finish();
        }

        AddDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mNextButton.getText().toString();
                Log.d("CHECK_TEXT",text);
                if(text.equals("Finish"))
                {
                    Intent MainIntent = new Intent(OnBoardScreenActivity.this,MainActivity.class);
                    startActivity(MainIntent);
                    savePrefsData();
                    finish();
                }
                else
                {
                    mSlideViewPager.setCurrentItem(mCurrentPage+1);
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
    }

    private boolean restorePrefData()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        boolean isIntroActivityOpenedBefore = pref.getBoolean("IsIntroOpened",false);
        return  isIntroActivityOpenedBefore;
    }

    private void savePrefsData()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("IsIntroOpened",true);
        editor.commit();
    }

    public void AddDotsIndicator(int position)
    {
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++)
        {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length>0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(int position) {
            AddDotsIndicator(position);
            mCurrentPage = position;

            if (position == 0)
            {
                mNextButton.setEnabled(true);
                mBackButton.setEnabled(false);
                mBackButton.setVisibility(View.INVISIBLE);

                mNextButton.setText("Next");
                mBackButton.setText("");
            }
            else if(position == mDots.length-1)
            {
                mNextButton.setEnabled(true);
                mBackButton.setEnabled(true);
                mBackButton.setVisibility(View.VISIBLE);

                mNextButton.setText("Finish");
                mBackButton.setText("Back");
            }
            else
            {
                mNextButton.setEnabled(true);
                mBackButton.setEnabled(true);
                mBackButton.setVisibility(View.VISIBLE);

                mNextButton.setText("Next");
                mBackButton.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
