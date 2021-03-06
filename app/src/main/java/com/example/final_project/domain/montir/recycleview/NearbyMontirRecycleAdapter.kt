package com.example.final_project.domain.montir.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.final_project.R
import com.example.final_project.config.defaultHost
import com.example.final_project.domain.montir.model.NearbyMontirProfile
import com.example.final_project.domain.montir.fragment.NearbyMontirFragment
import com.pixplicity.easyprefs.library.Prefs
import java.math.RoundingMode

class NearbyMontirRecycleAdapter(
    private val listMontir: List<NearbyMontirProfile>,
    private val activity: NearbyMontirFragment
) :
    RecyclerView.Adapter<MontirViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MontirViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.nearby_montir_recycle_view, parent, false)
        return MontirViewHolder(view)
    }

    override fun onBindViewHolder(holder: MontirViewHolder, position: Int) {
        holder.firstname.text = listMontir[position].firstname
        holder.lastname.text = listMontir[position].lastname
        holder.status.text = listMontir[position].status.status_activity

        var formatedRating = "5.0"
        if (!listMontir[position].rating.average_rating.isNullOrEmpty()) {
            formatedRating = listMontir[position].rating.average_rating.toFloat().toBigDecimal()
                .setScale(1, RoundingMode.UP).toString()
            holder.rating.text = formatedRating
        } else {
            holder.rating.text = formatedRating
        }

        val number = listMontir[position].distance.toFloat() / 1000
        val rounded = number.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        holder.distance.text = "$rounded"

        val url = "${defaultHost()}montir/file/image/${listMontir[position].imageUrl}"
        val glideUrl = GlideUrl(
            url,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer ${Prefs.getString("token", "0")}")
                .build()
        )
        Glide.with(activity)
            .load(glideUrl).circleCrop()
            .into(holder.photo)

        holder.itemView.setOnClickListener {
            Prefs.putString("selectedMontirId", listMontir[position].id.toString())
            Prefs.putString("montirAverageRating", formatedRating)
            Navigation.findNavController(it)
                .navigate(R.id.action_nearbyMontirFragment_to_montirDetailFragment)
        }
    }

    override fun getItemCount(): Int {
        return listMontir.size
    }
}

class MontirViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val photo = view.findViewById<ImageView>(R.id.photo_nearby_montir)
    val firstname = view.findViewById<TextView>(R.id.firstname_nearby_montir)
    val lastname = view.findViewById<TextView>(R.id.lastname_nearby_montir)
    val rating = view.findViewById<TextView>(R.id.rating_nearby_montir)
    val distance = view.findViewById<TextView>(R.id.distance_nearby_montir)
    val status = view.findViewById<TextView>(R.id.status_nearby_montir)
}
