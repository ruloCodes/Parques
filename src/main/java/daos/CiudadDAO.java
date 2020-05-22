package daos;

import interfaces.IDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelos.Ciudad;
import modelos.Parque;

/**
 *
 * @author rulo
 */
public class CiudadDAO extends BaseDAO implements IDAO<Ciudad, String> {

	private final static String SELECT = "SELECT id_ciudad FROM ciudades WHERE nombre_ciudad = ?";
	private final static String SELECT_POR_ID = "SELECT * FROM ciudades WHERE id_ciudad = ?";
	private final static String UPDATE = "UPDATE ciudad SET nombre = \"nuevo_nombre\"";
	private final static String DELETE = "DELETE FROM ciudad WHERE id = 100";
	private final static String INSERT = "INSERT INTO ciudad (id, nombre, CCAA) VALUES(100, \"nombre_ciudad\", \"nombre_CCAA\")";
	private final static String SELECT_POR_CIUDAD = "SELECT * FROM parques p INNER JOIN ciudades c ON p.id_ciudad = c.id_ciudad WHERE c.nombre_ciudad = ?";
	private final static String SELECT_POR_CCAA = "SELECT * FROM parques p INNER JOIN ciudades c ON p.id_ciudad = c.id_ciudad WHERE c.ccaa = ?";

	private static PreparedStatement ps;
	private static ResultSet rs;

	/**
	 * Método para recuperar el nombre de una ciudad por su id
	 *
	 * @param id el id de la ciudad a consultar
	 * @return String con el nombre de la ciudad
	 */
	public static Ciudad getCiudadPorId(int id) {
		conectar();

		Ciudad ciudad = new Ciudad();
		try {
			ps = conexion.prepareStatement(SELECT_POR_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				ciudad.setId_ciudad(rs.getInt(1));
				ciudad.setNombre_ciudad(rs.getString(2));
				ciudad.setCcaa(rs.getString(3));
			}
			
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getNombreCiudad\n" + e.toString());
		}
		finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getNombreCiudad\n" + e.toString());
			}
		}

		return ciudad;
	}

	public static ArrayList<Parque> getParquesPorCiudad(String city) {
		conectar();

		ArrayList<Parque> parques = new ArrayList<>();
		Parque parque;

		try {
			ps = conexion.prepareStatement(SELECT_POR_CIUDAD);
			ps.setString(1, city);
			rs = ps.executeQuery();

			while (rs.next()) {
				parque = new Parque();

				int id_parque = rs.getInt(1);
				String nombre_parque = rs.getString(2);
				int extension = rs.getInt(3);
				int id_ciudad = rs.getInt(4);

				parque.setId_parque(id_parque);
				parque.setNombre_parque(nombre_parque);
				parque.setExtension(extension);
				parque.setId_ciudad(id_ciudad);

				parques.add(parque);
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Fallo en getParquesPorCiudad\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException ex) {
				System.out.println("Fallo en cierre de conexión de getParquesPorCiudad\n" + ex.toString());
			}
		}

		return parques;
	}

	public static ArrayList<Parque> getParquesPorCCAA(String CCAA) {
		conectar();

		ArrayList<Parque> parques = new ArrayList<>();
		Parque parque;

		try {
			ps = conexion.prepareStatement(SELECT_POR_CCAA);
			ps.setString(1, CCAA);
			rs = ps.executeQuery();

			while (rs.next()) {
				parque = new Parque();

				int id_parque = rs.getInt(1);
				String nombre_parque = rs.getString(2);
				int extension = rs.getInt(3);
				int id_ciudad = rs.getInt(4);

				parque.setId_parque(id_parque);
				parque.setNombre_parque(nombre_parque);
				parque.setExtension(extension);
				parque.setId_ciudad(id_ciudad);

				parques.add(parque);
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Fallo en getParquesPorCCAA\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException ex) {
				System.out.println("Fallo en el finally de getParquesPorCCAA\n" + ex.toString());
			}
		}

		return parques;
	}

	public int getIdCiudad(String nombreCiudad) {
		conectar();
		int idCiudad = 0;
		try {
			ps = conexion.prepareStatement(SELECT);
			ps.setString(1, nombreCiudad);
			rs = ps.executeQuery();

			while (rs.next()) {
				idCiudad = rs.getInt("id_ciudad");
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getIdCiudad\n" + e.toString());
		}
		finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getIdCiudad\n" + e.toString());
			}
		}

		return idCiudad;
	}

	@Override
	public int consultarTodo() {
		return 0;
	}

	@Override
	public boolean getExiste(String ciudad) {
		return existeCiudad(ciudad);
	}
	
	private boolean existeCiudad(String nombreCiudad) {
		conectar();
		boolean existe = false;
		
		try {
			ps = conexion.prepareStatement(SELECT);
			ps.setString(1, nombreCiudad);
			rs = ps.executeQuery();

			if(rs.next()) {
				existe = true;
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en existeCiudad\n" + e.toString());
		}
		finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en finally de existeCiudad\n" + e.toString());
			}
		}
		return existe;
	}

}