package com.example.android.whatshot.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.whatshot.R;

import java.util.Hashtable;
 


public class DataProcessingUtils {
    private static class TypesTuple {
        int stringId;
        int iconId;

        TypesTuple(int iconId, int stringId) {
            this.stringId = stringId;
            this.iconId = iconId;
        }
    }
    
    private static final Hashtable<String,TypesTuple> TYPES = new Hashtable<String, TypesTuple>() {
        {
            put("accounting", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_accounting ));
            put("airport", new TypesTuple(R.mipmap.ic_airport_71,R.string.place_airport ));
            put("amusement_park", new TypesTuple(R.mipmap.ic_amusement_71,R.string.place_amusement_park ));
            put("aquarium", new TypesTuple(R.mipmap.ic_aquarium_71,R.string.place_aquarium ));
            put("art_gallery", new TypesTuple(R.mipmap.ic_art_gallery_71,R.string.place_art_gallery ));
            put("atm", new TypesTuple(R.mipmap.ic_atm_71,R.string.place_atm ));
            put("bakery", new TypesTuple(R.mipmap.ic_shopping_71,R.string.place_bakery ));
            put("bank", new TypesTuple(R.mipmap.ic_bank_dollar_71,R.string.place_bank ));
            put("bar", new TypesTuple(R.mipmap.ic_bar_71,R.string.place_bar ));
            put("beauty_salon", new TypesTuple(R.mipmap.ic_barber_71,R.string.place_beauty_salon ));
            put("bicycle_store", new TypesTuple(R.mipmap.ic_bicycle_71,R.string.place_bicycle_store ));
            put("book_store", new TypesTuple(R.mipmap.ic_library_71,R.string.place_book_store ));
            put("bowling_alley", new TypesTuple(R.mipmap.ic_bowling_71,R.string.place_bowling_alley ));
            put("bus_station", new TypesTuple(R.mipmap.ic_bus_71,R.string.place_bus_station ));
            put("cafe", new TypesTuple(R.mipmap.ic_cafe_71,R.string.place_cafe ));
            put("campground", new TypesTuple(R.mipmap.ic_camping_71,R.string.place_campground ));
            put("car_dealer", new TypesTuple(R.mipmap.ic_car_dealer_71,R.string.place_car_dealer ));
            put("car_rental", new TypesTuple(R.mipmap.ic_car_rental_71,R.string.place_car_rental ));
            put("car_repair", new TypesTuple(R.mipmap.ic_car_repair_71,R.string.place_car_repair ));
            put("car_wash", new TypesTuple(R.mipmap.ic_car_dealer_71,R.string.place_car_wash ));
            put("casino", new TypesTuple(R.mipmap.ic_casino_71,R.string.place_casino ));
            put("cemetery", new TypesTuple(R.mipmap.ic_worship_christian_71,R.string.place_cemetery ));
            put("church", new TypesTuple(R.mipmap.ic_worship_christian_71,R.string.place_church ));
            put("city_hall", new TypesTuple(R.mipmap.ic_civic_building_71,R.string.place_city_hall ));
            put("clothing_store", new TypesTuple(R.mipmap.ic_shopping_71,R.string.place_clothing_store ));
            put("convenience_store", new TypesTuple(R.mipmap.ic_convenience_71,R.string.place_convenience_store ));
            put("courthouse", new TypesTuple(R.mipmap.ic_courthouse_71,R.string.place_courthouse ));
            put("dentist", new TypesTuple(R.mipmap.ic_dentist_71,R.string.place_dentist ));
            put("department_store", new TypesTuple(R.mipmap.ic_shopping_71,R.string.place_department_store ));
            put("doctor", new TypesTuple(R.mipmap.ic_doctor_71,R.string.place_doctor ));
            put("electrician", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_electrician ));
            put("electronics_store", new TypesTuple(R.mipmap.ic_electronics_71,R.string.place_electronics_store ));
            put("embassy", new TypesTuple(R.mipmap.ic_civic_building_71,R.string.place_embassy ));
            put("fire_station", new TypesTuple(R.mipmap.ic_police_71,R.string.place_fire_station ));
            put("florist", new TypesTuple(R.mipmap.ic_flower_71,R.string.place_florist ));
            put("funeral_home", new TypesTuple(R.mipmap.ic_worship_general_71,R.string.place_funeral_home ));
            put("furniture_store", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_furniture_store ));
            put("gas_station", new TypesTuple(R.mipmap.ic_gas_station_71,R.string.place_gas_station ));
            put("gym", new TypesTuple(R.mipmap.ic_fitness_71,R.string.place_gym ));
            put("hair_care", new TypesTuple(R.mipmap.ic_barber_71,R.string.place_hair_care ));
            put("hardware_store", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_hardware_store ));
            put("hindu_temple", new TypesTuple(R.mipmap.ic_worship_hindu_71,R.string.place_hindu_temple ));
            put("home_goods_store", new TypesTuple(R.mipmap.ic_shopping_71,R.string.place_home_goods_store ));
            put("hospital", new TypesTuple(R.mipmap.ic_doctor_71,R.string.place_hospital ));
            put("insurance_agency", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_insurance_agency ));
            put("jewelry_store", new TypesTuple(R.mipmap.ic_jewelry_71,R.string.place_jewelry_store ));
            put("laundry", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_laundry ));
            put("lawyer", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_lawyer ));
            put("library", new TypesTuple(R.mipmap.ic_library_71,R.string.place_library ));
            put("liquor_store", new TypesTuple(R.mipmap.ic_wine_71,R.string.place_liquor_store ));
            put("local_government_office", new TypesTuple(R.mipmap.ic_civic_building_71,R.string.place_local_government_office ));
            put("locksmith", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_locksmith ));
            put("lodging", new TypesTuple(R.mipmap.ic_lodging_71,R.string.place_lodging ));
            put("meal_delivery", new TypesTuple(R.mipmap.ic_restaurant_71,R.string.place_meal_delivery ));
            put("meal_takeaway", new TypesTuple(R.mipmap.ic_restaurant_71,R.string.place_meal_takeaway ));
            put("mosque", new TypesTuple(R.mipmap.ic_worship_islam_71,R.string.place_mosque ));
            put("movie_rental", new TypesTuple(R.mipmap.ic_movies_71,R.string.place_movie_rental ));
            put("movie_theater", new TypesTuple(R.mipmap.ic_movies_71,R.string.place_movie_theater ));
            put("moving_company", new TypesTuple(R.mipmap.ic_truck_71,R.string.place_moving_company ));
            put("museum", new TypesTuple(R.mipmap.ic_museum_71,R.string.place_museum ));
            put("night_club", new TypesTuple(R.mipmap.ic_wine_71,R.string.place_night_club ));
            put("painter", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_painter ));
            put("park", new TypesTuple(R.mipmap.ic_generic_recreational_71,R.string.place_park ));
            put("parking", new TypesTuple(R.mipmap.ic_generic_recreational_71,R.string.place_parking ));
            put("pet_store", new TypesTuple(R.mipmap.ic_pet_71,R.string.place_pet_store ));
            put("pharmacy", new TypesTuple(R.mipmap.ic_doctor_71,R.string.place_pharmacy ));
            put("physiotherapist", new TypesTuple(R.mipmap.ic_doctor_71,R.string.place_physiotherapist ));
            put("plumber", new TypesTuple(R.mipmap.ic_repair_71,R.string.place_plumber ));
            put("police", new TypesTuple(R.mipmap.ic_police_71,R.string.place_police ));
            put("post_office", new TypesTuple(R.mipmap.ic_post_office_71,R.string.place_post_office ));
            put("real_estate_agency", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_real_estate_agency ));
            put("restaurant", new TypesTuple(R.mipmap.ic_restaurant_71,R.string.place_restaurant ));
            put("roofing_contractor", new TypesTuple(R.mipmap.ic_repair_71,R.string.place_roofing_contractor ));
            put("rv_park", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_rv_park ));
            put("school", new TypesTuple(R.mipmap.ic_school_71,R.string.place_school ));
            put("shoe_store", new TypesTuple(R.mipmap.ic_shopping_71,R.string.place_shoe_store ));
            put("shopping_mall", new TypesTuple(R.mipmap.ic_shopping_71,R.string.place_shopping_mall ));
            put("spa", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_spa ));
            put("stadium", new TypesTuple(R.mipmap.ic_stadium_71,R.string.place_stadium ));
            put("storage", new TypesTuple(R.mipmap.ic_generic_business_71,R.string.place_storage ));
            put("store", new TypesTuple(R.mipmap.ic_shopping_71,R.string.place_store ));
            put("subway_station", new TypesTuple(R.mipmap.ic_train_71,R.string.place_subway_station ));
            put("supermarket", new TypesTuple(R.mipmap.ic_supermarket_71,R.string.place_supermarket ));
            put("synagogue", new TypesTuple(R.mipmap.ic_worship_jewish_71,R.string.place_synagogue ));
            put("taxi_stand", new TypesTuple(R.mipmap.ic_taxi_71,R.string.place_taxi_stand ));
            put("train_station", new TypesTuple(R.mipmap.ic_train_71,R.string.place_train_station ));
            put("transit_station", new TypesTuple(R.mipmap.ic_train_71,R.string.place_transit_station ));
            put("travel_agency", new TypesTuple(R.mipmap.ic_travel_agent_71,R.string.place_travel_agency ));
            put("veterinary_care", new TypesTuple(R.mipmap.ic_pet_71,R.string.place_veterinary_care ));
            put("zoo", new TypesTuple(R.mipmap.ic_zoo_71,R.string.place_zoo ));
        }
    };



    public static String getTopVenueType(String types) {
        String[] splitTypes = TextUtils.split(types.replaceAll("-",""),",");
        Log.d("Types",TextUtils.join("+",splitTypes));

        
        return splitTypes[0]; // this will get smarter
    }
    
    public static int getStringIconId(String type) {
        TypesTuple resId = TYPES.get(type);

        return (resId != null) ? resId.iconId : R.mipmap.ic_generic_business_71;
    }

    public static int getStringPlaceId(String type) {
        TypesTuple resId = TYPES.get(type);

        return (resId != null) ? resId.stringId : R.string.place_generic;
    }
}
