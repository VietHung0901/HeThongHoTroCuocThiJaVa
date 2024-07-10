package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findById(Long id);
    Optional<User> findByCccd(String cccd);

    @Query("SELECT lt.tenLoaiTruong, COUNT(u.id) " +
            "FROM User u " +
            "JOIN u.truong t " +
            "JOIN t.loaiTruong lt " +
            "GROUP BY lt.tenLoaiTruong")
    List<Object[]> getUserCountsByLoaiTruong();

}
