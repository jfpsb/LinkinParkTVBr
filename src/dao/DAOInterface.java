package dao;

import java.util.List;

public interface DAOInterface<Classe> {
	List<Classe> listarWhere(Object...attr);
}
