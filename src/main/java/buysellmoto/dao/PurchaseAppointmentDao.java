package buysellmoto.dao;

import buysellmoto.model.dto.PurchaseAppointmentDto;
import buysellmoto.model.mapper.PurchaseAppointmentMapper;
import buysellmoto.repository.PurchaseAppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseAppointmentDao {

    @Autowired
    private PurchaseAppointmentRepository purchaseAppointmentRepository;

    @Autowired
    private PurchaseAppointmentMapper mapper;

    public PurchaseAppointmentDto getById(Long id) {
        return mapper.toDto(purchaseAppointmentRepository.findById(id).orElseThrow());
    }

    public List<PurchaseAppointmentDto> getAll() {
        return mapper.toDto(purchaseAppointmentRepository.findAll());
    }

    public PurchaseAppointmentDto getByMotorbikeId(Long motorbikeId) {
        return mapper.toDto(purchaseAppointmentRepository.findByMotorbikeId(motorbikeId).orElse(null));
    }

    @Transactional(rollbackOn = {Exception.class})
    public PurchaseAppointmentDto createOne(PurchaseAppointmentDto dto) {
        return mapper.toDto(purchaseAppointmentRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public PurchaseAppointmentDto updateOne(PurchaseAppointmentDto dto) {
        return mapper.toDto(purchaseAppointmentRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        purchaseAppointmentRepository.delete(purchaseAppointmentRepository.findById(id).orElseThrow());
        return true;
    }

}
