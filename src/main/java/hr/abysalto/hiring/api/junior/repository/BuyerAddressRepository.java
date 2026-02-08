package hr.abysalto.hiring.api.junior.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hr.abysalto.hiring.api.junior.model.BuyerAddress;

@Repository
public interface BuyerAddressRepository extends CrudRepository<BuyerAddress, Long> {

}
