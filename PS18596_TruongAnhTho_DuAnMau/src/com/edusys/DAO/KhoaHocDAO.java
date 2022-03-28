/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.helper.jdbcHelper;
import com.edusys.model.HocVien;
import com.edusys.model.KhoaHoc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tho
 */
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, Integer>{
    String insert="INSERT  KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?)";
    String update="UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH=?";
    String delete="DELETE FROM KhoaHoc WHERE MaKH=?";
    String selectAll="SELECT * FROM KhoaHoc";
    String selectById="SELECT * FROM KhoaHoc WHERE Makh=?";
@Override
    public void insert(KhoaHoc entity) {
        jdbcHelper.executeUpdate(insert, entity.getMaCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getNgayKG(),entity.getGhiChu(),entity.getMaNV());
    }

    @Override
    public void update(KhoaHoc entity) {
        jdbcHelper.executeUpdate(update,entity.getMaCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getNgayKG(),entity.getGhiChu(),entity.getMaNV(),entity.getMaKH());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.executeUpdate(delete, id);
    }

    @Override
    public KhoaHoc selectById(Integer id) {
        List<KhoaHoc> list = this.selectBySql(selectById, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return selectBySql(selectAll);     
    }
    
    public KhoaHoc readFromResultSet(ResultSet rs) throws SQLException{
        KhoaHoc model=new KhoaHoc();
         model.setMaKH(rs.getInt("MaKH"));
         model.setHocPhi(rs.getDouble("HocPhi"));
         model.setThoiLuong(rs.getInt("ThoiLuong"));
         model.setNgayKG(rs.getDate("NgayKG"));
         model.setGhiChu(rs.getString("GhiChu"));
         model.setMaNV(rs.getString("MaNV"));
         model.setNgayTao(rs.getDate("NgayTao"));
         model.setMaCD(rs.getString("MaCD"));
         return model;
    }
    
    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list=new ArrayList<>();
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
    public KhoaHoc findById(Integer makh){
        String sql="SELECT * FROM KhoaHoc WHERE MaKH=?";
        List<KhoaHoc> list = selectBySql(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
     } 
    public List<KhoaHoc> selectByChuyenDe(String macd){
        String sql="select *from khoahoc where macd like ?";
        return this.selectBySql(sql, macd);
    }
}
