package com.example.prototype1java;
// evaluation prototype code WIP
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.utils;
//import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
//import com.github.mikephil.charting.interfaces.datasets.IDataSet;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
///import com.github.mikephil.charting.utils.Fill;
//import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    VideoView simpleVideoView;
    MediaController mediaControls;
    private BarChart chart;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_15inchland);
        PathView path_view = findViewById(R.id.path);
        path_view.init();
        SetupVideo();
        ValueAnimator animator = setUpCounter();
        animator.setDuration(6000);
        animator.start();
        setUpChart();

    }

    private ValueAnimator setUpCounter() {
        score = findViewById(R.id.textinput_counter);
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, 579);
        animator.addUpdateListener(animation -> score.setText(String.valueOf(animation.getAnimatedValue())));
        animator.setEvaluator((TypeEvaluator<Integer>) (fraction, startValue, endValue) -> Math.round(startValue + (endValue - startValue) * fraction));
        return animator;
    }

    // evaluation prototype code WIP
    private void setUpChart() {
        // this future component's prototype
        chart = findViewById(R.id.chart1);
        //chart.setBackgroundColor(Color.GREEN);
        //chart.setOnChartValueSelectedListener(this);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);
        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        //xAxis.setValueFormatter(xAxisFormatter);
        //IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        //rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        //rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        // XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        // mv.setChartView(chart); // For bounds control
        //chart.setMarker(mv); // Set the marker to the chart
        // chart.setDrawLegend(false);

        setData(20, 20);
        chart.invalidate();
    }

    private void SetupVideo() {
        simpleVideoView = findViewById(R.id.simpleVideoView);
        simpleVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
        if (mediaControls == null) {
            mediaControls = new MediaController(MainActivity.this);
            mediaControls.setAnchorView(simpleVideoView);
        }

        simpleVideoView.setMediaController(mediaControls);
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.w1));
        simpleVideoView.start();
        simpleVideoView.setOnCompletionListener(mp -> {
            Toast.makeText(getApplicationContext(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
        });
        simpleVideoView.setOnErrorListener((mp, what, extra) -> false);
    }

    private void setData(int count, float range) {
        float start = 1f;
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                //values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.setColor(Color.rgb(60, 220, 78));
            set1.setValueTextColor(Color.rgb(60, 220, 78));
            set1.setValueTextSize(10f);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");

            set1.setDrawIcons(false);

//            List<Fill> gradientFills = new ArrayList<>();
//            gradientFills.add(new Fill(startColor1, endColor1));
//            gradientFills.add(new Fill(startColor2, endColor2));
//            gradientFills.add(new Fill(startColor3, endColor3));
//            gradientFills.add(new Fill(startColor4, endColor4));
//            gradientFills.add(new Fill(startColor5, endColor5));
//
//            set1.setFills(gradientFills);
            set1.setValues(values);
            set1.setColor(Color.rgb(255, 255, 255));
            set1.setValueTextColor(Color.RED);
            set1.setValueTextSize(10f);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.GRAY);
            //data.setValueTypeface(tfLight);
            data.setBarWidth(0.1f);
            //chart.setGridBackgroundColor(Color.RED);
            chart.setData(data);
            chart.setDrawingCacheBackgroundColor(Color.GRAY);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}