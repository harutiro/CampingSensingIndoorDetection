package net.harutiro.campingsensingindoordetection.Usecase

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import net.harutiro.campingsensingindoordetection.Entity.RssiDataClass
import net.harutiro.campingsensingindoordetection.Utils.BLEUtils
import net.harutiro.campingsensingindoordetection.Utils.GraphUtils
import net.harutiro.campingsensingindoordetection.Utils.OtherFileStorage

class BleSensingUsecase(
    private val context: Context,
    private val lifecycleOwner:LifecycleOwner,
    private var graphUtils: GraphUtils,
    private val date:String
) {

    private val bleUtils = BLEUtils()
    private var otherFileStorage1 : OtherFileStorage? = null
    private var otherFileStorage2 : OtherFileStorage? = null

    val entryListIsu = mutableListOf<RssiDataClass>()
    val entryListTent = mutableListOf<RssiDataClass>()


    var isSensing = false

    val TAG = "BleSensingUsecase"

    fun start(){

        isSensing = true

        // CSV用のインスタンス化
        otherFileStorage1 = OtherFileStorage(context)
        otherFileStorage1?.fileName = "BLESensorLog_isu"
        otherFileStorage2 = OtherFileStorage(context)
        otherFileStorage2?.fileName = "BLESensorLog_tent"

        bleUtils.getBle(context,lifecycleOwner){ beacons ->

            Log.d(TAG,"端末数" + beacons.size.toString())

            beacons.forEach {
                val epochMillis = System.currentTimeMillis()

                Log.d(TAG,"$epochMillis , ${it.id1} , ${it.bluetoothAddress} , ${it.bluetoothName} , ${it.distance} , ${it.rssi}")

                if(it.id1.toString() == "e7d61ea3-f8dd-49c8-8f2f-f2484c07acaa"){

//                    entryListIsu.add(RssiDataClass(epochMillis.toInt(),it.rssi))

//                    graphUtils.setData(
//                        entryListIsu,
//                        "椅子",
//                        Color.BLUE
//                    )
                    otherFileStorage1?.doLog(
                        RssiDataClass(epochMillis.toInt(),it.rssi),
                        date
                    )
                }

                if(it.id1.toString() == "e7d61ea3-f8dd-49c8-8f2f-f2484c07acbb"){

//                    entryListTent.add(RssiDataClass(epochMillis.toInt(),it.rssi))

//                    graphUtils.setData(
//                        entryListTent,
//                        "テント",
//                        Color.RED
//                    )

                    otherFileStorage2?.doLog(
                        RssiDataClass(epochMillis.toInt(),it.rssi),
                        date
                    )
                }
            }

        }
    }

    fun stop(){
        isSensing = false

        otherFileStorage1?.close(date)
        otherFileStorage1 = null

        otherFileStorage2?.close(date)
        otherFileStorage2 = null

        bleUtils.stopBle()
    }

}