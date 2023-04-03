package net.harutiro.campingsensingindoordetection.Usecase

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.github.mikephil.charting.charts.LineChart
import net.harutiro.campingsensingindoordetection.Entity.RssiDataClass
import net.harutiro.campingsensingindoordetection.R
import net.harutiro.campingsensingindoordetection.Utils.BLEUtils
import net.harutiro.campingsensingindoordetection.Utils.GraphUtils
import net.harutiro.campingsensingindoordetection.Utils.OtherFileStorage
import net.harutiro.campingsensingindoordetection.Utils.PermissionUtils

class BleSensingUsecase(
    private val context: Context,
    private val lifecycleOwner:LifecycleOwner,
    private var graphUtils: GraphUtils,
) {

    private val bleUtils = BLEUtils()
    private var otherFileStorage : OtherFileStorage? = null

    var isSensing = false

    val TAG = "BleSensingUsecase"

    fun start(){

        isSensing = true

        // CSV用のインスタンス化
        otherFileStorage = OtherFileStorage(context)

        bleUtils.getBle(context,lifecycleOwner){ beacons ->

            Log.d(TAG,"端末数" + beacons.size.toString())

            beacons.forEach {
                val epochMillis = System.currentTimeMillis()

                Log.d(TAG,"$epochMillis , ${it.id1} , ${it.bluetoothAddress} , ${it.bluetoothName} , ${it.distance} , ${it.rssi}")

                if(it.id1.toString() == "536108df-48d8-4db9-9ef0-156f7ddb4a54"){
                    graphUtils.setData(it.rssi.toFloat(),epochMillis.toInt())
                    otherFileStorage?.doLog(
                        RssiDataClass(epochMillis.toInt(),it.rssi)
                    )
                }
            }

        }
    }

    fun stop(){
        isSensing = false

        otherFileStorage?.close()
        otherFileStorage = null

        bleUtils.stopBle()
    }

}