package es.curso.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.entity.Coche;
import es.curso.repository.CocheRepository;
import es.curso.service.CocheService;


@Service
public class CocheServiceImpl implements CocheService {

	@Autowired
	private CocheRepository cocheRepository;

	/**
	 * @param c -> Objeto coche
	 * @return -> Devuelve un objeto HashMap indicando si est� todo correcto o han habido errores
	 */
	@Override
	@Transactional
	public HashMap<String, String> alta(Coche c) {
		return altaAndModificar(c, 1);
	}

	/**
	 * @param id -> par�metro para buscar a trav�s del repositorio un objeto Coche con esa ID
	 * @return -> Devuelve un objeto HashMap indicando si est� todo correcto o han habido errores
	 */
	@Override
	@Transactional
	public HashMap<String, String> baja(int id) {
		HashMap<String, String> messages = new HashMap<>();
		Coche coche = cocheRepository.findById(id).orElse(null);
		if (coche != null) {		
			cocheRepository.deleteById(id);
			messages.put("Baja Correcta -> ", "El vehiculo se ha eliminado correctamente");
		} else {
			messages.put("Null Error -> ", "El coche con la ID " + id + " no se encuentra en la bbdd");
		}
		return messages;
	}

	/**
	 * @param c -> Objeto coche
	 * @return -> Devuelve un objeto HashMap indicando si est� todo correcto o han habido errores
	 */
	@Override
	@Transactional
	public HashMap<String, String> modificar(Coche c) {
		return altaAndModificar(c, 2);
	}

	/**
	 * @param id -> par�metro para buscar a trav�s del repositorio un objeto Coche con esa ID
	 * @return -> Devuelve el objeto Coche encontrado con esa ID
	 */
	@Override
	public Coche obtener(int id) {
		return cocheRepository.findById(id).orElse(null);
	}

	/**
	 * @return -> Devuelve la lista de los coches existentes en la BBDD
	 */
	@Override
	public List<Coche> listar() {
		return cocheRepository.findAll();
	}
	
	/**
	 * @param c -> Objeto coche
	 * @param message -> Mensaje indicando si es Alta o Modificaci�n
	 * @return -> Devuelve un objeto HashMap indicando si est� todo correcto o han habido errores
	 */
	@Transactional
	public HashMap<String, String> altaAndModificar(Coche c, int option) {
		
		HashMap<String, String> messages = new HashMap<>();
		if (c != null) {
			if (c.getMarca().length() == 0) {
				messages.put("Marca Error -> ", "El campo Marca no puede estar vac�o");
			} 
			if (c.getMatricula().length() != 7) {
				messages.put("Matricula Error -> ", "El campo Matricula debe tener 7 caracteres");
			}
			if (c.getModel().length() == 0) {
				messages.put("Model Error -> ", "El campo Modelo no puede estar vac�o");
			}
			if (messages.isEmpty()) {
				if (option == 1) {
					this.cocheRepository.save(c);
					messages.put("Alta Correcta -> ", "El vehiculo ha sido creado correctamente");
				} else {
					this.cocheRepository.save(c);
					messages.put("Modificar Correcta -> ", "El vehiculo ha sido modificado correctamente");
				}
				
			}
		} else {
			messages.put("Null Error -> ", "El objeto coche no puede ser nulo");
		}
		return messages;
	}
	
}
