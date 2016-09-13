package br.com.nadefaciladmin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import br.com.nadefaciladmin.bean.Image;

public interface ImageMapper {
	
	final String SELECT_ALL = "SELECT * FROM IMAGE";
	final String SELECT_BY_CODE = "SELECT * FROM IMAGE WHERE id = #{id}";
	final String CREATE_IMAGE = "INSERT INTO IMAGE (name) VALUES (#{image.name})";
	final String UPDATE_IMAGE = "UPDATE IMAGE SET name=#{image.name} where id=#{image.id};";
	final String DELETE_IMAGE = "DELETE FROM IMAGE WHERE id=#{id};";

	@Select(SELECT_ALL)
	@Results(value = {
		@Result(column="server_path", property="serverPath")
	})
	List<Image> selectAll();
	
	@Select(SELECT_BY_CODE)
	@Results(value = {
		@Result(column="server_path", property="serverPath")
	})
	Image selectByCode(int id);
	
	@Insert(CREATE_IMAGE)
	boolean createImage(Image image);
	
	@Update(UPDATE_IMAGE)
	boolean updateImage(Image image);
	
	@Delete(DELETE_IMAGE)
	boolean deleteImage(int id);
} 