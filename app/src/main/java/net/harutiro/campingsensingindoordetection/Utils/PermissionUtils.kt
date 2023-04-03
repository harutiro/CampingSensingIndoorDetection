package net.harutiro.campingsensingindoordetection.Utils

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.EasyPermissions

class PermissionUtils {

    //パーミッション確認用のコード
    private val PERMISSION_REQUEST_CODE = 1

    //どのパーミッションを許可したいかリスト化する
    val permissions = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE
        )
    }else{
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    fun requestPermissions(app:AppCompatActivity){
        //パーミッション確認
        //TODO:ロケーションの取得の常に許可をできるようにする
        if (!EasyPermissions.hasPermissions(app, *permissions)) {
            // パーミッションが許可されていない時の処理
            EasyPermissions.requestPermissions(app, "パーミッションに関する説明", PERMISSION_REQUEST_CODE, *permissions)
        }
    }
}