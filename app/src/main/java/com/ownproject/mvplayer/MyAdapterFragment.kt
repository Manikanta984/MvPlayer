package com.ownproject.mvplayer
import android.Manifest
import android.app.PictureInPictureParams
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import kotlinx.android.synthetic.main.vedioitem.view.*
import java.util.ArrayList

class MyAdapterFragment : Fragment() {
    var lst=ArrayList<String>()
    var pos=-1
    lateinit var vd:VideoView
    companion object {
        fun newInstance() = MyAdapterFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.my_adapter_fragment, container, false)
        vd=view.findViewById(R.id.fragvedio)
        var mc= MediaController(view.context)
        mc.setAnchorView(vd)
            vd.setMediaController(mc)
        vd.setVideoPath(lst[pos])
        vd.requestFocus()
       activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        vd.start()
        var bool=true
        vd.setOnClickListener {
            if(bool) {
                vd.pause()
                bool=false
            }else{
                vd.start()
                bool=true
            }


        }
        vd.setOnCompletionListener {
            if(pos!=lst.size-1)
             pos+=1
            else
                pos=0
            vd.setVideoPath(lst[pos])
            vd.start()
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!=null){
           lst= arguments!!.getStringArrayList("list") as ArrayList<String>
            pos= arguments!!.getInt("pos")
        }
    }

    override fun onPause() {
        super.onPause()
        vd.resume()
    }
    override fun onResume() {
        super.onResume()
        vd.start()
    }
}