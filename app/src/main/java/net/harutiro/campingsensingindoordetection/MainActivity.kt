package net.harutiro.campingsensingindoordetection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import net.harutiro.campingsensingindoordetection.Utils.BLEUtils
import net.harutiro.campingsensingindoordetection.Utils.GraphUtils
import net.harutiro.campingsensingindoordetection.Utils.PermissionUtils


class MainActivity : AppCompatActivity() {

    val permissionUtils = PermissionUtils()
    val bleUtils = BLEUtils()
    var graphUtils: GraphUtils? = null

    val TAG = "MainActivity"

    private var mChart: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //最初のパーミッションの許可を行う
        permissionUtils.requestPermissions(this)

        //グラフの設定
        mChart = findViewById(R.id.line_chart)
        graphUtils = GraphUtils(mChart!!)
        graphUtils?.init()

        //BLEの受信をする
        bleUtils.getBle(this,this){ beacons ->


            Log.d(TAG,"端末数" + beacons.size.toString())
            beacons.forEach {
                val epochMillis = System.currentTimeMillis()

                Log.d(TAG,"$epochMillis , ${it.id1} , ${it.bluetoothAddress} , ${it.bluetoothName} , ${it.distance} , ${it.rssi}")

                if(it.id1.toString() == "536108df-48d8-4db9-9ef0-156f7ddb4a54"){
                    graphUtils?.setData(it.rssi.toFloat(),epochMillis.toInt())
                }
            }
        }




    }


}