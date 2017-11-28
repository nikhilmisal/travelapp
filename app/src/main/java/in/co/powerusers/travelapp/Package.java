package in.co.powerusers.travelapp;

/**
 * Created by Powerusers on 26-07-2017.
 */

public class Package {
    String pktitle;
    String fromloc;
    String duration;
    String places;
    String flight;
    String hotel;
    String sights;
    String pickup;
    String drop;
    String food;
    String price;
    String itinerary;

    public Package()
    {

    }

    public Package(String _pktitle,String _fromloc,String _duration,String _places,String _flight,String _hotel,String _sights,String _pickup,String _drop,String _food, String _price,String _itinerary)
    {
        pktitle = _pktitle;
        fromloc = _fromloc;
        duration = _duration;
        places = _places;
        flight = _flight;
        hotel = _hotel;
        sights = _sights;
        pickup = _pickup;
        drop = _drop;
        food = _food;
        price = _price;
        itinerary = _itinerary;
    }

    public String getPktitle() {
        return pktitle;
    }

    public void setPktitle(String pktitle) {
        this.pktitle = pktitle;
    }

    public String getFromloc() {
        return fromloc;
    }

    public void setFromloc(String fromloc) {
        this.fromloc = fromloc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getSights() {
        return sights;
    }

    public void setSights(String sights) {
        this.sights = sights;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItinerary() { return itinerary; }

    public void setItinerary(String itinerary) { this.itinerary = itinerary; }

    @Override
    public String toString()
    {
        return pktitle+"|"+fromloc+"|"+duration+"|"+places+"|"+flight+"|"+hotel+"|"+sights+"|"+pickup+"|"+drop+"|"+food+"|"+price;
    }
}
