package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITruongRepository extends
    PagingAndSortingRepository<Truong, Long>, JpaRepository<Truong, Long> {
        default List<Truong> findAllBooks(Integer pageNo,
                                          Integer pageSize,
                                          String sortBy) {
            return findAll(PageRequest.of(pageNo,
                    pageSize,
                    Sort.by(sortBy)))
                    .getContent();
        }
}
