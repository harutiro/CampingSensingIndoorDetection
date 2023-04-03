package net.harutiro.campingsensingindoordetection.Utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region
import pub.devrel.easypermissions.EasyPermissions

class BLEUtils {

    private val permissionUtils = PermissionUtils()

    //tagName
    private val TAG: String = "BLE"

    // iBeaconのデータを認識するためのParserフォーマット
    val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"



    fun getBle(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        func: (beacons: Collection<Beacon>) -> Unit
    ){

        Log.d(TAG,"BLEを受信します。")

        //取得した時の動作部分
        val rangingObserver = Observer<Collection<Beacon>> { beacons ->
            Log.d(TAG, "Ranged: ${beacons.count()} beacons")
//
//            for (beacon: Beacon in beacons) {
//                Log.d(TAG, "$beacon about ${beacon.distance} meters away")
//            }

            func(beacons)
        }

        //パーミッションが許可された時にIbeaconが動く
        if(EasyPermissions.hasPermissions(context, *permissionUtils.permissions)){

            Log.d(TAG,"BLEを受信するパーミッションを許可しました")
            //絞り込みをする部分
            //今回nullなので、全てを取得する。
            //id1:uuid id2:major id3:minor
            val mRegion = Region("unique-id-001", null, null, null)

            val beaconManager =  BeaconManager.getInstanceForApplication(context)
            // Set up a Live Data observer so this Activity can get ranging callbacks
            // observer will be called each time the monitored regionState changes (inside vs. outside region)
            beaconManager.getRegionViewModel(mRegion).rangedBeacons.observe(lifecycleOwner, rangingObserver)
            beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
            beaconManager.startRangingBeacons(mRegion)
        }
    }
}