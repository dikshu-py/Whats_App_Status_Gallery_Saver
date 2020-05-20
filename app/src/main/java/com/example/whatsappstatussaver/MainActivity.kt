package com.example.whatsappstatussaver

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Size
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.GridView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel


class MainActivity : AppCompatActivity() {
    lateinit var gridview : GridView
    var data : MutableList<customclass> = ArrayList()
    lateinit var root :String
    val THUMBSIZE = 200
    lateinit var path : String
    lateinit var button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Requesting for Accessing the Storage Permissions.
        setupPermissions()
        setupPermissions2()
        gridview=findViewById(R.id.gridview)
        button=findViewById(R.id.button)
        button.setOnClickListener {
            val intent : Intent
            intent= packageManager.getLaunchIntentForPackage("com.whatsapp")!!
            startActivity(intent)

        }



        root =(Environment.getExternalStorageDirectory().absolutePath + "/Whatsapp/Media/.Statuses").toString()
        path=(Environment.getExternalStorageDirectory().absolutePath+"/DCIM/Camera").toString()
        val file :File = File(root)
        getfile(file)




    }



    fun getfile(dir: File): MutableList<customclass> {

        val listFile = dir.listFiles()


        if (listFile != null && listFile.size > 0) {
            for (i in listFile.indices) {
                if (listFile[i].isDirectory ) {

                } else {
                    if (listFile[i].name.endsWith(".png")
                        || listFile[i].name.endsWith(".jpg")
                        || listFile[i].name.endsWith(".jpeg")
                        || listFile[i].name.endsWith(".gif" )
                        && listFile[i].isHidden



                    ) {
                        val name = listFile[i].name
                        val ThumbImage = ThumbnailUtils.extractThumbnail(
                            BitmapFactory.decodeFile("$root/$name"),
                            THUMBSIZE, THUMBSIZE)

                        data.add(customclass(ThumbImage,listFile[i].name.toString()))

                    }
                    else if (listFile[i].name.endsWith(".mp4") && listFile[i].isHidden){
                        val name = listFile[i].name
                        val videopath = "$root/$name"
                        val thumb : File = File(videopath)

                        val inn : Size = Size(100,100)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            val generate =ThumbnailUtils.createVideoThumbnail(thumb,inn,null)
                            data.add(customclass(generate,listFile[i].name.toString()))
                        }
                        else{
                            val generate =ThumbnailUtils.createVideoThumbnail(videopath,
                                MediaStore.Images.Thumbnails.MICRO_KIND)
                            data.add(customclass(generate,listFile[i].name.toString()))

                        }













                    }
                }
            }
        }
        val adapter =adapter(this,R.layout.row_item,data)
        gridview.adapter = adapter
        //Command Line for the ClickView on the Items.
        gridview.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val itemname =data[position].name.toString()

            val videopath1 :File = File("$root/$itemname")
            val despath1 :File= File("$path/$itemname")
            copyFile(videopath1, despath1)
        }





        return data



    }
    private val RECORD_REQUEST_CODE = 101
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }

    }
    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)}


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }



    @Throws(IOException::class)
    fun copyFile(sourceFile: File?, destFile: File) {
        if (!destFile.parentFile.exists()) destFile.parentFile.mkdirs()
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        var source: FileChannel? = null
        var destination: FileChannel? = null
        try {
            source = FileInputStream(sourceFile).getChannel()
            destination = FileOutputStream(destFile).getChannel()
            destination.transferFrom(source, 0, source.size())
        } finally {
            source?.close()
            destination?.close()
        }
    }
    private val RECORD_REQUEST_CODES = 101
    private fun setupPermissions2() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest2()
        }

    }
    private fun makeRequest2() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODES)}










}

