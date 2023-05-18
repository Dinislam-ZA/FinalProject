package com.example.finalproject.ui

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.finalproject.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SplashScreenFragment() : Fragment(), CoroutineScope {


    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + Job()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }




    override fun onStart() {
        super.onStart()
        launch {
            delay(1500)
            withContext(Dispatchers.Main){
                val appLaunchTv = view?.findViewById<TextView>(R.id.splash_screen_TV)
                val appLaunchIc = view?.findViewById<ImageView>(R.id.splash_screen_ic)
                appLaunchTv?.visibility = View.GONE
                appLaunchIc?.animate()?.apply {
                    duration = 1500
                    scaleXBy(.5f)
                    scaleYBy(.5f)
                }?.withEndAction {
                    view?.findNavController()?.navigate(R.id.action_splashScreenFragment_to_mainFragment)
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        launch {
//            delay(3000)
//            withContext(Dispatchers.Main){
//                view?.findNavController()?.navigate(R.id.action_splashScreenFragment_to_mainFragment)
//            }
//        }
//    }




    companion object {

        fun newInstance() =
            SplashScreenFragment()
    }
}