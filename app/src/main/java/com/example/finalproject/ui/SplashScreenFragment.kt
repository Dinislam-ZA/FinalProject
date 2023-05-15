package com.example.finalproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        launch {
            delay(3000)
            withContext(Dispatchers.Main){
                view.findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
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