package com.jaya.app.videorecorderdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView=findViewById(R.id.videoView)
        button=findViewById(R.id.button)
        button.isEnabled=false

//        val mediaCollection=MediaController(this)
//        mediaCollection.setAnchorView(videoView)
//        videoView.setMediaController(mediaCollection)


        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),111)
        else
            button.isEnabled=true




    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            button.isEnabled=true
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==111 && resultCode== RESULT_OK){
            videoView.setVideoURI(data?.data)
            videoView.start()
        }

        Log.d("onActivityResult", "onActivityResult:${data?.data} ")

    }


    fun startVideo(view: View) {
        var intent=Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (intent.resolveActivity(packageManager)!= null){
            startActivityForResult(intent,111)
        }
    }




}