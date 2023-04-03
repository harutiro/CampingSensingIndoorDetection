package net.harutiro.campingsensingindoordetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import net.harutiro.campingsensingindoordetection.Utils.BLEUtils
import net.harutiro.campingsensingindoordetection.Utils.PermissionUtils

class MainActivity : AppCompatActivity() {

    val permissionUtils = PermissionUtils()
    val bleUtils = BLEUtils()

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //最初のパーミッションの許可を行う
        permissionUtils.requestPermissions(this)

        //BLEの受信をする
        bleUtils.getBle(this,this){ beacons ->

            val epochMillis = System.currentTimeMillis()

            Log.d(TAG,"端末数" + beacons.size.toString())
            beacons.forEach {
                Log.d(TAG,"$epochMillis , ${it.id1} , ${it.bluetoothAddress} , ${it.bluetoothName} , ${it.distance} , ${it.rssi}")
            }
        }
    }
}