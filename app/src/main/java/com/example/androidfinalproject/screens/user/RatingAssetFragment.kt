package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.rating.Rating
import com.example.androidfinalproject.user.rating.RatingViewModel
import kotlinx.android.synthetic.main.fragment_detail_ticket.*
import kotlinx.android.synthetic.main.fragment_rating_asset.*
import javax.inject.Inject


class RatingAssetFragment : Fragment() {
    @Inject
    lateinit var ratingViewModel: RatingViewModel
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating_asset, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_rating.setOnClickListener {
            val newRating = Rating(
                user_id = sharedPreferences?.getString("ID_USER", "default").toString()
                , asset_id = sharedPreferences?.getString("ID_ASSET_TICKET", "default").toString()
                , rating = ratingBar.rating.toString()
                , comment = rating_comment.text.toString()
            )
            ratingViewModel.createRating(newRating)
            view?.findNavController()
                ?.navigate(R.id.action_global_homeUserFragment)
        }
        gedung_rating.text =
            sharedPreferences?.getString("ASSET_NAME_TICKET", "default").toString()
    }
}