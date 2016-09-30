package br.com.nadefaciladmin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import br.com.nadefaciladmin.bean.Image;

public interface ImageMapper {
	
	final String SELECT_ALL = "SELECT * FROM IMAGE";
	final String SELECT_BY_CODE = "SELECT * FROM IMAGE WHERE id = #{id}";
	final String SELECT_BY_NAME = "SELECT * FROM IMAGE WHERE name = #{name}";
	final String CREATE_IMAGE = "INSERT INTO IMAGE (name, server_path) VALUES (#{image.name}, #{image.serverPath})";
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
	
	@Select(SELECT_BY_NAME)
	@Results(value = {
		@Result(column="server_path", property="serverPath")
	})
	Image selectByName(String name);
	
	@Insert(CREATE_IMAGE)
	@Options(useGeneratedKeys = true, keyProperty = "image.id", keyColumn = "ID")
	int createImage(@Param("image") Image image);
	
	@Update(UPDATE_IMAGE)
	Image updateImage(@Param("image") final Image image);
	
	@Delete(DELETE_IMAGE)
	boolean deleteImage(int id);
} 