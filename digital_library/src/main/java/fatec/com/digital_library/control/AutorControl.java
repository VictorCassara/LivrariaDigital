package fatec.com.digital_library.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fatec.com.digital_library.dao.AutorDAO;
import fatec.com.digital_library.dao.impl.AutorDAOImpl;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean
@RequestScoped
public class AutorControl {

	private Autor autor = new Autor();
	private List<Autor> autorList = new ArrayList<Autor>();
	private AutorDAO autorDAO = new AutorDAOImpl();
	private String condition;
	private static Autor oldAutor;
	@ManagedProperty(value = "#{loader}")
	private Loader loader;

	@PostConstruct
	public void onLoad() {
		this.autorList = loader.getAutorList();
	}

	public void createAutor(Autor autor) {
		if (autorDAO.createAutor(autor)) {
			loader.loadAutors();
			autorList.add(autor);
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.ADD_AUTOR_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.ADD_AUTOR_FAILURE, condition);
		}

	}

	public void removeAutor(Autor autor) {
		if (autorDAO.removeAutor(autor)) {
			loader.loadAutors();
			autorList.remove(autor);
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.REMOVE_AUTOR_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.REMOVE_AUTOR_FAILURE, condition);
		}
	}

	public void alterAutor(Autor autor, Autor oldAutor) {
		if (autorDAO.updateAutor(autor, oldAutor)) {
			condition = DigitalLibraryConstants.INFO;
			loader.loadAutors();
			this.autorList = loader.getAutorList();
			addMessage(DigitalLibraryConstants.UPDATE_AUTOR_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.UPDATE_AUTOR_FAILURE, condition);
		}
	}

	public void loadUpdateAutor(Autor autor) {
		this.oldAutor = new Autor();
		this.oldAutor.setName(autor.getName());
		this.oldAutor.setStateOfBirth(autor.getStateOfBirth());
		this.oldAutor.setCityOfBirth(autor.getCityOfBirth());
		this.oldAutor.setCountryOfBirth(autor.getCountryOfBirth());
		this.oldAutor.setBirthDate(autor.getBirthDate());
		this.autor = autor;
	}

	public Autor fetchAutor(Autor autor) {
		return autorDAO.fetchAutor(autor);
	}

	public List<Autor> fetchCategories() {
		return autorDAO.fetchAutors();
	}

	public void addMessage(String summary, String condition) {
		FacesMessage message;

		switch (condition) {
		case DigitalLibraryConstants.INFO:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case DigitalLibraryConstants.ERROR:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case DigitalLibraryConstants.WARN:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		default:
			break;
		}

	}

	public List<String> completeCity(String query) {
		List<String> cityList = new ArrayList<String>();

		for (int i = 0; i < loader.getCityList().size(); i++) {
			String city = loader.getCityList().get(i);
			if (city.startsWith(query)) {
				cityList.add(city);
			} else if (city.toLowerCase().startsWith(query)) {
				cityList.add(city);
			}
		}

		return cityList;
	}

	public List<String> completeState(String query) {
		List<String> stateList = new ArrayList<String>();

		for (int i = 0; i < loader.getStateList().size(); i++) {
			String state = loader.getStateList().get(i);
			if (state.startsWith(query)) {
				stateList.add(state);
			} else if (state.toLowerCase().startsWith(query)) {
				stateList.add(state);
			}
		}
		return stateList;
	}

	public List<String> completeCountry(String query) {
		List<String> countryList = new ArrayList<String>();

		for (int i = 0; i < loader.getCountryList().size(); i++) {
			String state = loader.getCountryList().get(i);
			if (state.startsWith(query)) {
				countryList.add(state);
			} else if (state.toLowerCase().startsWith(query)) {
				countryList.add(state);
			}
		}
		return countryList;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<Autor> getAutorList() {
		return autorList;
	}

	public void setAutorList(List<Autor> autorList) {
		this.autorList = autorList;
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public Autor getOldAutor() {
		return oldAutor;
	}

	public void setOldAutor(Autor oldAutor) {
		this.oldAutor = oldAutor;
	}

}
