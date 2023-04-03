package net.harutiro.campingsensingindoordetection.Utils

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class GraphUtils(val mChart:LineChart) {

    var firstTime = 0
    var index = 0

    fun init(){
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

    val entryList = mutableListOf<Entry>()


    fun setData(value:Float , time:Int) {
        // Entry()を使ってLineDataSetに設定できる形に変更してarrayを新しく作成
        if(firstTime == 0){
            firstTime = time
        }

        entryList.add(
            Entry(((time-firstTime)/1000).toFloat(),value)
        )

        val lineDataSet = LineDataSet(entryList,"RSSI")
        lineDataSet.color = Color.BLUE

        val lineDataSets = mutableListOf<ILineDataSet>(lineDataSet)

        val lineData = LineData(lineDataSets)

        mChart.data = lineData

        mChart.invalidate()
        mChart.notifyDataSetChanged()


    }
}