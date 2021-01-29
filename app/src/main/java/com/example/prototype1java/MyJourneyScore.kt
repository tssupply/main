package com.example.prototype1java

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    private lateinit var myJourneyChart: BarChart
    private lateinit var scorePathView: ScorePathView

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
        myJourneyChart = findViewById(R.id.my_journey_chart)
        myJourneyChart.setDrawBarShadow(false)
        myJourneyChart.setDrawValueAboveBar(true)
        myJourneyChart.description.isEnabled = false
        // if more than 60 entries are displayed in the chart, no values will be drawn
        myJourneyChart.setMaxVisibleValueCount(60)
        myJourneyChart.setPinchZoom(false)
        myJourneyChart.setDrawGridBackground(false)
        val xAxis: XAxis = myJourneyChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 7
        val leftAxis: YAxis = myJourneyChart.axisLeft
        leftAxis.setLabelCount(8, false)
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val rightAxis: YAxis = myJourneyChart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(8, false)
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val l: Legend = myJourneyChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f

        setData(20, 20)
        myJourneyChart.invalidate()
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
                values.add(BarEntry(i.toFloat(), value))
            }
            i++
        }
        val set1: BarDataSet
        if (myJourneyChart.data != null &&
                myJourneyChart.data.dataSetCount > 0) {
            set1 = myJourneyChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            set1.color = Color.rgb(60, 220, 78)
            set1.valueTextColor = Color.rgb(60, 220, 78)
            set1.valueTextSize = 10f
            myJourneyChart.data.notifyDataChanged()
            myJourneyChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "The year 2017")
            set1.setDrawIcons(false)
            set1.values = values
            set1.color = Color.rgb(255, 255, 255)
            set1.valueTextColor = Color.RED
            set1.valueTextSize = 10f
            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTextColor(Color.GRAY)
            data.barWidth = 0.1f
            myJourneyChart.data = data
            myJourneyChart.drawingCacheBackgroundColor = Color.GRAY
        }
    }
}
