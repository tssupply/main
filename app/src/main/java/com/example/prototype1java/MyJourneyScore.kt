package com.example.prototype1java

//import com.github.mikephil.charting.utils;
//import com.github.mikephil.charting.components.YAxis.AxisDependency;
///import com.github.mikephil.charting.utils.Fill;

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class MyJourneyScore  : AppCompatActivity() {

    private lateinit var chart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_journey_score)
        setUpChart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setUpChart() {
        // this future component's prototype
        chart = findViewById(R.id.chart1)
        //chart.setBackgroundColor(Color.GREEN);
        //chart.setOnChartValueSelectedListener(this);
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.getDescription().setEnabled(false)
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        // chart.setDrawYLabels(false);
        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        val xAxis: XAxis = chart.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        //xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 7
        //xAxis.setValueFormatter(xAxisFormatter);
        //IAxisValueFormatter custom = new MyAxisValueFormatter();
        val leftAxis: YAxis = chart.getAxisLeft()
        //leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false)
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val rightAxis: YAxis = chart.getAxisRight()
        rightAxis.setDrawGridLines(false)
        //rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false)
        //rightAxis.setValueFormatter(custom);
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val l: Legend = chart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f

        // XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        // mv.setChartView(chart); // For bounds control
        //chart.setMarker(mv); // Set the marker to the chart
        // chart.setDrawLegend(false);
        setData(20, 20)
        chart.invalidate()
    }
    private fun setData(count: Int, range: Int) {
        val start = 1f
        val values: ArrayList<BarEntry> = ArrayList()
        var i = start.toInt()
        while (i < start + count) {
            val value = (Math.random() * (range + 1)).toFloat()
            if (Math.random() * 100 < 25) {
                //values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
//                values.add(BarEntry(i, `val`))
                values.add(BarEntry(i.toFloat(), value))
            }
            i++
        }
        val set1: BarDataSet
        if (chart.data != null &&
                chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            set1.color = Color.rgb(60, 220, 78)
            set1.valueTextColor = Color.rgb(60, 220, 78)
            set1.valueTextSize = 10f
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "The year 2017")
            set1.setDrawIcons(false)

//            List<Fill> gradientFills = new ArrayList<>();
//            gradientFills.add(new Fill(startColor1, endColor1));
//            gradientFills.add(new Fill(startColor2, endColor2));
//            gradientFills.add(new Fill(startColor3, endColor3));
//            gradientFills.add(new Fill(startColor4, endColor4));
//            gradientFills.add(new Fill(startColor5, endColor5));
//
//            set1.setFills(gradientFills);
            set1.values = values
            set1.color = Color.rgb(255, 255, 255)
            set1.valueTextColor = Color.RED
            set1.valueTextSize = 10f
            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTextColor(Color.GRAY)
            //data.setValueTypeface(tfLight);
            data.barWidth = 0.1f
            //chart.setGridBackgroundColor(Color.RED);
            chart.data = data
            chart.drawingCacheBackgroundColor = Color.GRAY
        }
    }
}
