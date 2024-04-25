package com.ta.csc.repositroy;

import com.ta.csc.domain.Container;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContainerRepository extends CrudRepository<Container,Long> {
    List<Container> findAll();
    Optional<Container> findByContainerNumber(String containerNumber);
    List<Container> findByShippingLine(String shippingLine);
    @Query("SELECT c FROM Container c WHERE c.shippingLine = ?1 and c.invoiceCategory = ?2")
    List<Container> findbyShippingLineAndInvoiceCategory(String shippingLine,String invoiceCategory);
    @Query("SELECT c FROM Container c WHERE c.shippingLine = ?1 and c.fullOrEmpty = ?2")
    List<Container> findByShippingLineAndFullOrEmpty(String shippingLine,String fullOEmpty);
    @Query("SELECT c FROM Container c WHERE c.shippingLine = ?1 and c.fullOrEmpty = ?2 and c.invoiceCategory = ?3 and c.reef = ?4")
    List<Container> findByShippingLineAndFullOrEmptyAndInvoiceCategoryAndReef(String shippingLine,String fullOEmpty,String invoiceCategroy,boolean reef);
    @Query("SELECT DISTINCT shippingLine FROM Container ORDER BY shippingLine ")
    List<String> gettAllShippingLines();
}
