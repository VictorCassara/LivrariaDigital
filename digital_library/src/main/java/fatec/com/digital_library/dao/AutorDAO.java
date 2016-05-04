package fatec.com.digital_library.dao;

import java.util.List;

import fatec.com.digital_library.entity.Autor;

public interface AutorDAO {
	
	public List<Autor> fetchAutors();
	public boolean createAutor(Autor autor);
	public boolean updateAutor(Autor autor, Autor oldAutor);
	public boolean removeAutor(Autor autor);
	public Autor fetchAutor(Autor autor);

}
