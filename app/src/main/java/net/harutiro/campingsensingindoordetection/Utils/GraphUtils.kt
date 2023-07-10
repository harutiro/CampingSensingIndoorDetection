package net.harutiro.campingsensingindoordetection.Utils

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import net.harutiro.campingsensingindoordetection.Entity.RssiDataClass

class GraphUtils(val mChart:LineChart) {

    private var firstTime = 0
    private val lineDataSets: MutableList<ILineDataSet> = mutableListOf()

    init {
        setupChart()
    }

    private fun setupChart() {
        // Grid背景色
        mChart.setDrawGridBackground(true)

        // no description text
        mChart.description?.isEnabled = true

        // Grid縦軸を破線
        val xAxis: XAxis? = mChart.xAxis
        xAxis?.enableGridDashedLine(10f, 10f, 0f)
        xAxis?.position = XAxis.XAxisPosition.BOTTOM

        val leftAxis: YAxis? = mChart.axisLeft
        // Y軸最大最小設定
        leftAxis?.axisMaximum = 0f
        leftAxis?.axisMinimum = -150f

        // Grid横軸を破線
        leftAxis?.enableGridDashedLine(10f, 10f, 0f)
        leftAxis?.setDrawZeroLine(true)

        // 右側の目盛り
        mChart.axisRight?.isEnabled = false
    }

    fun clearData() {
        lineDataSets.clear()
        updateChart()
    }

    fun setData(values: MutableList<RssiDataClass>, label: String, color: Int) {
        if (firstTime == 0) {
            firstTime = values[0].time
        }

        val entries: List<Entry> = values.mapIndexed { index, value ->
            Entry(((value.time - firstTime) / 1000).toFloat(), value.rssi.toFloat())
        }

        val lineDataSet = LineDataSet(entries, label)
        lineDataSet.color = color

        lineDataSets.add(lineDataSet)

        updateChart()
    }

    fun updateChart() {
        val lineData = LineData(lineDataSets)
        mChart.data = lineData

        mChart.invalidate()
        mChart.notifyDataSetChanged()
    }
}