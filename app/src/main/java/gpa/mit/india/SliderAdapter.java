package gpa.mit.india;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter
{
    private Context mContext;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    public int[] sliderImages = {R.drawable.slide_1,R.drawable.slide_2,R.drawable.slide_3,R.drawable.slide_4};
    public String[] sliderHeadings = {"WELCOME TO GPA CALCULATOR","GPA/CGPA CALCULATION","REMOVING AND ADDING","STORED VALUES"};
    public String[] sliderDescriptions =
            {"GPA Calculator app makes your GPA and CGPA  calculations easier and allows you to store the results for future references.",
                    "Enter the Number of Subjects and then select your credits and grades and press the calculate button to calculate your GPA. Follow the same steps for CGPA calculation.",
                    "If you have added any extra subjects than required, you can simply remove it by either swiping it to the left or right. Similarly if you want to add more subjects, you can do it by using add one more button.",
                    "You can view your Stored values in the My GPAs/ My CGPAs menu and you can edit, remove or add  them  to CGPA calculation by touching the Add to CGPA Calculation Button. You can also save a new value by pressing the add icon in the top right corner."};


    @Override
    public int getCount() {
        return sliderHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView SlideImage = view.findViewById(R.id.slide_image);
        TextView SlideHeading = view.findViewById(R.id.slide_heading);
        TextView SlideDescription = view.findViewById(R.id.slide_description);

        SlideImage.setImageResource(sliderImages[position]);
        SlideHeading.setText(sliderHeadings[position]);
        SlideDescription.setText(sliderDescriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
