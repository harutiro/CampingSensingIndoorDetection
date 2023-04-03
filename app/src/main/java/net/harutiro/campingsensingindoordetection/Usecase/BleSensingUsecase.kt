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
    private val date:String
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

                if(it.id1.toString() == "e7d61ea3-f8dd-49c8-8f2f-f2484c07ac1b"){
                    graphUtils.setData(it.rssi.toFloat(),epochMillis.toInt())
                    otherFileStorage?.doLog(
                        RssiDataClass(epochMillis.toInt(),it.rssi),
                        date
                    )
                }
            }

        }
    }

    fun stop(){
        isSensing = false

        otherFileStorage?.close(date)
        otherFileStorage = null

        bleUtils.stopBle()
    }

}