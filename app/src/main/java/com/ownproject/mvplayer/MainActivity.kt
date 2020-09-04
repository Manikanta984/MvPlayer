package com.ownproject.mvplayer

import android.Manifest
import android.app.PictureInPictureParams
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var dir: File
    var bool by Delegates.notNull<Boolean>()
    val REQUEST_PERMISSION = 1
    lateinit var adapt:MyAdapter
    lateinit var ItemList :ArrayList<File>
    var a=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            ItemList = ArrayList()
            setContentView(R.layout.activity_main)
            recycler = findViewById(R.id.listvediovew)
            dir = File("/sdcard/")
            val grid = GridLayoutManager(this, 1)
            recycler.layoutManager = grid
        val pos=intent.getIntExtra("position",-1)
        val lst=intent.getStringArrayListExtra("list")
        if (pos!=-1){
                supportActionBar?.hide()
                val bundle = Bundle()
                bundle.putStringArrayList("list", lst)
                bundle.putInt("pos", pos)
                val fragobj = MyAdapterFragment()
               fragobj.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.main, fragobj).commit()
                enterPictureInPictureMode(PictureInPictureParams.Builder().build())
        }
        else{
            seekPermission()
        }
    }

    fun seekPermission() {
        val su = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
        if (su) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            }
            else {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_PERMISSION
                )
            }
        }
        else{
            bool=true
            getFile(dir)
            adapt= MyAdapter(this,itemlist = ItemList)
            recycler.adapter=adapt
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==REQUEST_PERMISSION){
            if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                bool=true
                getFile(dir)
                adapt= MyAdapter(this,itemlist = ItemList)
                recycler.adapter=adapt
            }
        }
    }
    fun getFile(dir:File) {
        if (dir.name != "WhatsApp" && dir.name!="Android"){
        val lst = dir.listFiles()
            if (lst!=null)
        for (i in lst) {
            if (i.isDirectory) {
                getFile(i)
            } else {
                if (i.name.endsWith(".mkv") || i.name.endsWith("mp4") )
                    ItemList.add(i)
            }
        }

    }
    }

}