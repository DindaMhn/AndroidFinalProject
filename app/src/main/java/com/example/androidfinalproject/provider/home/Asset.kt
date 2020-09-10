package com.example.androidfinalproject.provider.home

class Asset(
    var id: String = "",
    var id_wallet: String = "",
    var provider_id: String = "",
    var asset_name: String = "",
    var asset_area: String = "",
    var longitude: String = "",
    var latitude: String = "",
    var car_capacity: String = "",
    var motorcycle_capacity: String = "",
    var bicycle_capacity: String = "",
    var photo: String = "",
    var createdAt: String = "",
    var editedAt: String = "",
    var deletedAt: String = "",
    var status: String = ""
)

class AssetView(
    var id: String = "",
    var asset_name: String = "",
    var asset_area: String = "",
    var longitude: String = "",
    var latitude: String = "",
    var car_capacity: String = "",
    var motor_capacity: String = "",
    var bicycle_capacity: String = "",
    var photo: String = "",
    var saldo: String = "",
    var status: String = ""
)

class AssetDailyView(
    var asset_name: String = "",
    var date: String = "",
    var total_parked: String = "",
    var total_revenue: String = ""
)

class AssetMonthlyView(
    var asset_name: String = "",
    var months: String = "",
    var total_parked: String = "",
    var total_revenue: String = ""
)
