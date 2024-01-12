package com.library.lookheartLibrary.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.library.lookheartLibrary.R;
import com.library.lookheartLibrary.model.ArrDataModel;
import com.library.lookheartLibrary.model.GetArrDataModel;
import com.library.lookheartLibrary.model.GetArrListModel;
import com.library.lookheartLibrary.server.RetrofitServerManager;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.library.lookheartLibrary.controller.PeakController;
import com.library.lookheartLibrary.server.RetrofitService;

import org.apache.commons.lang3.ArrayUtils;

public class ArrFragment extends Fragment {

    /*constant*/
    //region
    private static final int FIND_ARRAY_INDEX = 14;
    private static final int ARR_DATA_SIZE = 500;
    private static final int NUMBER_BUTTON_PRESS_COLOR = 0;
    private static final int NUMBER_BUTTON_NORMAL_COLOR = 1;
    private static final int TEXT_BUTTON_PRESS_COLOR = 2;
    private static final int TEXT_BUTTON_NORMAL_COLOR = 3;
    private static final Boolean ARR_FLAG = true;
    private static final Boolean EMERGENCY_FLAG = false;
    private static final int[] arrButtonColorList = {
            R.drawable.arr_botton_press,
            R.drawable.arr_button_normal,
            R.drawable.bpm_border,
            R.drawable.home_bottom_button
    };
    private static final int[] emergencyButtonColorList = {
            R.drawable.emergency_press,
            R.drawable.arr_button_normal,
            R.drawable.emergency_border,
            R.drawable.home_bottom_button
    };
    //endregion

    /*constructor*/
    //region
    public ArrFragment(String email) {
        retrofitServerManager = RetrofitServerManager.getInstance();
        retrofitServerManager.setEmail(email);
    }
    //endregion

    private final RetrofitServerManager retrofitServerManager;
    private final Map<String, String> emergencyMap = new HashMap<>();    // writeTime : address
    private Toast currentToast;

    /*Date*/
    //region
    private String startDate;
    private String endDate;
    //endregion

    /*SimpleDateFormat*/
    //region
    SimpleDateFormat date;
    //endregion

    /*image button*/
    //region
    ImageButton yesterdayButton;
    ImageButton tomorrowButton;
    //endregion

    /*TextView*/
    //region
    TextView dateDisplay;
    TextView status;
    TextView statusText;
    TextView arrStatus;
    TextView arrStatusText;
    //endregion

    /*arrayList*/
    //region
    ArrayList<Button> numberButtonList = new ArrayList<>();
    ArrayList<Button> writeTimeButtonList = new ArrayList<>();
    //endregion

    /*LinearLayout*/
    //region
    LinearLayout arrNumberButtonsView;
    LinearLayout arrWriteTimeButtonsView;
    //endregion

    View view;
    LineChart arrChart;
    ScrollView scrollView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_arr, container, false);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        setViewID();

        setChart();

        setOnClickListener();

        updateCurrentDate();

        todayArrList();

        return view;
    }

    public void updateArrList() {
        System.out.println("update");
        updateCurrentDate();
        todayArrList();
    }

    public void updateCurrentDate() {
        // 시간 갱신 메서드
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);

        startDate = date.format(mDate);

        dateCalculate(0, true);
    }

    public void tomorrowButtonEvent() {
        dateCalculate(1, true);
        todayArrList();
    }

    public void yesterdayButtonEvent() {
        dateCalculate(1, false);
        todayArrList();
    }

    public void dateCalculate(int myDay, boolean check) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(startDate, formatter);

        if (check)
            date = date.plusDays(myDay);    // tomorrow
        else
            date = date.minusDays(myDay);   // yesterday

        startDate = date.format(formatter);

        date = LocalDate.parse(startDate, formatter);
        date = date.plusDays(1);

        endDate = date.format(formatter);
    }

    private void init() {
        dateDisplay.setText(startDate);
        arrChart.clear();

        setTextViewVisible(false);

        arrNumberButtonsView.removeAllViews();
        arrWriteTimeButtonsView.removeAllViews();

        numberButtonList = new ArrayList<>();
        writeTimeButtonList = new ArrayList<>();
    }

    public void todayArrList() {

        init();
        cancelToast();
        setLoadingBar(true);

        retrofitServerManager.getArrList(startDate, endDate, new RetrofitServerManager.ServerTaskCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.contains("result")) {
                    Gson gson = new Gson();
                    List<GetArrListModel> arrList = gson.fromJson(result, new TypeToken<List<GetArrListModel>>(){}.getType());
                    setupArrButtonList(arrList);

                } else {
                    // no data
                    toast(getResources().getString(R.string.noArrData));
                }
                setLoadingBar(false);
            }

            @Override
            public void onFailure(Exception e) {
                // server Err
                toast(getResources().getString(R.string.serverError));
                setLoadingBar(false);
            }
        });
    }

    public void setupArrButtonList(List<GetArrListModel> arrList) {

        int arrNumber = 1;
        int emergencyNumber = 1;

        for (GetArrListModel arrTime : arrList) {
            // Button
            Button numberButton = new Button(getActivity());
            Button writeTimeButton = new Button(getActivity());

            // Button Layout Params
            numberButton.setLayoutParams(setupButtonParams());
            writeTimeButton.setLayoutParams(setupButtonParams());

            if (arrTime.getAddress() == null) {
                // Arr
                // Button Properties
                setButtonProperties(numberButton, writeTimeButton, String.valueOf(arrNumber), arrTime.getWritetime(), arrNumber);

                // Button OnClickListener
                setButtonOnClickListener(numberButton, writeTimeButton, ARR_FLAG);

                arrNumber++;

            } else {
                // Emergency
                // Button Properties
                setButtonProperties(numberButton, writeTimeButton, "E", arrTime.getWritetime(), emergencyNumber);

                // Button OnClickListener
                setButtonOnClickListener(numberButton, writeTimeButton, EMERGENCY_FLAG);

                emergencyMap.put(arrTime.getWritetime(), arrTime.getAddress());

                emergencyNumber++;
            }

            numberButtonList.add(numberButton);
            writeTimeButtonList.add(writeTimeButton);

            arrNumberButtonsView.addView(numberButton);
            arrWriteTimeButtonsView.addView(writeTimeButton);
        }
    }

    private LinearLayout.LayoutParams setupButtonParams() {
        // LayoutParams 설정
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,    // 버튼의 너비
                LinearLayout.LayoutParams.WRAP_CONTENT     // 버튼의 높이
        );

        // 마진 설정
        int margin_in_dp = 5;
        final float scale = getResources().getDisplayMetrics().density;
        int margin_in_px = (int) (margin_in_dp * scale + 0.5f); // dp를 px로 변환

        params.setMargins(margin_in_px, margin_in_px, margin_in_px, margin_in_px);

        return params;
    }

    private void setButtonProperties(Button numberButton, Button textButton, String numberText, String buttonText, int buttonID){
        // 버튼 속성 설정
        numberButton.setText(numberText);
        numberButton.setTextColor(Color.WHITE);
        numberButton.setTextSize(14);
        numberButton.setTypeface(null, Typeface.BOLD);
        numberButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.arr_button_normal));
        numberButton.setId(buttonID);

        textButton.setText(buttonText);
        textButton.setTextColor(Color.BLACK);
        textButton.setTextSize(14);
        textButton.setTypeface(null, Typeface.BOLD);
        textButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.home_bottom_button));
        textButton.setId(buttonID);
    }

    private void setButtonColor(Button button, int[] colorList) {
        for (Button otherButton : numberButtonList) {
            if (otherButton.getId() == button.getId())
                otherButton.setBackground(ContextCompat.getDrawable(requireActivity(), colorList[NUMBER_BUTTON_PRESS_COLOR]));
            else
                otherButton.setBackground(ContextCompat.getDrawable(requireActivity(), colorList[NUMBER_BUTTON_NORMAL_COLOR]));
        }
        for (Button otherButton : writeTimeButtonList) {
            if (otherButton.getId() == button.getId())
                otherButton.setBackground(ContextCompat.getDrawable(requireActivity(), colorList[TEXT_BUTTON_PRESS_COLOR]));
            else
                otherButton.setBackground(ContextCompat.getDrawable(requireActivity(), colorList[TEXT_BUTTON_NORMAL_COLOR]));
        }
    }

    private void setButtonOnClickListener(Button numberButton, Button writeTimeButton, Boolean arrFlag) {

        int[] colorList;

        if (arrFlag)
            colorList = arrButtonColorList; // ARR COLOR
        else
            colorList = emergencyButtonColorList;   // EMERGENCY COLOR

        numberButton.setOnClickListener(v -> {
            getArrData(writeTimeButton.getText().toString(), arrFlag);
            setButtonColor(numberButton, colorList);
        });

        writeTimeButton.setOnClickListener(v -> {
            getArrData(writeTimeButton.getText().toString(), arrFlag);
            setButtonColor(writeTimeButton, colorList);
        });
    }

    private void getArrData(String writeTime, Boolean arrFlag) {

        setLoadingBar(true);

        retrofitServerManager.getArrData(writeTime, new RetrofitServerManager.ServerTaskCallback() {
            @Override
            public void onSuccess(String result) {

                setTextViewVisible(true);

                Gson gson = new Gson();
                List<GetArrDataModel> arrData = gson.fromJson(result, new TypeToken<List<GetArrDataModel>>(){}.getType());

                if (arrFlag)
                    setupChartData(arrData);
                else
                    setupEmergencyChart(arrData, writeTime);

                setLoadingBar(false);
            }

            @Override
            public void onFailure(Exception e) {
                // server Err
                toast(getResources().getString(R.string.serverError));
                setLoadingBar(false);
            }
        });
    }

    private void setupChartData(List<GetArrDataModel> arrDataList) {

        ArrDataModel arrData = new ArrDataModel(arrDataList.get(0).getArr());
        PeakController peakController = new PeakController();
        List<Double> resultList = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();

        setStatus(arrData.getBodyState(), arrData.getArrType());

        if (peakController.getEcgToPeakDataFlag()) {
            // PEAK MODE
            List<Double[]> doubleEcgList = new ArrayList<>();
            Double[] preEcgData = {};
            Double[] resultEcgData;

            // Ecg Data String to Double Array
            for (GetArrDataModel arrEcgData : arrDataList)
                doubleEcgList.add(arrEcgData.parseToSingleDoubleArray());

            // Sum Ecg Data Array
            for (Double[] data : doubleEcgList)
                preEcgData = ArrayUtils.addAll(preEcgData, data);

            int startIndex = findStartIndex(preEcgData, arrData.getEcgData());

            // Ecg to Peak 초기값 최소 5개 필요
            if (startIndex >= 5)
                resultEcgData = arrData.setResultPeakData(Arrays.stream(preEcgData, 0, startIndex).toArray(Double[]::new));
            else
                resultEcgData = arrData.setResultPeakData(preEcgData);

            // Double ECG -> Double PEAK
            for (Double ecgData : resultEcgData)
                resultList.add(peakController.changeEcgData(ecgData));

            // Double PEAK -> Chart Entries
            for (int i = resultList.size() - ARR_DATA_SIZE; i < resultList.size(); i++)
                entries.add(new Entry((float)i, resultList.get(i).floatValue()));

        } else {
            // ECG MODE
            resultList.addAll(Arrays.asList(arrData.getEcgData()));

            int x = 0;
            for (Double ecgData : resultList)
                entries.add(new Entry(x++, ecgData.floatValue()));

        }

        setSearchArrChartOption(entries);
    }

    private void setupEmergencyChart(List<GetArrDataModel> arrDataList, String writeTime) {

        setStatus(getResources().getString(R.string.emergency), Objects.requireNonNull(emergencyMap.get(writeTime)));

        boolean nullCheck = arrDataList.get(0).getArr().length() == 0;
        String setArrDataString = "null, null, null, null, ";

        if (!nullCheck)
            setArrDataString += arrDataList.get(0).getArr();
        else
            setArrDataString += "0.0";

        ArrDataModel arrDataModel = new ArrDataModel(setArrDataString);
        PeakController peakController = new PeakController();
        List<Double> resultList = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();

        if (peakController.getEcgToPeakDataFlag()) {
            // PEAK MODE
            if (!nullCheck) {
                // Peak Data 초기값 설정을 위해 같은 배열을 합침 ( 500 + 500 )
                Double[] arrData = ArrayUtils.addAll(arrDataModel.getEcgData(), arrDataModel.getEcgData());

                for (Double ecgData : arrData)
                    resultList.add(peakController.changeEcgData(ecgData));

                for (int i = resultList.size() - ARR_DATA_SIZE; i < resultList.size(); i++)
                    entries.add(new Entry((float)i, resultList.get(i).floatValue()));

            } else {
                // no Data
                for (int i = 0; i < 500; i++)
                    entries.add(new Entry((float)i, 0.0F));
            }

        } else {
            // ECG MODE
            if (!nullCheck) {

                resultList.addAll(Arrays.asList(arrDataModel.getEcgData()));

                for (Double ecgData : resultList)
                    entries.add(new Entry(0, ecgData.floatValue()));
            } else {
                for (int i = 0; i < 500; i++)
                    entries.add(new Entry((float)i, 500.0F));
            }

        }

        setSearchArrChartOption(entries);

    }

    private int findStartIndex(Double[] preEcgData, Double[] arrData) {

        int arrIndex = 0;
        Double[] subArrayToFind = Arrays.copyOf(arrData, FIND_ARRAY_INDEX);

        for (int i = 0; i <= preEcgData.length - FIND_ARRAY_INDEX; i++) {

            if (isSubArrayMatch(preEcgData[i], subArrayToFind[arrIndex])) {
                if (++arrIndex == FIND_ARRAY_INDEX)
                    return i - FIND_ARRAY_INDEX + 1;
            } else {
                arrIndex = 0;
            }
        }

        return -1;  // not found
    }

    private boolean isSubArrayMatch(Double ecgArray, Double arrArray) {
        return ecgArray.equals(arrArray);
    }

    void setSearchArrChartOption(List<Entry> entries){
        // 1
        LineDataSet arrChartDataSet = getArrChartDataSet(entries);
        // 2
        LineData arrChartData = new LineData(arrChartDataSet);

        arrChart.setData(arrChartData);
        arrChart.getXAxis().setEnabled(false);

        arrChart.getDescription().setEnabled(false); // 차트 설명
        arrChart.getData().notifyDataChanged();
        arrChart.notifyDataSetChanged();
        arrChart.moveViewToX(0);
    }

    private void setChart() {
        arrChart.getXAxis().setEnabled(false);
        arrChart.setNoDataText("");
        arrChart.getLegend().setEnabled(false); // 라벨 제거
        arrChart.getAxisLeft().setAxisMaximum(1024);
        arrChart.getAxisLeft().setAxisMinimum(0);
        arrChart.getAxisRight().setEnabled(false);
        arrChart.setDrawMarkers(false);
        arrChart.setDragEnabled(false);
        arrChart.setPinchZoom(false);
        arrChart.setDoubleTapToZoomEnabled(false);
        arrChart.setHighlightPerTapEnabled(false);
    }

    LineDataSet getArrChartDataSet(List<Entry> entries){
        LineDataSet arrChartDataSet = new LineDataSet(entries, null);
        arrChartDataSet.setDrawCircles(false);
        arrChartDataSet.setColor(Color.BLUE);
        arrChartDataSet.setMode(LineDataSet.Mode.LINEAR);
        arrChartDataSet.setDrawValues(false);
        return arrChartDataSet;
    }


    public void setStatus(String myStatus, String myType){
        statusText.setText(getResources().getString(R.string.arrState));
        arrStatusText.setText(getResources().getString(R.string.arrType));

        status.setText(setStatus(myStatus));
        arrStatus.setText(setType(myType.replace("\n", "")));
    }

    private String setStatus(String status) {
        switch (status){
            case "R":
                return getResources().getString(R.string.rest);
            case "E":
                return getResources().getString(R.string.exercise);
            case "S":
                return getResources().getString(R.string.sleep);
            default:
                setEmergencyText();
                return "";
        }
    }

    private String setType(String type) {
        switch (type){
            case "arr":
                return getResources().getString(R.string.typeArr);
            case "fast":
                return getResources().getString(R.string.typeFastArr);
            case "slow":
                return getResources().getString(R.string.typeSlowArr);
            case "irregular":
                return getResources().getString(R.string.typeHeavyArr);
            default:
                return type;    // EMERGENCY ADDRESS
        }
    }

    private void setEmergencyText() {
        statusText.setText("");
        arrStatusText.setText(getResources().getString(R.string.emergencyType));
    }

    void setViewID() {
        arrNumberButtonsView = view.findViewById(R.id.arrButton);
        arrWriteTimeButtonsView = view.findViewById(R.id.arrText);

        statusText = view.findViewById(R.id.status);
        status = view.findViewById(R.id.statusValue);

        arrStatusText = view.findViewById(R.id.arrStatus);
        arrStatus = view.findViewById(R.id.arrStatusValue);
        arrChart = view.findViewById(R.id.fragment_arrChart);

        yesterdayButton = view.findViewById(R.id.yesterdayButton);
        tomorrowButton = view.findViewById(R.id.tomorrowButton);
        dateDisplay = view.findViewById(R.id.dateDisplay);
        scrollView = view.findViewById(R.id.scrollView);

        progressBar = view.findViewById(R.id.progressBar);
    }

    void setOnClickListener() {
        yesterdayButton.setOnClickListener(view -> yesterdayButtonEvent());

        tomorrowButton.setOnClickListener(view -> tomorrowButtonEvent());
    }

    private void setTextViewVisible(boolean flag) {
        int visible;

        if (flag)
            visible = View.VISIBLE;
        else
            visible = View.GONE;

        statusText.setVisibility(visible);
        status.setVisibility(visible);

        arrStatusText.setVisibility(visible);
        arrStatus.setVisibility(visible);

    }

    private void setLoadingBar(boolean flag) {
        if (flag)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void toast(String text) {
        if (getActivity() != null) {
            if (currentToast != null)
                currentToast.cancel();

            currentToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
            currentToast.show();
        }
    }

    private void cancelToast() {
        if (currentToast != null)
            currentToast.cancel();
    }

}