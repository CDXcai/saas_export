package com.cdx.dao.system;

import com.cdx.domain.system.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface UserDao {

	//根据企业id查询全部
	List<User> findAll(String companyId);

	//根据id查询
    User findById(String userId);

	//根据id删除
	int delete(String userId);
	//根据id删除逻辑删除
	int deleteStatae(String userId);

	//保存
	int save(User user);

	//更新
	int update(User user);

	// 删除用户的角色信息
	void deleteRoleAll(String userid);

	// 添加用户的角色信息
	void saveRole(@Param("uid") String userid,@Param("rid") String roleId);

	// 根据邮箱查找用户信息
	User findByEmail(String email);

}