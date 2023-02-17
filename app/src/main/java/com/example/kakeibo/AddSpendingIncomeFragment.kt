package com.example.kakeibo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kakeibo.databinding.FragmentAddSpendingIncomeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddSpendingIncomeFragment : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentAddSpendingIncomeBinding
    var icLiving = false
    var icFood = false
    var icCafe = false
    var icTransportation = false
    var icFashion = false
    var icCommunication = false
    var icHealth = false
    var icLearn = false
    var icCulture = false
    var icBeauty = false
    var icPet = false
    var icGift = false

    var recyclerSum = 0

    private lateinit var listener: OnActionCompleteListener

    fun setOnActionCompleteListener(listener: OnActionCompleteListener) {
        this.listener = listener
    }

    interface OnActionCompleteListener {
        fun onActionComplete(item: IncomeNoteList)
    }


    //dialog 높이 지정
    override fun onStart() {
        super.onStart()
        view?.viewTreeObserver?.addOnGlobalLayoutListener {
            val behavior = BottomSheetBehavior.from(requireView().parent as View)
            behavior.peekHeight = 1700
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAddSpendingIncomeBinding.inflate(inflater, container, false)

        //아이콘 단일선택 클릭 구현한 부분
        viewBinding.icUnselectedLiving.setOnClickListener {
            icLiving = radioButton(
                viewBinding.icUnselectedLiving,
                icLiving,
                R.drawable.ic_selected_living
            )
        }
        viewBinding.icUnselectedFood.setOnClickListener {
            icFood = radioButton(
                viewBinding.icUnselectedFood,
                icFood,
                R.drawable.ic_selected_food
            )
        }
        viewBinding.icUnselectedCafe.setOnClickListener {
            icCafe = radioButton(
                viewBinding.icUnselectedCafe,
                icCafe,
                R.drawable.ic_selected_cafe
            )
        }
        viewBinding.icUnselectedTransportation.setOnClickListener {
            icTransportation = radioButton(
                viewBinding.icUnselectedTransportation,
                icTransportation,
                R.drawable.ic_selected_transportation
            )
        }
        viewBinding.icUnselectedFashion.setOnClickListener {
            icFashion = radioButton(
                viewBinding.icUnselectedFashion,
                icFashion,
                R.drawable.ic_selected_fashion
            )
        }
        viewBinding.icUnselectedCommunication.setOnClickListener {
            icCommunication = radioButton(
                viewBinding.icUnselectedCommunication,
                icCommunication,
                R.drawable.ic_selected_communication
            )
        }
        viewBinding.icUnselectedHealth.setOnClickListener {
            icHealth = radioButton(
                viewBinding.icUnselectedHealth,
                icHealth,
                R.drawable.ic_selected_health
            )
        }
        viewBinding.icUnselectedLearn.setOnClickListener {
            icLearn = radioButton(
                viewBinding.icUnselectedLearn,
                icLearn,
                R.drawable.ic_selected_learn
            )
        }
        viewBinding.icUnselectedCulture.setOnClickListener {
            icCulture = radioButton(
                viewBinding.icUnselectedCulture,
                icCulture,
                R.drawable.ic_selected_culture
            )
        }
        viewBinding.icUnselectedBeauty.setOnClickListener {
            icBeauty = radioButton(
                viewBinding.icUnselectedBeauty,
                icBeauty,
                R.drawable.ic_selected_beauty
            )
        }
        viewBinding.icUnselectedPet.setOnClickListener {
            icPet = radioButton(
                viewBinding.icUnselectedPet,
                icPet,
                R.drawable.ic_selected_pet
            )
        }
        viewBinding.icUnselectedGift.setOnClickListener {
            icGift = radioButton(
                viewBinding.icUnselectedGift,
                icGift,
                R.drawable.ic_selected_gift
            )
        }

        val bundle = Bundle()

        //'취소' 버튼 눌렀을 때 dialog dismiss 되도록 구현한 부분
        viewBinding.btnAddHistoryMainCancel.setOnClickListener {
//            Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show()
            dismiss()

            //EditText 부분 init
            viewBinding.tvAddHistoryMainContentBox.setText("") //내용
            viewBinding.tvAddHistoryMainMoneyBox.setText("") //금액 입력
        }

        //우측 상단 '완료' 버튼 눌렀을 때 실행되는 부분
        viewBinding.btnAddHistoryMainDone.setOnClickListener {
//            Toast.makeText(context, "완료", Toast.LENGTH_SHORT).show()
            bundle.putInt("dataIcn", resultRadio())
            bundle.putString(
                "dataContent",
                viewBinding.tvAddHistoryMainContentBox.text.toString()
            ) //내용
            bundle.putString(
                "dataMoney",
                viewBinding.tvAddHistoryMainMoneyBox.text.toString()
            ) //금액 입력

//            val intent = Intent(context, IncomeActivity::class.java)
//            intent.putExtra("bundle", bundle)
//            startActivity(intent)
//            dismiss()

            //EditText 부분 init
//            viewBinding.tvAddHistoryMainContentBox.setText("") //내용
//            viewBinding.tvAddHistoryMainMoneyBox.setText("") //금액 입력
        }

        //중앙 하단 '완료' 버튼 눌렀을 때 실행되는 부분
        viewBinding.btnAddHistoryMainBigDone.setOnClickListener {
//            Toast.makeText(context, "완료", Toast.LENGTH_SHORT).show()
            bundle.putInt("dataIcn", resultRadio())
            bundle.putString(
                "dataContent",
                viewBinding.tvAddHistoryMainContentBox.text.toString()
            ) //내용
            bundle.putString(
                "dataMoney",
                viewBinding.tvAddHistoryMainMoneyBox.text.toString()
            ) //금액 입력


//            val intent = Intent(context, IncomeActivity::class.java)
//            intent.putExtra("bundle", bundle)
//            startActivity(intent)
//            IncomeActivity().initAddData()

            listener.onActionComplete(
                IncomeNoteList(
                    resultRadio(), viewBinding.tvAddHistoryMainContentBox.text.toString(),
                    viewBinding.tvAddHistoryMainMoneyBox.text.toString()
                )
            )


            dismiss()

            //EditText 부분 init
            viewBinding.tvAddHistoryMainContentBox.setText("") //내용
            viewBinding.tvAddHistoryMainMoneyBox.setText("") //금액 입력
        }
        return viewBinding.root
    }

    //아이콘들이 radioButton(단일선택)처럼 작동하게 하는 함수
    fun radioButton(
        iconId: ImageButton,
        checkNum: Boolean,
        selectedImg: Int
    ): Boolean {
        return if (checkNum) { //selected -> unselected
            initRadioBox()
            false
        } else { //unselected -> selected
            initRadioBox()
            iconId.setImageResource(selectedImg)
            true
        }
    }

    //모든 아이콘의 이미지를 unselected, Boolean 변수를 false로 init하는 함수
    fun initRadioBox() {
        viewBinding.icUnselectedLiving.setImageResource(R.drawable.ic_unselected_living)
        viewBinding.icUnselectedFood.setImageResource(R.drawable.ic_unselected_food)
        viewBinding.icUnselectedCafe.setImageResource(R.drawable.ic_unselected_cafe)
        viewBinding.icUnselectedTransportation.setImageResource(R.drawable.ic_unselected_transportation)
        viewBinding.icUnselectedFashion.setImageResource(R.drawable.ic_unselected_fashion)
        viewBinding.icUnselectedCommunication.setImageResource(R.drawable.ic_unselected_communication)

        viewBinding.icUnselectedHealth.setImageResource(R.drawable.ic_unselected_health)
        viewBinding.icUnselectedLearn.setImageResource(R.drawable.ic_unselected_learn)
        viewBinding.icUnselectedCulture.setImageResource(R.drawable.ic_unselected_culture)
        viewBinding.icUnselectedBeauty.setImageResource(R.drawable.ic_unselected_beauty)
        viewBinding.icUnselectedPet.setImageResource(R.drawable.ic_unselected_pet)
        viewBinding.icUnselectedGift.setImageResource(R.drawable.ic_unselected_gift)

        icLiving = false
        icFood = false
        icCafe = false
        icTransportation = false
        icFashion = false
        icCommunication = false
        icHealth = false
        icLearn = false
        icCulture = false
        icBeauty = false
        icPet = false
        icGift = false
    }

    //Boolean값이 true인 아이콘(사용자가 최종으로 선택한 아이콘)의 halfselected 이미지를 return하는 함수
    fun resultRadio(): Int {
        if (icLiving)
            return R.drawable.ic_halfselected_living
        if (icFood)
            return R.drawable.ic_halfselected_food
        if (icCafe)
            return R.drawable.ic_halfselected_cafe
        if (icTransportation)
            return R.drawable.ic_halfselected_transportation
        if (icFashion)
            return R.drawable.ic_halfselected_fashion
        if (icCommunication)
            return R.drawable.ic_halfselected_communication
        if (icHealth)
            return R.drawable.ic_halfselected_health
        if (icLearn)
            return R.drawable.ic_halfselected_learn
        if (icCulture)
            return R.drawable.ic_halfselected_culture
        if (icBeauty)
            return R.drawable.ic_halfselected_beauty
        if (icPet)
            return R.drawable.ic_halfselected_pet
        if (icGift)
            return R.drawable.ic_halfselected_gift

        return 0
    }
}