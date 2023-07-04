package com.driver;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryLayer {

    Map<String,Hotel> hotelDB = new HashMap<>();
    Map<Integer,User> userDB = new HashMap<>();
    Map<String,Booking> bookingDB = new HashMap<>();

    public String addHotel(Hotel hotel) {
        if (hotel.getHotelName()==null || hotel==null || hotelDB.containsKey(hotel.getHotelName()))
            return "FAILURE";
        else{
            hotelDB.put(hotel.getHotelName(),hotel);
            return "SUCCESS";
        }
    }

    public int addUser(User user) {

        userDB.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String hotelName = "";
        int size = 0;
        for( String name : hotelDB.keySet()){
            if(hotelDB.get(name).getFacilities().size()>size){
                size = hotelDB.get(name).getFacilities().size();
                    hotelName = name ;

            }
            else if(hotelDB.get(name).getFacilities().size()==size){
                if (hotelName.compareTo(name)>0){
                    hotelName = name ;
                }
            }
        }

        if( size==0){
            return "";
        }
        return hotelName;
    }

    public int bookARoom(Booking booking) {
        bookingDB.put(booking.getBookingId(),booking);

        String hotalName = booking.getHotelName();
        int nobOfRooms = booking.getNoOfRooms();
        int pricePerNight = hotelDB.get(hotalName).getPricePerNight();
        int availableRooms = hotelDB.get(hotalName).getAvailableRooms();
        int roomNeed = booking.getNoOfRooms();
        if(roomNeed>availableRooms) return -1;

        booking.setAmountToBePaid(pricePerNight*nobOfRooms);
        return pricePerNight*nobOfRooms;
    }

    public int getBookings(Integer aadharCard) {
        int booking = 0;
        for (String id : bookingDB.keySet()){
            int adhar = bookingDB.get(id).getBookingAadharCard();
            if (adhar==aadharCard) booking++;
        }
        return booking;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDB.get(hotelName);
            hotel.setFacilities(newFacilities);
            return hotel;
    }
}
