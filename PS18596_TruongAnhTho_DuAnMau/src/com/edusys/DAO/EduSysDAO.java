/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import java.util.List;

/**
 *
 * @author Tho
 */
abstract public class EduSysDAO<EntityTpye, KeyType> {
    abstract public void insert(EntityTpye entity);
    abstract public void update(EntityTpye entity);
    abstract public void delete(KeyType id);
    abstract public EntityTpye selectById(KeyType id);
    abstract public List<EntityTpye> selectAll();
    abstract protected List<EntityTpye> selectBySql(String sql,Object...args);
}
