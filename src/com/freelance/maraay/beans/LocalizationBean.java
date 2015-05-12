package com.freelance.maraay.beans;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LocalizationBean {
	private boolean isEnglish = false;
	private final Locale ENGLISH = Locale.ENGLISH;
	private final Locale ARABIC = new Locale("ar");

	public boolean isEnglish() {
		return isEnglish;
	}

	public void setEnglish(boolean isEnglish) {
		this.isEnglish = isEnglish;
	}

	public Locale getENGLISH() {
		return ENGLISH;
	}

	public Locale getARABIC() {
		return ARABIC;
	}

	public Locale getLocale() {
		if (isEnglish) {
			return (ENGLISH);
		} else {
			return (ARABIC);
		}

	}

	public void swapLocal() {
		switchLocale();
	}

	private void switchLocale() {
		isEnglish = !isEnglish;
		Locale newLocale;
		if (isEnglish) {
			newLocale = ENGLISH;
		} else {
			newLocale = ARABIC;
		}
		FacesContext.getCurrentInstance().getViewRoot().setLocale(newLocale);
	}
}
