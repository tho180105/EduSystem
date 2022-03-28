/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.helper.jdbcHelper;
import com.edusys.model.HocVien;
import com.edusys.model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tho
 */
public class HocVienDAO extends EduSysDAO<HocVien, Integer>{
    String insert="INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
    String update="UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
    String delete="DELETE FROM HocVien WHERE MaHV=?";
     String selectAll="SELECT * FROM HocVien";
     String selectById="SELECT * FROM HocVien WHERE MaHV=?";
@Override
    public void insert(HocVien entity) {
        jdbcHelper.executeUpdate(insert, entity.getMaKH(),entity.getMaNH(),entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        jdbcHelper.executeUpdate(update,entity.getMaKH(), entity.getMaNH(),entity.getDiem(),entity.getMaHV());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.executeUpdate(delete, id);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = this.selectBySql(selectById, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }

    @Override
    public List<HocVien> selectAll() {
        return selectBySql(selectAll);     
    }
    
    public HocVien readFromResultSet(ResultSet rs) throws SQLException{
        HocVien model=new HocVien();
        model.setMaHV(rs.getInt("MaHV"));
         model.setMaKH(rs.getInt("maKH"));
         model.setMaNH(rs.getString("MaNH"));
         model.setDiem(rs.getDouble("Diem"));
         return model;
    }
    
    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list=new ArrayList<>();
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
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }
    public List<HocVien> selectByKhoaHoc(int makh){
        String sql ="select *from HocVien where makh= ?";
        return this.selectBySql(sql, makh);
    }
    
    
    
    
    
}
