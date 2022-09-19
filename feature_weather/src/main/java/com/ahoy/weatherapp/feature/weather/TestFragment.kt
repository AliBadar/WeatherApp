//package com.ahoy.weatherapp.feature.weather
//
//import android.content.Context
//import androidx.lifecycle.ViewModelProvider
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.ahoy.data.TestRepositoryImp
//import com.ahoy.weatherapp.feature.di.inject
//import javax.inject.Inject
//
//class TestFragment : Fragment(){
//
//    companion object {
//        fun newInstance() = TestFragment()
//    }
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//
//    private val viewModel: TestViewModel by lazy {
//        ViewModelProvider(this, viewModelFactory)[TestViewModel::class.java]
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_test, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        var testRepositoryImp = TestRepositoryImp()
//        viewModel.setRepo(testRepositoryImp)
//
//        var desc = viewModel.getDescription().plus("Test Fragment")
//
//        view.findViewById<TextView>(R.id.txt_test).text = desc
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        inject()
//    }
//
//
//
//}