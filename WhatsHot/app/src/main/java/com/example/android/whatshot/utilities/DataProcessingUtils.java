package com.example.android.whatshot.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.whatshot.R;

import java.util.Hashtable;

public class DataProcessingUtils {
    private static final Hashtable<String,Integer> TYPES = new Hashtable<String, Integer>() {
        {
            put("accounting",R.mipmap.ic_generic_business_71);
            put("airport", R.mipmap.ic_airport_71);
            put("amusement_park",R.mipmap.ic_amusement_71);
            put("aquarium",R.mipmap.ic_aquarium_71);
            put("art_gallery",R.mipmap.ic_art_gallery_71);
            put("atm",R.mipmap.ic_atm_71);
            put("bakery",R.mipmap.ic_shopping_71);
            put("bank",R.mipmap.ic_bank_dollar_71);
            put("bar",R.mipmap.ic_bar_71);
            put("beauty_salon",R.mipmap.ic_barber_71);
            put("bicycle_store",R.mipmap.ic_bicycle_71);
            put("book_store",R.mipmap.ic_library_71);
            put("bowling_alley",R.mipmap.ic_bowling_71);
            put("bus_station",R.mipmap.ic_bus_71);
            put("cafe",R.mipmap.ic_cafe_71);
            put("campground",R.mipmap.ic_camping_71);
            put("car_dealer",R.mipmap.ic_car_dealer_71);
            put("car_rental",R.mipmap.ic_car_rental_71);
            put("car_repair",R.mipmap.ic_car_repair_71);
            put("car_wash",R.mipmap.ic_car_dealer_71);
            put("casino",R.mipmap.ic_casino_71);
            put("cemetery",R.mipmap.ic_worship_christian_71);
            put("church",R.mipmap.ic_worship_christian_71);
            put("city_hall",R.mipmap.ic_civic_building_71);
            put("clothing_store",R.mipmap.ic_shopping_71);
            put("convenience_store",R.mipmap.ic_convenience_71);
            put("courthouse",R.mipmap.ic_courthouse_71);
            put("dentist",R.mipmap.ic_dentist_71);
            put("department_store",R.mipmap.ic_shopping_71);
            put("doctor",R.mipmap.ic_doctor_71);
            put("electrician",R.mipmap.ic_generic_business_71);
            put("electronics_store",R.mipmap.ic_electronics_71);
            put("embassy",R.mipmap.ic_civic_building_71);
            put("fire_station",R.mipmap.ic_police_71);
            put("florist",R.mipmap.ic_flower_71);
            put("funeral_home",R.mipmap.ic_worship_general_71);
            put("furniture_store",R.mipmap.ic_generic_business_71);
            put("gas_station",R.mipmap.ic_gas_station_71);
            put("gym",R.mipmap.ic_fitness_71);
            put("hair_care",R.mipmap.ic_barber_71);
            put("hardware_store",R.mipmap.ic_generic_business_71);
            put("hindu_temple",R.mipmap.ic_worship_hindu_71);
            put("home_goods_store",R.mipmap.ic_shopping_71);
            put("hospital",R.mipmap.ic_doctor_71);
            put("insurance_agency",R.mipmap.ic_generic_business_71);
            put("jewelry_store",R.mipmap.ic_jewelry_71);
            put("laundry",R.mipmap.ic_generic_business_71);
            put("lawyer",R.mipmap.ic_generic_business_71);
            put("library",R.mipmap.ic_library_71);
            put("liquor_store",R.mipmap.ic_wine_71);
            put("local_government_office",R.mipmap.ic_civic_building_71);
            put("locksmith",R.mipmap.ic_generic_business_71);
            put("lodging",R.mipmap.ic_lodging_71);
            put("meal_delivery",R.mipmap.ic_restaurant_71);
            put("meal_takeaway",R.mipmap.ic_restaurant_71);
            put("mosque",R.mipmap.ic_worship_islam_71);
            put("movie_rental",R.mipmap.ic_movies_71);
            put("movie_theater",R.mipmap.ic_movies_71);
            put("moving_company",R.mipmap.ic_truck_71);
            put("museum",R.mipmap.ic_museum_71);
            put("night_club",R.mipmap.ic_wine_71);
            put("painter",R.mipmap.ic_generic_business_71);
            put("park",R.mipmap.ic_generic_recreational_71);
            put("parking",R.mipmap.ic_generic_recreational_71);
            put("pet_store",R.mipmap.ic_pet_71);
            put("pharmacy",R.mipmap.ic_doctor_71);
            put("physiotherapist",R.mipmap.ic_doctor_71);
            put("plumber",R.mipmap.ic_repair_71);
            put("police",R.mipmap.ic_police_71);
            put("post_office",R.mipmap.ic_post_office_71);
            put("real_estate_agency",R.mipmap.ic_generic_business_71);
            put("restaurant",R.mipmap.ic_restaurant_71);
            put("roofing_contractor",R.mipmap.ic_repair_71);
            put("rv_park",R.mipmap.ic_generic_business_71);
            put("school",R.mipmap.ic_school_71);
            put("shoe_store",R.mipmap.ic_shopping_71);
            put("shopping_mall",R.mipmap.ic_shopping_71);
            put("spa",R.mipmap.ic_generic_business_71);
            put("stadium",R.mipmap.ic_stadium_71);
            put("storage",R.mipmap.ic_generic_business_71);
            put("store",R.mipmap.ic_shopping_71);
            put("subway_station",R.mipmap.ic_train_71);
            put("supermarket",R.mipmap.ic_supermarket_71);
            put("synagogue",R.mipmap.ic_worship_jewish_71);
            put("taxi_stand",R.mipmap.ic_taxi_71);
            put("train_station",R.mipmap.ic_train_71);
            put("transit_station",R.mipmap.ic_train_71);
            put("travel_agency",R.mipmap.ic_travel_agent_71);
            put("veterinary_care",R.mipmap.ic_pet_71);
            put("zoo",R.mipmap.ic_zoo_71);
        }
    };



    public static String getTopVenueType(String types) {
        String[] splitTypes = TextUtils.split(types.replaceAll("-",""),",");
        Log.d("Types",TextUtils.join("+",splitTypes));

        
        return splitTypes[0]; // this will get smarter
    }
    
    public static int getStringId(String type) {
        Integer resId = TYPES.get(type);

        return (resId != null) ? resId : R.mipmap.ic_generic_business_71;
    }
}
