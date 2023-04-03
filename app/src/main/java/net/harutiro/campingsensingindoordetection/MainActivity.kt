package net.harutiro.campingsensingindoordetection

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import net.harutiro.campingsensingindoordetection.Entity.RssiDataClass
import net.harutiro.campingsensingindoordetection.Usecase.BleSensingUsecase
import net.harutiro.campingsensingindoordetection.Utils.BLEUtils
import net.harutiro.campingsensingindoordetection.Utils.GraphUtils
import net.harutiro.campingsensingindoordetection.Utils.OtherFileStorage
import net.harutiro.campingsensingindoordetection.Utils.PermissionUtils


class MainActivity : AppCompatActivity() {

    private val permissionUtils = PermissionUtils()
    var bleSensingUsecase : BleSensingUsecase? = null

    var mChart:LineChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //最初のパーミッションの許可を行う
        permissionUtils.requestPermissions(this)

        //グラフの設定
        mChart = findViewById(R.id.line_chart)



        findViewById<Switch>(R.id.SensingStartSwitch).setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                //センシングの準備
                bleSensingUsecase = BleSensingUsecase(
                    this,
                    this,
                    getGraph()
                )

                bleSensingUsecase?.start()


            }else{
                bleSensingUsecase?.stop()
                bleSensingUsecase = null
            }
        }
    }
    
    fun getGraph(): GraphUtils {
        val graphUtils = GraphUtils(mChart!!)
        graphUtils.init()

        return graphUtils
    }

    override fun onDestroy() {
        super.onDestroy()

        bleSensingUsecase?.stop()

    }


}