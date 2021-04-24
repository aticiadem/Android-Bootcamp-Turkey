package com.aa.harcamalarabt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.adapter.ExpenseRecyclerAdapter
import com.aa.harcamalarabt.databinding.FragmentHomeBinding
import com.aa.harcamalarabt.model.ExpenseModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExpenseRecyclerAdapter
    private lateinit var expenseList: ArrayList<ExpenseModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseList = ArrayList()

        val x = ExpenseModel("Elektrik","190 TL")
        val y = ExpenseModel("Su","5 TL")
        val z = ExpenseModel("Ayakkabı","120 TL")
        val a = ExpenseModel("Araba","145900 TL")
        val b = ExpenseModel("Dükkan Kirası","4500 TL")
        val c = ExpenseModel("Baba Mekan","99999 TL")
        val d = ExpenseModel("Umarım Olmuştur","1 TL")

        expenseList.add(x)
        expenseList.add(y)
        expenseList.add(z)
        expenseList.add(a)
        expenseList.add(b)
        expenseList.add(c)
        expenseList.add(d)

        val layoutManager = LinearLayoutManager(requireActivity())

        adapter = ExpenseRecyclerAdapter(expenseList)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))

        binding.buttonDolar.setOnClickListener {
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

        binding.buttonSterlin.setOnClickListener {
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

        binding.buttonEuro.setOnClickListener {
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

        binding.buttonTl.setOnClickListener {
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

    }

}