package com.enomusence.projectgm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.enomusence.projectgm.databinding.MusicPlayerBinding

class MusicPlayerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    // Binding 선언
    private lateinit var binding: MusicPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = MusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 여기에 뷰를 초기화하고 이벤트 핸들러를 설정하는 등의 코드 추가

        // 닫기 버튼 클릭 이벤트 처리
        binding.closeBtn.setOnClickListener {
            dismiss() // BottomSheetDialog 닫기
        }

        // 기타 초기화 및 이벤트 핸들러 설정
    }

    // 정보 초기화
    fun updateMusicInfo(url: String, albumCover: String, singer: String, title: String) {
        binding.startTime.text = "0:00" // 초기화 또는 필요한 초기 작업 수행
        binding.finishTime.text = "0:00" // 초기화 또는 필요한 초기 작업 수행

        //binding.musicImg.load(albumCover)
        binding.songTitle.text = title
        binding.singer.text = singer
    }
}