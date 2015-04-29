package cola.machine.calendar.user.bean;

import java.sql.Timestamp;

public class Active {
private String activeid;
private String userid;
private boolean actived;
public boolean isActived() {
	return actived;
}
public void setActived(boolean actived) {
	this.actived = actived;
}
private Timestamp activedtime;
public String getActiveid() {
	return activeid;
}
public void setActiveid(String activeid) {
	this.activeid = activeid;
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}
public Timestamp getActivedtime() {
	return activedtime;
}
public void setActivedtime(Timestamp activedtime) {
	this.activedtime = activedtime;
}
}
