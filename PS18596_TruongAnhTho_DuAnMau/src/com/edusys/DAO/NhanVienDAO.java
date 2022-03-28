/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.helper.jdbcHelper;
import com.edusys.model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tho
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String>{
    String insert=" insert NhanVien(MaNV,MatKhau,HoTen,Vaitro) values(?,?,?,?)";
    String update="UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
    String delete="DELETE FROM NhanVien WHERE MaNV=?";
    String selectAll="SELECT * FROM NhanVien";
    String selectById="SELECT * FROM NhanVien WHERE MaNV=?";
    @Override
    public void insert(NhanVien entity) {
        jdbcHelper.executeUpdate(insert, entity.getMaNV(),entity.getMatKhau(),entity.getHoTen(),entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        jdbcHelper.executeUpdate(update, entity.getMatKhau(),entity.getHoTen(),entity.isVaiTro(),entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.executeUpdate(delete, id);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(selectById, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(selectAll);     
    }
    
    public NhanVien readFromResultSet(ResultSet rs) throws SQLException{
        NhanVien model=new NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setHoTen(rs.getString("HoTen"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        return model;
    }
    
    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list=new ArrayList<>();
        try {
            ResultSet rs=null;
            try{
                rs=jdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    list.add(readFromResultSet(rs));
                }
            }finally{
                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return list;
    }
    public NhanVien findById(String id) {
        String sql="SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list=selectBySql(sql, id);
        return list.size()>0?list.get(0):null;               //có thể trả về là null
    }
    
    
}
