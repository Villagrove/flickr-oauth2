package com.vu.scs.flickr.mbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vu.scs.flickr.bean.PersonDetail;

@ManagedBean(name = "basicProfileBean")
@RequestScoped
public class BasicProfileBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(BasicProfileBean.class);

	private static final long serialVersionUID = 1L;

	private String accessToken;

	private PersonDetail personDetail;

	public PersonDetail getPersonDetail() {
		return personDetail;
	}

	public void setPersonDetail(PersonDetail personDetail) {
		this.personDetail = personDetail;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@ManagedProperty(value = "#{dashboardBean}")
	// this is EL name of your bean
	private DashboardBean dashboardBean;

	public void setDashboardBean(DashboardBean dashboardBean) {
		this.dashboardBean = dashboardBean; 
	}

	@PostConstruct
	private void init() {
		personDetail = dashboardBean.getPersonDetail(); 
		logger.debug(personDetail.getName());
	}

}