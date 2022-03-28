/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.helper.jdbcHelper;
import com.edusys.model.ChuyenDe;
import com.edusys.model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tho
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String>{
    String insert="INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
    String update="UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
    String delete="DELETE FROM ChuyenDe WHERE MaCD=?";
    String selectAll="SELECT * FROM ChuyenDe";
    String selectById="SELECT * FROM ChuyenDe WHERE MaCD=?";
    private ChuyenDe readFromResultSet(ResultSet rs) throws SQLException{
	ChuyenDe model=new ChuyenDe();
        model.setMaCD(rs.getString("MaCD"));
        model.setHinh(rs.getString("Hinh"));
        model.setHocPhi(rs.getDouble("HocPhi"));
        model.setMoTa(rs.getString("MoTa"));
        model.setTenCD(rs.getString("TenCD"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        return model;
    }
    @Override
    public void insert(ChuyenDe entity) {
        jdbcHelper.executeUpdate(insert,entity.getMaCD(),entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity)  {
        jdbcHelper.executeUpdate(update,entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa(),entity.getMaCD());
    }
    @Override
    public void delete(String id) {
        jdbcHelper.executeUpdate(delete, id);
    }
    
    @Override
    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = this.selectBySql(selectById, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return selectBySql(selectAll);
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list=new ArrayList<>();
        try {
            ResultSet rs=null;
            try{
                rs=jdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    list.add(readFromResultSet(rs));
                }
            }finally{
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return list;
    }
    public ChuyenDe findById(String id) {
        String sql="SELECT * FROM ChuyenDe WHERE MaCD=?";
        List<ChuyenDe> list=selectBySql(sql,id);
        return list.size()>0?list.get(0):null;
    }  
 

    
}
