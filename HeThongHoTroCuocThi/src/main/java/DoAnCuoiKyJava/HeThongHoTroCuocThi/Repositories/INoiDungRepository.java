package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INoiDungRepository extends
        PagingAndSortingRepository<NoiDung, Long>, JpaRepository<NoiDung, Long> {

    @Query("""
            SELECT n FROM NoiDung n
            WHERE n.tenNoiDung LIKE %?1%
            """)
    List<NoiDung> searchNoiDung(String keyword);

    default List<NoiDung> findAllNoiDungs(Integer pageNo, Integer pageSize, String sortBy) {
        return findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy))).getContent();
    }
}
