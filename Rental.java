import java.util.Date;

public class Rental {
	private Video video ;
	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = 0 ;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == 1 ) {
			this.status = 1;
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented ;
		daysRented = getDaysRented();
		if ( daysRented <= 2) return limit;
		
		return video.getDaysRentedLimit();
	}

	public int getDaysRented() {
		int daysRented;
		long now;
		if (getStatus() == 1) { // returned Video
			now = returnDate.getTime();		
		} else { // not yet returned
			now = new Date().getTime();
		}
		long diff = now - rentDate.getTime();
		daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		return daysRented;
	}
	
	public double getCharge() {
		int daysRented = getDaysRented();
		double charge = 0;
		
		switch (getVideoPriceCode()) {
		case Video.REGULAR:
			charge += 2;
			if (daysRented > 2)
				charge += (daysRented - 2) * 1.5;
			break;
		case Video.NEW_RELEASE:
			charge = daysRented * 3;
			break;
		default:
			break;
		}
		return charge;
	}

	public int getPoint() {
		int point = 0;
		int daysRented = getDaysRented();

		point++;

		if ((getVideoPriceCode() == Video.NEW_RELEASE) )
			point++;

		if ( daysRented > getDaysRentedLimit() )
			point -= Math.min(point, getVideo().getLateReturnPointPenalty()) ;

		return point;
	}
	
	public String getVideoTitle() {
		return video.getTitle();
	}
	
	public int getVideoPriceCode() {
		return video.getPriceCode();
	}
}
