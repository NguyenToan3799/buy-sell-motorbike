package hoangdung.springboot.projecthighlands.service;

import hoangdung.springboot.projecthighlands.config.aop.MultipleTransferToResponseEntities;
import hoangdung.springboot.projecthighlands.config.aop.TranferToResponseEntity;
import hoangdung.springboot.projecthighlands.config.aop.Transformable;
import hoangdung.springboot.projecthighlands.model.dao.Addresses;
import hoangdung.springboot.projecthighlands.model.request.AddressesRequestEntity;
import hoangdung.springboot.projecthighlands.repository.AddressesRepository;
import hoangdung.springboot.projecthighlands.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressesService {

    private final AddressesRepository addressesRepository;

    private final UserRepository userRepository;

    @TranferToResponseEntity
    public Transformable getAddressesById(String id) {
        return addressesRepository.findById(id).orElseThrow();
    }

    @MultipleTransferToResponseEntities
    public List<? extends Transformable> getAllAddressesByUserID(String id) {
        return addressesRepository.getListAddressesByUserID(id);

    }

    @TranferToResponseEntity
    public Transformable createNewAddresses(String userID, AddressesRequestEntity entity) {
        return addressesRepository.save(Addresses.builder()
                .addressesName(entity.getAddressesName())
                .address1(entity.getAddress1())
                .address2(entity.getAddress2())
                .address3(entity.getAddress3())
                .address4(entity.getAddress4())
                .phoneNumber(entity.getPhoneNumber())
                .user(userRepository.findById(entity.getUserID()).orElseThrow())
                .build());
    }

    @TranferToResponseEntity
    public Transformable updateExistingAddresses(String id, AddressesRequestEntity entity) {
        Addresses loadedAddresses = addressesRepository.findById(id).orElseThrow();

        loadedAddresses.setAddressesName(entity.getAddressesName());
        loadedAddresses.setAddress1(entity.getAddress1());
        loadedAddresses.setAddress2(entity.getAddress2());
        loadedAddresses.setAddress3(entity.getAddress3());
        loadedAddresses.setAddress4(entity.getAddress4());
        loadedAddresses.setUser(userRepository.findById(entity.getUserID()).orElseThrow());
        loadedAddresses.setPhoneNumber(entity.getPhoneNumber());

        return addressesRepository.save(loadedAddresses);
    }

    @TranferToResponseEntity
    public Transformable deleteAddressesByID(String id) {
        Addresses loadedAddresses = addressesRepository.findById(id).orElseThrow();
        addressesRepository.deleteById(id);
        return loadedAddresses;
    }
}
