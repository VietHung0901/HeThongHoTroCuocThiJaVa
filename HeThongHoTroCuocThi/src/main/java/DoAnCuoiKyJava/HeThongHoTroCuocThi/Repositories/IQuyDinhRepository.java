package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.QuyDinh;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuyDinhRepository extends
        PagingAndSortingRepository<QuyDinh, Long>, JpaRepository<QuyDinh, Long> {

    @Query("""
            SELECT q FROM QuyDinh q
            WHERE q.tenQuyDinh LIKE %?1%
            """)
    List<QuyDinh> searchQuyDinh(String keyword);

    default List<QuyDinh> findAllQuyDinhs(Integer pageNo, Integer pageSize, String sortBy) {
        return findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy))).getContent();
    }
}
