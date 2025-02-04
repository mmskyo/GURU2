package com.example.guru2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MapFragment : Fragment() {
//    private val binding get() = _binding!!
//    private var _binding : FragmentMapBinding? = null
//
//    private lateinit var mapView : MapView
//    private var kakaoMap : KakaoMap? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        _binding = FragmentMapBinding.inflate(inflater, container, false)
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //showMapView()
    }



//    private fun showMapView(){
//
//        mapView = binding.mapView
//
//        mapView.start(object : MapLifeCycleCallback() {
//
//            override fun onMapDestroy() {
//                // 지도 API가 정상적으로 종료될 때 호출
//                Log.d("KakaoMap", "onMapDestroy")
//            }
//
//            override fun onMapError(p0: Exception?) {
//                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출
//                Log.e("KakaoMap", "onMapError")
//            }
//        }, object : KakaoMapReadyCallback(){
//            override fun onMapReady(kakaomap: KakaoMap) {
//                // 정상적으로 인증이 완료되었을 때 호출
//                kakaoMap = kakaomap
//            }
//        })
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}