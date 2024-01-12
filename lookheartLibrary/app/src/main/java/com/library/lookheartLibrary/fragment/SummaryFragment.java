package com.library.lookheartLibrary.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.library.lookheartLibrary.R;
import com.library.lookheartLibrary.viewmodel.SharedViewModel;
import com.library.lookheartLibrary.summary.SummaryArr;
import com.library.lookheartLibrary.summary.SummaryBpm;
import com.library.lookheartLibrary.summary.SummaryCal;
import com.library.lookheartLibrary.summary.SummaryHRV;
import com.library.lookheartLibrary.summary.SummaryStep;

import java.util.ArrayList;

public class SummaryFragment extends Fragment {
    SharedViewModel viewModel;

//    private Fragment bpm, arr, hrv, cal, step;
    FragmentManager fragmentManager;

    private SummaryBpm bpm;
    private SummaryArr arr;
    private SummaryHRV hrv;
    private SummaryCal cal;
    private SummaryStep step;

    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private LinearLayout bpmButton, arrButton, hrvButton, calButton, stepButton;
    private TextView bpmText, arrText, hrvText, calText, stepText;
    private ImageView bpmImg, arrImg, hrvImg, calImg, stepImg;
    LinearLayout[] layouts;
    TextView[] textViews;
    ImageView[] imageViews;
    View view;

    private SummaryBpm childFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summary, container, false);

        // LinearLayout
        bpmButton = view.findViewById(R.id.summaryBpm);
        arrButton = view.findViewById(R.id.summaryArr);
        hrvButton = view.findViewById(R.id.summaryHRV);
        calButton = view.findViewById(R.id.summaryCal);
        stepButton = view.findViewById(R.id.summaryStep);
        // TextView
        bpmText = view.findViewById(R.id.summaryBpmText);
        arrText = view.findViewById(R.id.summaryArrText);
        hrvText = view.findViewById(R.id.summaryHRVText);
        calText = view.findViewById(R.id.summaryCalText);
        stepText = view.findViewById(R.id.summaryStepText);
        // ImageView
        bpmImg = view.findViewById(R.id.summaryBpmImg);
        arrImg = view.findViewById(R.id.summaryArrImg);
        hrvImg = view.findViewById(R.id.summaryHRVImg);
        calImg = view.findViewById(R.id.summaryCalImg);
        stepImg = view.findViewById(R.id.summaryStepImg);

        layouts = new LinearLayout[]{bpmButton, arrButton, hrvButton, calButton, stepButton};
        textViews = new TextView[]{bpmText, arrText, hrvText, calText, stepText};
        imageViews = new ImageView[]{bpmImg, arrImg, hrvImg, calImg, stepImg};

        // Start Fragment
        bpm = new SummaryBpm();

        childFragment = (SummaryBpm) bpm;

        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.summaryFrame, bpm);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

//        refresh();

        bpmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(bpmButton, bpmText, bpmImg);

                if(bpm == null) {
                    bpm = new SummaryBpm();
                    fragmentManager.beginTransaction().add(R.id.summaryFrame, bpm).commit();
                    arrayList.add(bpm);
                } else {
                    bpm.updateChart();
                }

                if(bpm != null) fragmentManager.beginTransaction().show(bpm).commit();
                if(arr != null) fragmentManager.beginTransaction().hide(arr).commit();
                if(hrv != null) fragmentManager.beginTransaction().hide(hrv).commit();
                if(cal != null) fragmentManager.beginTransaction().hide(cal).commit();
                if(step != null) fragmentManager.beginTransaction().hide(step).commit();

            }
        });

        arrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(arrButton, arrText, arrImg);

                if(arr == null) {
                    arr = new SummaryArr();
                    fragmentManager.beginTransaction().add(R.id.summaryFrame, arr).commit();
                    arrayList.add(arr);
                } else {
                    arr.updateChart();
                }

                if(bpm != null) fragmentManager.beginTransaction().hide(bpm).commit();
                if(arr != null) fragmentManager.beginTransaction().show(arr).commit();
                if(hrv != null) fragmentManager.beginTransaction().hide(hrv).commit();
                if(cal != null) fragmentManager.beginTransaction().hide(cal).commit();
                if(step != null) fragmentManager.beginTransaction().hide(step).commit();
            }
        });

        hrvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(hrvButton, hrvText, hrvImg);

                if(hrv == null) {
                    hrv = new SummaryHRV();
                    fragmentManager.beginTransaction().add(R.id.summaryFrame, hrv).commit();
                    arrayList.add(hrv);
                } else {
                    hrv.updateChart();
                }

                if(bpm != null) fragmentManager.beginTransaction().hide(bpm).commit();
                if(arr != null) fragmentManager.beginTransaction().hide(arr).commit();
                if(hrv != null) fragmentManager.beginTransaction().show(hrv).commit();
                if(cal != null) fragmentManager.beginTransaction().hide(cal).commit();
                if(step != null) fragmentManager.beginTransaction().hide(step).commit();
            }
        });

        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(calButton, calText, calImg);

                if(cal == null) {
                    cal = new SummaryCal();
                    fragmentManager.beginTransaction().add(R.id.summaryFrame, cal).commit();
                    arrayList.add(cal);
                } else {
                    cal.updateChart();
                }

                if(bpm != null) fragmentManager.beginTransaction().hide(bpm).commit();
                if(arr != null) fragmentManager.beginTransaction().hide(arr).commit();
                if(hrv != null) fragmentManager.beginTransaction().hide(hrv).commit();
                if(cal != null) fragmentManager.beginTransaction().show(cal).commit();
                if(step != null) fragmentManager.beginTransaction().hide(step).commit();
            }
        });

        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(stepButton, stepText, stepImg);

                if(step == null) {
                    step = new SummaryStep();
                    fragmentManager.beginTransaction().add(R.id.summaryFrame, step).commit();
                    arrayList.add(step);
                } else {
                    step.updateChart();
                }

                if(bpm != null) fragmentManager.beginTransaction().hide(bpm).commit();
                if(arr != null) fragmentManager.beginTransaction().hide(arr).commit();
                if(hrv != null) fragmentManager.beginTransaction().hide(hrv).commit();
                if(cal != null) fragmentManager.beginTransaction().hide(cal).commit();
                if(step != null) fragmentManager.beginTransaction().show(step).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bpm != null)
            bpm.updateChart();
        if (arr != null)
            arr.updateChart();
        if (hrv != null)
            hrv.updateChart();
        if (cal != null)
            cal.updateChart();
        if (step != null)
            step.updateChart();
    }

    public void setColor(LinearLayout layout, TextView textView, ImageView imageView){
        BackgroundColor(layout);
        TextColor(textView);
        ImageColor(imageView);
    }

    public void BackgroundColor(LinearLayout layout){
        // 클릭한 레이아웃의 색상 변경
        layout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.summary_button_press));

        // 클릭하지 않은 레이아웃의 색상을 다른 색으로 변경
        for (LinearLayout otherLayout : layouts) {
            if (otherLayout != layout) {
                otherLayout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.summary_botton_normal));
            }
        }
    }
    @SuppressLint("ResourceType")
    public void TextColor(TextView text){

        text.setTextColor(Color.WHITE);

        for(TextView otherText : textViews){
            if (otherText != text) {
                otherText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.lightGray, null));
            }
        }
    }
    public void ImageColor(ImageView image){

        image.setColorFilter(Color.WHITE);

        for(ImageView otherImageView : imageViews){
            if (otherImageView != image) {
                otherImageView.setColorFilter(ResourcesCompat.getColor(getResources(), R.color.lightGray, null));
            }
        }
    }
}
