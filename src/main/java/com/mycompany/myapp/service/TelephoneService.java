package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Telephone;
import com.mycompany.myapp.repository.TelephoneRepository;
import com.mycompany.myapp.service.dto.TelephoneDTO;
import com.mycompany.myapp.service.mapper.TelephoneMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Telephone}.
 */
@Service
@Transactional
public class TelephoneService {

    private static final Logger LOG = LoggerFactory.getLogger(TelephoneService.class);

    private final TelephoneRepository telephoneRepository;

    private final TelephoneMapper telephoneMapper;

    public TelephoneService(TelephoneRepository telephoneRepository, TelephoneMapper telephoneMapper) {
        this.telephoneRepository = telephoneRepository;
        this.telephoneMapper = telephoneMapper;
    }

    /**
     * Save a telephone.
     *
     * @param telephoneDTO the entity to save.
     * @return the persisted entity.
     */
    public TelephoneDTO save(TelephoneDTO telephoneDTO) {
        LOG.debug("Request to save Telephone : {}", telephoneDTO);
        Telephone telephone = telephoneMapper.toEntity(telephoneDTO);
        telephone = telephoneRepository.save(telephone);
        return telephoneMapper.toDto(telephone);
    }

    /**
     * Update a telephone.
     *
     * @param telephoneDTO the entity to save.
     * @return the persisted entity.
     */
    public TelephoneDTO update(TelephoneDTO telephoneDTO) {
        LOG.debug("Request to update Telephone : {}", telephoneDTO);
        Telephone telephone = telephoneMapper.toEntity(telephoneDTO);
        telephone = telephoneRepository.save(telephone);
        return telephoneMapper.toDto(telephone);
    }

    /**
     * Partially update a telephone.
     *
     * @param telephoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TelephoneDTO> partialUpdate(TelephoneDTO telephoneDTO) {
        LOG.debug("Request to partially update Telephone : {}", telephoneDTO);

        return telephoneRepository
            .findById(telephoneDTO.getId())
            .map(existingTelephone -> {
                telephoneMapper.partialUpdate(existingTelephone, telephoneDTO);

                return existingTelephone;
            })
            .map(telephoneRepository::save)
            .map(telephoneMapper::toDto);
    }

    /**
     * Get all the telephones.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TelephoneDTO> findAll() {
        LOG.debug("Request to get all Telephones");
        return telephoneRepository.findAll().stream().map(telephoneMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the telephones where PmEntreprise is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TelephoneDTO> findAllWherePmEntrepriseIsNull() {
        LOG.debug("Request to get all telephones where PmEntreprise is null");
        return StreamSupport.stream(telephoneRepository.findAll().spliterator(), false)
            .filter(telephone -> telephone.getPmEntreprise() == null)
            .map(telephoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the telephones where PmEtablissement is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TelephoneDTO> findAllWherePmEtablissementIsNull() {
        LOG.debug("Request to get all telephones where PmEtablissement is null");
        return StreamSupport.stream(telephoneRepository.findAll().spliterator(), false)
            .filter(telephone -> telephone.getPmEtablissement() == null)
            .map(telephoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one telephone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TelephoneDTO> findOne(Long id) {
        LOG.debug("Request to get Telephone : {}", id);
        return telephoneRepository.findById(id).map(telephoneMapper::toDto);
    }

    /**
     * Delete the telephone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Telephone : {}", id);
        telephoneRepository.deleteById(id);
    }
}
