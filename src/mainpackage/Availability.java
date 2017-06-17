package mainpackage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Availability {
	private Doctor doctor;
	private Timestamp start;
	private Timestamp end;
	private List<Timestamp> avail;

	public Availability(Doctor doctor, Timestamp start, Timestamp end) {
		this.doctor = doctor;
		this.start = start;
		this.end = end;
		avail =  new ArrayList<>();
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public void addHour(Timestamp h){
		if(avail!=null){
				avail.add(h);
		}
	}

	// https://stackoverflow.com/questions/28578072/split-java-util-date-collection-by-days
	@SuppressWarnings("deprecation")
	public List<List<Timestamp>> getAvail(){
		long millisPerDay = TimeUnit.DAYS.toMillis(1);
		List<List<Timestamp>> r =  new ArrayList<>(avail.stream().sorted().collect(
		        Collectors.groupingBy( t -> t.getDate(),
			            LinkedHashMap::new,
			            Collectors.toList())).values());
		//add kenes listes anamesa stis meres pou dn exei kanena rantevou
		for(int i=0; i< (end.getTime()-start.getTime())/millisPerDay; i++){
			if(r.size()<=i || r.get(i).get(0).getTime()> (start.getTime()+ (i+1)* millisPerDay)){
					r.add(i,new ArrayList<>());
			}
		}
		return r;
	}

}
