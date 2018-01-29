package client_app.appl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import client_app.api.database.DBManager;
import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.Picture;

public class PictureDao implements BaseDao<Picture> {
	private static PictureDao instance = null;
    private EntityManager em;

    private PictureDao() {
        em = new EntityManagerImpl();
    }

    public static PictureDao getInstance() {
    	if (instance == null) {
    		instance = new PictureDao();
    	}
    	
    	return instance;
    }

	@Override
	public Picture findById(Integer id) {
		return em.findById(Picture.class, id);
	}

	@Override
	public Integer getNextIdVal(String columnIdName) {
		return em.getNextIdVal(EntityUtils.getTableName(Picture.class), columnIdName);
	}

	@Override
	public Picture insert(Picture entity) {
		String picturesTableName = EntityUtils.getTableName(Picture.class);
		Connection connection = DBManager.getConnection();
		Integer id = getNextIdVal("ID");

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
				"INSERT INTO " + picturesTableName + " (ID, NAME, PICTURE, UPLOAD_DATE) VALUES (?, ?, ?, ?)"
			);

			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, entity.getName());
			preparedStatement.setBlob(3, entity.getPicture());
			preparedStatement.setTimestamp(4, entity.getUploadDate());

			preparedStatement.executeUpdate();
			entity.setId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entity;
	}

	@Override
	public List<Picture> findAll() {
		return em.findAll(Picture.class);
	}

	@Override
	public Picture update(Picture entity) {
		return null;
	}

	@Override
	public void delete(Picture entity) {
		em.delete(entity);
	}

	@Override
	public List<Picture> findMatchingEntities(Picture patternEntity) {
		return em.findMatchingEntities(Picture.class, patternEntity);
	}
}
