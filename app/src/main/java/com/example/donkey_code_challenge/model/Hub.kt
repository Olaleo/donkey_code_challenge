package com.example.donkey_code_challenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hub(
    val id: Int,
    val name: String,
    val latitude: String,
    val longitude: String,
    val radius: Int,
    val bikes_count: Int,
    val optimal_capacity: Int,
    val maximum_capacity: Int,
    val state: String,
    val total_time_to_repair: Int,
    val open_tickets_count: Int,
    val disabling_tickets_count: Int,
    val battery_low_tickets_count: Int,
    val pickup_tickets_count: Int,
    val batteries_to_swap: Int,
    val disabled_vehicles_count: Int,
    val open_ticket_vehicles_count: Int,
    val time_to_repair_disabling_tickets: Int,
    val avg_time_to_repair_disabling_tickets: Int
) : Parcelable