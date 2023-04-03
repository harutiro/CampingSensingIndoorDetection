package net.harutiro.campingsensingindoordetection.Utils

import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import net.harutiro.campingsensingindoordetection.Entity.RssiDataClass
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.PrintWriter

class OtherFileStorage(private val context: Context){

    val fileAppend : Boolean = true //true=追記, false=上書き
    var fileName : String = "SensorLog.csv"
    val filePath: String = context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString().plus("/").plus(fileName) //内部ストレージのDocumentのURL


    val tmpDataList = mutableListOf<RssiDataClass>()

    var pw: PrintWriter? = null

    private fun writeText(text:String){
        pw?.println(text)

    }

    private fun openFile(path:String){
        val fil = FileWriter(path,fileAppend)
        pw = PrintWriter(BufferedWriter(fil))
    }

    private fun closeFile(){
        pw?.close()
        tmpDataList.clear()
    }

    fun doLog(data:RssiDataClass) {

        tmpDataList.add(data)

        if(tmpDataList.size >= 10 ){
            openFile(filePath)

            tmpDataList.forEach() { item ->
                val text = "${item.time},${item.rssi}"
                writeText(text)
            }

            closeFile()
        }
    }

    fun close(){

        openFile(filePath)

        tmpDataList.forEach() { item ->
            val text = "${item.time},${item.rssi}"
            writeText(text)
        }

        closeFile()
    }


}