/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.helper.XDate;
import com.edusys.helper.jdbcHelper;
import com.edusys.model.NguoiHoc;
import com.edusys.model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tho
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String>{
    String insert=" insert NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,Ghichu,MaNV,ngayDK) values(?,?,?,?,?,?,?,?,?)";
    String update="UPDATE NguoiHoc SET HoTen=?, NgaySinh=? , GioiTinh=?, DienThoai=?, Email=?, Ghichu=? WHERE MaNH=?";
    String delete="DELETE FROM NguoiHoc WHERE MaNH=?";
    String selectAll="SELECT * FROM NguoiHoc";
    String selectById="SELECT * FROM NguoiHoc WHERE MaNH=?";
    @Override
    public void insert(NguoiHoc entity) {
        jdbcHelper.executeUpdate(insert,entity.getMaNH(),
             entity.getHoTen(),
             entity.getNgaySinh(),
             entity.isGioiTinh(),
             entity.getDienThoai(),
             entity.getEmail(),
             entity.getGhiChu(),
             entity.getMaNV(),
             XDate.now());
    }

    @Override
    public void update(NguoiHoc entity) {
        jdbcHelper.executeUpdate(update,
             entity.getHoTen(),
             entity.getNgaySinh(),
             entity.isGioiTinh(),
             entity.getDienThoai(),
             entity.getEmail(),
             entity.getGhiChu(),
             entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.executeUpdate(delete, id);
    }

    @Override
    public NguoiHoc selectById(String id) {
        List<NguoiHoc> list = this.selectBySql(selectById, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return selectBySql(selectAll);     
    }
    
    private NguoiHoc readFromResultSet(ResultSet rs) throws SQLException{
	NguoiHoc entity=new NguoiHoc();
         entity.setMaNH(rs.getString("MaNH"));
         entity.setHoTen(rs.getString("HoTen"));
         entity.setNgaySinh(rs.getDate("NgaySinh"));
         entity.setGioiTinh(rs.getBoolean("GioiTinh"));
         entity.setDienThoai(rs.getString("DienThoai"));
         entity.setEmail(rs.getString("Email"));
         entity.setGhiChu(rs.getString("GhiChu"));
         entity.setMaNV(rs.getString("MaNV"));
         entity.setNgayDK(rs.getDate("NgayDK"));
         return entity;
    }
    
    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list=new ArrayList<>();
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
     public List<NguoiHoc> selectByKeyword(String keyword) {
        String sql="SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return selectBySql(sql, "%"+keyword+"%");
    }
    
    //truy xuất tất cả học viên không học khóa học maKH
     public List<NguoiHoc> selectNotInCourse(Integer makh,String key){   //để là Integer cho đúng kiểu Object
        String sql="SELECT * FROM NguoiHoc WHERE hoten like ? and MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
        return selectBySql(sql,"%"+key+"%",makh);
     }
     
     //truy xuất người học theo maNH
     public NguoiHoc findById(String manh){
     String sql="SELECT * FROM NguoiHoc WHERE MaNH=?";
     List<NguoiHoc> list = selectBySql(sql, manh);
     return list.size() > 0 ? list.get(0) : null;
     }
}
