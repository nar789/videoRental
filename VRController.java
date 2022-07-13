import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VRController {
	private List<Customer> customers;
	private List<Video> videos;

	public VRController(List<Customer> customers, List<Video> videos) {
		this.customers = customers;
		this.videos = videos;
	}

	public VRController() {
		this.customers = new ArrayList<Customer>();
		this.videos = new ArrayList<Video>();
	}

	public void init() {
		registerCustomer("James");
		registerCustomer("Brown");
		registerVideo("v1",  Video.CD, Video.REGULAR);
		registerVideo("v2", Video.DVD, Video.NEW_RELEASE);

		rentVideo(foundCustomer("James"),"v1");
		rentVideo(foundCustomer("James"),"v2");
	}

	public void clearRentals(Customer foundCustomer) {
		List<Rental> rentals = new ArrayList<Rental>() ;
		foundCustomer.setRentals(rentals);
	}
	
	public void returnVideo(Customer foundCustomer, String videoTitle) {
		List<Rental> customerRentals = foundCustomer.getRentals() ;
		for ( Rental rental: customerRentals ) {
			if ( rental.getVideoTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
				rental.returnVideo();
				rental.getVideo().setRented(false);
				break ;
			}
		}
	}

	public void rentVideo(Customer foundCustomer, String videoTitle) {
		Video foundVideo = null ;
		for ( Video video: getVideos() ) {
			if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
				foundVideo = video ;
				break ;
			}
		}

		if ( foundVideo == null ) return ;

		Rental rental = new Rental(foundVideo) ;
		foundVideo.setRented(true);

		List<Rental> customerRentals = foundCustomer.getRentals() ;
		customerRentals.add(rental);
		foundCustomer.setRentals(customerRentals);
	}

	public Customer foundCustomer(String customerName) {
		Customer foundCustomer = null ;
		for ( Customer customer: customers ) {
			if ( customer.getName().equals(customerName)) {
				foundCustomer = customer ;
				break ;
			}
		}
		return foundCustomer;
	}

	public void registerVideo(String title, int videoType, int priceCode) {
		Date registeredDate = new Date();
		Video video = new Video(title, videoType, priceCode, registeredDate) ;
		videos.add(video) ;
	}
	public void registerCustomer(String name) {
		Customer customer = new Customer(name) ;
		customers.add(customer) ;
	}


	public List<Customer> getCustomers() {
		return customers;
	}

	public List<Video> getVideos() {
		return videos;
	}

}